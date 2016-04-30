import com.alexdupre.shapeshift.providers.ShapeShiftDispatchProvider

import scala.concurrent.ExecutionContext.Implicits.global

trait Provider extends ShapeShift {

  val http = ShapeShiftDispatchProvider.defaultHttp

  override def getClient() = ShapeShiftDispatchProvider.newClient()

  override def shutdown() = http.shutdown()

}
