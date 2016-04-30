import com.alexdupre.shapeshift.models.Market

object CreateFixedInputTransactionWithSpecialOutput extends App with ShapeShift with Provider {

  execute { c =>
    val market = Market("BTC", "XMR")
    val inputAmount = BigDecimal(args(0))
    val outputAddress = xmrAddress
    val outputSpecial = "paymentId" -> xmrPaymentId
    c.createFixedInputTransaction(market, inputAmount, outputAddress, outputSpecial = Option(outputSpecial))
  }

}
