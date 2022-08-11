package database.dao

import zio.ZLayer

import javax.sql.DataSource
import io.getquill.{query as quillQuery, *}
import io.getquill.context.qzio.ImplicitSyntax.*
import io.getquill.context.ZioJdbc.*
import models.Person
import io.getquill.context.qzio.ImplicitSyntax.Implicit

object DataServiceLive {
  val layer = ZLayer.fromFunction(DataServiceLive(_))
}

final case class DataServiceLive(dataSource: DataSource) extends DataService {

  import QuillContext.*

  val env = ZLayer.succeed(dataSource)

  case class PersonPlanQuery(plan: String, pa: List[Person])

  inline def getPeopleQ(inline columns: List[String], inline filters: Map[String, String]) =
    quote {
      quillQuery[Person]
        .filterColumns(columns)
        .filterByKeys(filters)
        .take(10)
    }

  def getPeople(columns: List[String], filters: Map[String, String]) = {
    println(s"Getting columns: $columns")
    run(getPeopleQ(columns, filters)).implicitDS(Implicit(dataSource)).mapError(e => {
//            logger.underlying.error("getPeople query failed", e)
      e
    })
  }

  inline def plan(inline columns: List[String], inline filters: Map[String, String]) = {
    quote {
      sql"EXPLAIN ${getPeopleQ(columns, filters)}".pure.as[Query[String]]
    }
  }

  def getPeoplePlan(columns: List[String], filters: Map[String, String]) =
    run(plan(columns, filters), OuterSelectWrap.Never).map(_.mkString("\n")).implicitDS.mapError(e => {
//      logger.underlying.error("getPeoplePlan query failed", e)
      e
    })
}
