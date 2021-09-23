package models

import play.api.libs.json.OFormat
import play.api.libs.json.Json

final case class Dish(id: Long, name: String, price: Int)

object Dish {
    implicit val dishFormat: OFormat[Dish] = Json.format[Dish]
}