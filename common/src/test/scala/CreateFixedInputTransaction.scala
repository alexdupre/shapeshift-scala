import com.alexdupre.shapeshift.models.Market

object CreateFixedInputTransaction extends App with ShapeShift with Provider {

  execute { c =>
    val market = Market("ETH", "BTC")
    val inputAmount = BigDecimal(args(0))
    val outputAddress = btcAddress
    val returnAddress = ethAddress
    c.createFixedInputTransaction(market, inputAmount, outputAddress, returnAddress = Option(returnAddress))
  }

}
