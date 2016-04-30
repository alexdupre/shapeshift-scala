import com.alexdupre.shapeshift.models.Market

object CreateFixedOutputTransaction extends App with ShapeShift with Provider {

  execute { c =>
    val market = Market("ETH", "BTC")
    val inputAmount = BigDecimal(args(0))
    val outputAddress = btcAddress
    val returnAddress = ethAddress
    c.createFixedOutputTransaction(market, inputAmount, outputAddress, returnAddress = Option(returnAddress))
  }

}
