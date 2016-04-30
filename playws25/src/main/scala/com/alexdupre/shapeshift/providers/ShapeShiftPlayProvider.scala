package com.alexdupre.shapeshift.providers

import java.util.concurrent.ExecutionException

import com.alexdupre.shapeshift.{BuildInfo, ShapeShiftClient}
import play.api.libs.json.JsValue
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.{ExecutionContext, Future}

class ShapeShiftPlayProvider(http: WSClient)(implicit ec: ExecutionContext) extends ProviderAPI {

  override def get(endpoint: String): Future[ProviderResponse] = {
    toProviderResponse(initRequest(endpoint).get())
  }

  override def post(endpoint: String, params: JsValue): Future[ProviderResponse] = {
    toProviderResponse(initRequest(endpoint).post(params))
  }

  private def toProviderResponse(resp: Future[WSResponse]): Future[ProviderResponse] = {
    resp map { r =>
      val js = try {
        r.json
      } catch {
        case e : Exception => sys.error("ShapeShift Protocol Exception")
      }
      ProviderResponse(r.status, js)
    } recover {
      case e: ExecutionException => throw e.getCause
    }
  }

  private def initRequest(endpoint: String) = {
    http.url(endpoint).withHeaders("User-Agent" -> s"AlexDupre-ShapeShift/${BuildInfo.version}")
  }
}

object ShapeShiftPlayProvider {

  def newClient(http: WSClient)(implicit ec: ExecutionContext) =
    new ShapeShiftClient(new ShapeShiftPlayProvider(http))

}
