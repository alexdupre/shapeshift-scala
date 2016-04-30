import com.alexdupre.shapeshift.models.Market

object CreateOpenTransactionWithSpecialInput extends App with ShapeShift with Provider {

  execute { c =>
    val market = Market("XMR", "BTC")
    val outputAddress = btcAddress
    val returnAddress = xmrAddress
    c.createOpenTransaction(market, outputAddress, returnAddress = Option(returnAddress))
  }

}