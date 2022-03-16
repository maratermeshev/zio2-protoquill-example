package example.module

import io.getquill.context.ZioJdbc._
import zio._
import io.getquill._

import java.sql.SQLException
import javax.sql.DataSource

case class Person(name: String, age: Int)

object QuillContext extends PostgresZioJdbcContext(SnakeCase) {
   val dataSourceLayer = DataSourceLayer.fromPrefix("myDatabaseConfig").orDie
}

trait DataService {
  def getPeople: ZIO[Any, SQLException, List[Person]]
  def getPeopleOlderThan(age: Int): ZIO[Any, SQLException, List[Person]]
}

object DataService {
   def getPeople = ZIO.serviceWithZIO[DataService](_.getPeople)
   def getPeopleOlderThan(age: Int) = ZIO.serviceWithZIO[DataService](_.getPeopleOlderThan(age))
}

object DataServiceLive {
   val layer = (DataServiceLive.apply _).toLayer[DataService]
}

final case class DataServiceLive(dataSource: DataSource) extends DataService {
   import QuillContext._
   val env = ZLayer.succeed(dataSource)
   def getPeople =
     run(query[Person]).provide(env)
   def getPeopleOlderThan(age: Int) =
     run(query[Person].filter(p => p.age > lift(age))).provide(env)
}
