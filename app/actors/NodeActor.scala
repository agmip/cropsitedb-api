package cropsitedb.actors

import akka.actor.{Actor, Props}
import akka.event.Logging

import scala.concurrent.Promise

import cropsitedb.helpers.ClusterMessages._

class NodeActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case join: ClusterJoin => {
      join.out.success("Joined "+join.in.cluster)
      // Actually need to do something useful here
    }
    case _ => log.info("Received a message")
  }
}
