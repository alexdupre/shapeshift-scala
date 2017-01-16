import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.alexdupre.shapeshift.providers.ShapeShiftPlayProvider
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.ExecutionContext.Implicits.global

trait Provider extends ShapeShift {

  implicit val actorSystem       = ActorSystem()
  implicit val actorMaterializer = ActorMaterializer()
  val ws                         = AhcWSClient()

  override def getClient() = ShapeShiftPlayProvider.newClient(ws)

  override def shutdown() {
    ws.close()
    actorMaterializer.shutdown()
    actorSystem.terminate()
  }

}
