import com.alexdupre.shapeshift.models.Market

object GetDepositLimit extends App with ShapeShift with Provider {

  execute { c =>
    val market = Market(args(0), args(1))
    c.getDepositLimit(market)
  }

}
