import com.alexdupre.shapeshift.providers.ShapeShiftGigahorseProvider

import scala.concurrent.ExecutionContext.Implicits.global

trait Provider extends ShapeShift {

  val http = ShapeShiftGigahorseProvider.defaultHttp

  override def getClient() = ShapeShiftGigahorseProvider.newClient()

  override def shutdown() = http.close()

}
