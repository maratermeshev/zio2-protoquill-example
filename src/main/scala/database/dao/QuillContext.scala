package database.dao

import io.getquill.context.ZioJdbc.*
import io.getquill.jdbczio.Quill
import io.getquill._
import models.Person
import zio._
import java.sql.SQLException
import javax.sql.DataSource

object QuillContext extends PostgresZioJdbcContext(SnakeCase) {
  val dataSourceLayer = Quill.DataSource.fromPrefix("myDatabaseConfig").orDie
}

