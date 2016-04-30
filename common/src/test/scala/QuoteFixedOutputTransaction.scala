import com.alexdupre.shapeshift.models.Market

object QuoteFixedOutputTransaction extends App with ShapeShift with Provider {

  execute { c =>
    val market = Market("ETH", "BTC")
    val outputAmount = BigDecimal(args(0))
    c.quoteFixedOutputTransaction(market, outputAmount)
  }

}
