import com.alexdupre.shapeshift.models.Market

object CreateFixedOutputTransactionWithSpecialInput extends App with ShapeShift with Provider {

  execute { c =>
    val market = Market("XMR", "BTC")
    val inputAmount = BigDecimal(args(0))
    val outputAddress = btcAddress
    c.createFixedOutputTransaction(market, inputAmount, outputAddress)
  }

}
