import com.alexdupre.shapeshift.models.Market

object CreateOpenTransaction extends App with ShapeShift with Provider {

  execute { c =>
    val market        = Market("BTC", "LTC")
    val outputAddress = ltcAddress
    c.createOpenTransaction(market, outputAddress)
  }

}
