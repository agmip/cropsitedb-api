package cropsitedb.controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Akka
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.ws._


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Success, Failure}

import play.api.Logger
import play.api.Play.current
import cropsitedb.helpers.ClusterMessages._

object NodeController extends Controller {
  def index = TODO

  /*** Used to join a client to a cluster ***/
  def join = Action.async(parse.json) { req =>
    val joinReq = req.body.validate[ClientJoinMessage]
    joinReq.fold(
      errors => Future(BadRequest(Json.obj("error"->"Invalid join message"))),
      jReq   => {
        // This is when we send something to the connector from the ClientJoinMessage
        val joinMessage = ClusterJoinMessage(jReq.cluster, jReq.node) 
        WS.url(jReq.connector+"/cropsitedb/2/cluster/join").post(Json.toJson(joinMessage)).map {
          res =>
            Logger.info("Joining "+jReq.cluster+" on "+jReq.connector)
            Ok(res.body)
        }
      }
    )
  }

  def leave = TODO

  def ping  = Action {
    Ok("pong")
  }
}
