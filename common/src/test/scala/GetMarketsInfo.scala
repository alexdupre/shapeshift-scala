object GetMarketsInfo extends App with ShapeShift with Provider {

  executeT { c =>
    c.getMarketsInfo()
  }

}
