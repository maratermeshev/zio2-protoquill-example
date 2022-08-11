package graphql

import caliban.GraphQL.graphQL
import caliban.execution.Field
import caliban.schema.Schema
import caliban.{RootResolver, ZHttpAdapter, schema}
import database.dao.{DataService, DataServiceLive, QuillContext}
import io.getquill.CalibanIntegration.*
import io.getquill.ProductArgs
import models.Person
import zhttp.http.*
import zhttp.service.Server
import zio.{Task, ZIO, ZLayer}


object CalibanContext extends zio.ZIOAppDefault :
  case class Queries(
                      getPeople: Field =>
                        ProductArgs[Person] =>
                          ZIO[DataServiceLive, Throwable, List[Person]],
                      getPeoplePlan: Field =>
                        ProductArgs[Person] =>
                          ZIO[DataServiceLive, Throwable, DataServiceLive.PersonPlanQuery]
                    )

  val endpoints =
    graphQL[DataServiceLive, Queries, Unit, Unit](
      RootResolver(
        Queries(
          people =>
            productArgs => {
              val cols = quillColumns(people)
              ZIO.serviceWithZIO[DataServiceLive](_.getPeople(cols, productArgs.keyValues))
            },
          peoplePlan =>
            productArgs => {
              val cols = quillColumns(peoplePlan)
              ZIO.serviceWithZIO[DataServiceLive](_.getPeoplePlan(cols, productArgs.keyValues))
                .zip(ZIO.serviceWithZIO[DataServiceLive](_.getPeople(cols, productArgs.keyValues)))
                .map((pa, plan) => DataServiceLive.PersonPlanQuery(pa, plan))
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
    myApp.provide(DataServiceLive.layer, QuillContext.dataSourceLayer).exitCode

end CalibanContext
