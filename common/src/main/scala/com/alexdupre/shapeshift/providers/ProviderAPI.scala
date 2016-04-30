package com.alexdupre.shapeshift.providers

import play.api.libs.json.JsValue

import scala.concurrent.Future

trait ProviderAPI {

  def get(path: String): Future[ProviderResponse]

  def post(path: String, params: JsValue): Future[ProviderResponse]

}
