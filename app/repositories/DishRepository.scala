package repositories

import com.google.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext
import javax.inject.Singleton
import slick.jdbc.JdbcProfile

import models.Dish
import scala.concurrent.Future

@Singleton
class DishRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

    // JDBCProfile for this provider
    private val dbConfig = dbConfigProvider.get[JdbcProfile]

    import dbConfig._
    import profile.api._

    private class DishTable(tag: Tag) extends Table[Dish](tag, "dish") {

        def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

        def name = column[String]("name")

        def price = column[Int]("price")

        def * = (id, name, price) <> ((Dish.apply _).tupled, Dish.unapply)
    }

    // starting point for all queries
    private val dishes = TableQuery[DishTable]

    def create(name: String, price: Int): Future[Dish] = db.run {
        (dishes.map(dish => (dish.name, dish.price))
            returning dishes.map(_.id)
            into ((namePrice, id) => Dish(id, namePrice._1, namePrice._2))
            ) += (name, price)
    }

    def list(): Future[Seq[Dish]] = db.run {
        dishes.result
    }
}
