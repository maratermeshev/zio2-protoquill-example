package database.dao

import io.getquill.context.ZioJdbc.*
import io.getquill.jdbczio.Quill
import io.getquill.*
import models.Person
import zio.*
import java.sql.SQLException
import java.time.Instant
import java.util.Date

import javax.sql.DataSource

object QuillContext extends PostgresZioJdbcContext(SnakeCase) {
  val dataSourceLayer = Quill.DataSource.fromPrefix("myDatabaseConfig").orDie
  implicit val instantDecoder: MappedEncoding[Date, Instant] = MappedEncoding[Date, Instant](d => d.toInstant)
  implicit val instantEncoder: MappedEncoding[Instant, Date] = MappedEncoding[Instant, Date](i => Date.from(i))

}
