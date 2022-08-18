package models

import java.time.Instant

case class Person(
                   id: Int,
                   name: String,
                   age: Int,
                   insuranceId: Option[Int],
                   isInsured: Boolean,
                   createdAt: Instant
                 )
