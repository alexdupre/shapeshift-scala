object GetOrderInfo extends App with ShapeShift with Provider {

  execute { c =>
    val id = args(0)
    c.getOrderInfo(id)
  }

}
