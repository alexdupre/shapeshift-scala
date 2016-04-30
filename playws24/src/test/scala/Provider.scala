
import com.alexdupre.shapeshift.providers.ShapeShiftPlayProvider

import scala.concurrent.ExecutionContext.Implicits.global

trait Provider extends ShapeShift {

  val http = ShapeShiftPlayProvider.defaultHttp

  override def getClient() = ShapeShiftPlayProvider.newClient(http)

  override def shutdown() = http.close()

}
