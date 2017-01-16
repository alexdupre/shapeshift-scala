package com.alexdupre.shapeshift.providers

import java.util.concurrent.ExecutionException

import com.alexdupre.shapeshift.{BuildInfo, ShapeShiftClient}
import com.ning.http.client.providers.netty.NettyAsyncHttpProviderConfig
import dispatch.{Http, Req, url}
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.{ExecutionContext, Future}

class ShapeShiftDispatchProvider(http: Http)(implicit ec: ExecutionContext) extends ProviderAPI {

  override def get(endpoint: String): Future[ProviderResponse] = {
    val req = url(endpoint).GET
    execute(req)
  }

  override def post(endpoint: String, params: JsValue): Future[ProviderResponse] = {
    val payload = Json.stringify(params)
    val req     = url(endpoint).POST.setBody(payload).setContentType("application/json", "UTF-8")
    execute(req)
  }

  private def execute(req: Req): Future[ProviderResponse] = {
    http(req) map { r =>
      val js = try {
        Json.parse(r.getResponseBodyAsBytes)
      } catch {
        case e: Exception => sys.error("ShapeShift Protocol Exception")
      }
      ProviderResponse(r.getStatusCode, js)
    } recover {
      case e: ExecutionException => throw e.getCause
    }
  }
}

object ShapeShiftDispatchProvider {

  def newClient(http: Http = defaultHttp)(implicit ec: ExecutionContext) =
    new ShapeShiftClient(new ShapeShiftDispatchProvider(defaultHttp))

  lazy val defaultHttp = {
    val nettyConfig = new NettyAsyncHttpProviderConfig
    nettyConfig.setHandshakeTimeout(5000)
    Http.configure(
      _.setUserAgent(s"AlexDupre-ShapeShift/${BuildInfo.version}")
        .setRequestTimeout(30000)
        .setConnectTimeout(5000)
        .setAsyncHttpClientProviderConfig(nettyConfig))
  }

}
