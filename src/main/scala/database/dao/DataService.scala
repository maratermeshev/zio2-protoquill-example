package database.dao

import models.Person
import zio.ZIO

import java.sql.SQLException

trait DataService {
  def getPeople(columns: List[String], filters: Map[String, String]): ZIO[Any, SQLException, List[Person]]
}

object DataService {
  def getPeople(columns: List[String], filters: Map[String, String]) = ZIO.serviceWithZIO[DataService](_.getPeople(columns, filters))
}
