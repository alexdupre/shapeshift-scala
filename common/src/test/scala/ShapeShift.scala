import com.alexdupre.shapeshift.ShapeShiftAPI

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

trait ShapeShift {

  def getClient(): ShapeShiftAPI
  def shutdown(): Unit

  def execute[T](f: ShapeShiftAPI => Future[T]) = {
    try {
      val r = Await.result(f(getClient()), 30 seconds)
      println(r)
    } finally {
      shutdown()
    }
  }

  def executeT[T](f: ShapeShiftAPI => Future[Traversable[T]]) = {
    try {
      val r = Await.result(f(getClient()), 30 seconds)
      r.foreach(println)
    } finally {
      shutdown()
    }
  }

  val btcAddress = "1DJ28uXT5pMHx4uWurEa8ZVTfyWVBCzm6E"
  val ethAddress = "0xf6287ad45d37bccf56ee950301c6959966757b1d"
  val ltcAddress = "LXXTzTVFjiumz1H5xL6nvfmChzDNe46WKZ"
  val xmrAddress = "48xu85vn4AuP9aaCcZM9WLchue3jicJQKjMEU9HkDHmLV5d24CeCUf3dvFTRuvtKxh9xotZdZBMNBX2qyvieRcJ8CwEFsrL"
  val xmrPaymentId = "c278c1a9a026a80b869b6ff4d84e7c61327e48d70e8dbd7897b40d6ed05e28ed"

}
