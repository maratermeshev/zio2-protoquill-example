package graphql

import caliban.GraphQL.graphQL
import caliban.RootResolver
import caliban.execution.Field
import database.dao.DataService
import io.getquill.CalibanIntegration._
import io.getquill.ProductArgs
import models.Person
import zio.Task
import caliban.{RootResolver, ZHttpAdapter}
import zhttp.http._
import zhttp.service.Server
import zio.ZLayer

object CalibanContext extends zio.ZIOAppDefault :
  case class Queries(
                      getPeople: Field => ProductArgs[Person] => Task[List[Person]],
                      getPeoplePlan: Field => ProductArgs[Person] => Task[DataServiceLive.PersonPlanQuery]
                    )

  val endpoints =
    graphQL(
      RootResolver(
        Queries(
          people =>
            productArgs => {
              val cols = quillColumns(people)
              DataServiceLive.getPeople(cols, productArgs.keyValues)
            },
          peoplePlan =>
            productArgs => {
              val cols = quillColumns(peoplePlan)
              (DataServiceLive.getPeoplePlan(cols, productArgs.keyValues) zip DataServiceLive.getPeople(cols, productArgs.keyValues)).map(
                (pa, plan) => Dao.PersonAddressPlanQuery(pa, plan)
              )
            }
        )
      )
    ).interpreter

  val myApp = for {
    //    _ <- DataService.resetDatabase()
    interpreter <- endpoints
    _ <- Server.start(
      port = 8088,
      http = Http.collectHttp[Request] { case _ -> !! / "api" / "graphql" =>
        ZHttpAdapter.makeHttpService(interpreter)
      }
    )
      .forever
  } yield ()

  override def run =
    myApp.exitCode

end CalibanContext