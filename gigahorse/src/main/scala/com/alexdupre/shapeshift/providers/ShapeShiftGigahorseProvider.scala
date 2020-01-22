package com.alexdupre.shapeshift.providers

import java.nio.charset.Charset
import java.util.concurrent.ExecutionException

import com.alexdupre.shapeshift.{BuildInfo, ShapeShiftClient}
import gigahorse._
import gigahorse.support.okhttp.Gigahorse
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

class ShapeShiftGigahorseProvider(http: HttpClient)(implicit ec: ExecutionContext) extends ProviderAPI {

  override def get(endpoint: String): Future[ProviderResponse] = {
    val req = Gigahorse.url(endpoint).get
    execute(req)
  }

  override def post(endpoint: String, params: JsValue): Future[ProviderResponse] = {
    val payload = Json.stringify(params)
    val utf8    = Charset.forName("UTF-8")
    val req     = Gigahorse.url(endpoint).withContentType("application/json", utf8).post(payload, utf8)
    execute(req)
  }

  private def execute(req: Request): Future[ProviderResponse] = {
    http.run(req) map { r =>
      val js =
        try {
          Json.parse(r.bodyAsString)
        } catch {
          case e: Exception => sys.error("ShapeShift Protocol Exception")
        }
      ProviderResponse(r.status, js)
    } recover {
      case e: ExecutionException => throw e.getCause
    }
  }
}

object ShapeShiftGigahorseProvider {

  def newClient(http: HttpClient = defaultHttp)(implicit ec: ExecutionContext) =
    new ShapeShiftClient(new ShapeShiftGigahorseProvider(defaultHttp))

  lazy val defaultHttp = {
    val config = Gigahorse.config
      .withUserAgentOpt(Some(s"AlexDupre-ShapeShift/${BuildInfo.version}"))
      .withConnectTimeout(5 seconds)
      .withReadTimeout(30 seconds)
      .withRequestTimeout(60 seconds)
    Gigahorse.http(config)
  }

}
