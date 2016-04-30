import com.alexdupre.shapeshift.models.Market

object QuoteFixedInputTransaction extends App with ShapeShift with Provider {

  execute { c =>
    val market = Market("ETH", "BTC")
    val inputAmount = BigDecimal(args(0))
    c.quoteFixedInputTransaction(market, inputAmount)
  }

}
