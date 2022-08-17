package models

import java.time.ZonedDateTime

case class Person(
                   id: Int,
                   name: String,
                   age: Int,
                   insuranceId: Option[Int],
                   isInsured: Boolean,
                   createdAt: ZonedDateTime
                 )