package database.dao

import zio.ZLayer
import javax.sql.DataSource
import io.getquill.{query as quillQuery, *}
import io.getquill.context.qzio.ImplicitSyntax._
import io.getquill.context.ZioJdbc._
import models.Person

object DataServiceLive {
  val layer = ZLayer.fromFunction(DataServiceLive(_))
}

final case class DataServiceLive(dataSource: DataSource) extends DataService {
  import QuillContext.*
  val env = ZLayer.succeed(dataSource)
  inline def getPeopleQ(inline columns: List[String], inline filters: Map[String, String]) =
    quote {
      quillQuery[Person]
        .filterColumns(columns)
        .filterByKeys(filters)
        .take(10)
    }

  def getPeople(columns: List[String], filters: Map[String, String]) = {
    implicit val implicitDataSource: DataSource = dataSource
    println(s"Getting columns: $columns")
    run(getPeopleQ(columns, filters)).implicitDS.mapError(e => {
      logger.underlying.error("getPeopleQ query failed", e)
      e
    })
  }
}
