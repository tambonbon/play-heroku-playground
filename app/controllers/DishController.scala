package controllers

import repositories.DishRepository
import com.google.inject.Inject
import play.api.mvc.MessagesControllerComponents

import scala.concurrent.ExecutionContext
import play.api.mvc.MessagesAbstractController
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import play.api.db._

import scala.concurrent.Future
import play.api.libs.json.Json
import play.api.data.validation.Constraints._

class DishController @Inject() (repo: DishRepository, cc: MessagesControllerComponents)(implicit
    ec: ExecutionContext
) extends MessagesAbstractController(cc) {

    // mapping for dish form
    val dishForm: Form[CreateDishForm] = Form {
        mapping(
            "name" -> nonEmptyText,
            "price" -> number.verifying(min(0), max(100))
        )(CreateDishForm.apply)(CreateDishForm.unapply)
    }

    def addDish = Action.async {implicit request => 
        dishForm.bindFromRequest().fold(
            errorForm => Future.successful(Ok(views.html.dish(errorForm))
            ),
            aDish => {
                repo.create(aDish.name, aDish.price).map { _ => 
                    Redirect(routes.HomeController.index()).flashing("success" -> "dish.created")}
            }
        )
    }

    def getDishes = Action.async { implicit request => 
        repo.list().map {allDishes => Ok(Json.toJson(allDishes))}}
}

case class CreateDishForm(name: String, price: Int)
