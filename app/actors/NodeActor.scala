package cropsitedb.actors

import akka.actor.{Actor, Props}
import akka.event.Logging

import scala.concurrent.Promise


class NodeActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case x: Promise[String] => {
      Thread.sleep(10000)
      x.success("ROASTED!")
    }
    case _ => log.info("Received a message")
  }
}
