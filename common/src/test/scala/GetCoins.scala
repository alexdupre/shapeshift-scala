import scala.concurrent.ExecutionContext.Implicits.global

object GetCoins extends App with ShapeShift with Provider {

  executeT { c =>
    c.getCoins().map(_.values.toList.sortBy(_.name))
  }

}
