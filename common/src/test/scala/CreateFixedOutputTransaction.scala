import com.alexdupre.shapeshift.models.Market

object CreateFixedOutputTransaction extends App with ShapeShift with Provider {

  execute { c =>
    val market        = Market("XRP", "BTC")
    val outputAmount  = BigDecimal(args(0))
    val outputAddress = btcAddress
    val returnAddress = xrpAddress
    c.createFixedOutputTransaction(market, outputAmount, outputAddress, returnAddress = Option(returnAddress))
  }

}
