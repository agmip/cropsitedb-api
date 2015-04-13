package cropsitedb.controllers

import akka.actor.ActorSelection
import akka.pattern.ask
import akka.util.Timeout

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Akka
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.ws._
import play.api.Play.current

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.concurrent.duration._
import scala.util.{Success, Failure}

import cropsitedb.helpers.ClusterMessages._

/*** Used to control the cluster syncronization ***/
object ClusterController extends Controller {
  val nodeActor = Akka.system.actorSelection("akka://application/user/node-handler")

  def index = TODO

  /*** Used by the cluster to accept a client joining ***/
  def join = Action.async(parse.json) { req =>
  val joinReq = req.body.validate[ClusterJoinMessage]
    joinReq.fold(
      errors => Future(BadRequest(Json.obj("error"->"Invalid json message"))),
      jReq   => {
          val p = Promise[String]
          nodeActor ! p
          p.future.map { x =>
            Ok(x)
          }
      }
    )
  }

  def leave = TODO

  def ping  = Action {
    Ok("pong")
  }
}
