package database.dao

import zio.ZLayer

import javax.sql.DataSource
import io.getquill.{query as quillQuery, *}
import io.getquill.context.qzio.ImplicitSyntax.*
import io.getquill.context.ZioJdbc.*
import models.Person
import io.getquill.context.qzio.ImplicitSyntax.Implicit

object DAO {
  val layer = ZLayer.fromFunction(DAO(_))

  case class PersonPlanQuery(plan: String, pa: List[Person])
}

final case class DAO(dataSource: DataSource) extends DataService {

  import QuillContext.*

  val env = ZLayer.succeed(dataSource)

  inline def getPeopleQ(inline columns: List[String], inline filters: Map[String, String]) =
    quote {
      quillQuery[Person]
        .filterByKeys(filters)
        .filterColumns(columns)
        .take(10)
    }

  given Implicit[DataSource] = Implicit(dataSource)

  def getPeople(columns: List[String], filters: Map[String, String]) = {
    println(s"Getting columns: $columns")
    run(getPeopleQ(columns, filters)).implicitDS.mapError(e => {
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
