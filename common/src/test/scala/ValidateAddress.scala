import com.alexdupre.shapeshift.models.Coin

import scala.concurrent.ExecutionContext.Implicits.global

object ValidateAddress extends App with ShapeShift with Provider {

  execute { c =>
    val coin    = Coin.from(args(0))
    val address = args(1)
    c.validateAddress(coin, address).map(_ => "OK")
  }

}
