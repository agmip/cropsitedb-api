package cropsitedb.actors

import akka.actor.{Actor,Props}
import akka.agent.Agent
import akka.event.Logging
import play.api.libs.ws._

import scala.concurrent.Future
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

object SyncMessages {
}

// This will work for static endpoints, but dynamic
// ones will need some conceptual work. Probably
// along the lines of an add/remove to the SyncRouter
class SyncRouter(endpoints: List[String]) extends Actor {
  val log = Logging(context.system, thisl)
  def receive = {
    case _ => log.info("we're good!")
  }
}

class SyncActor extends Actor {
  case class Status(current: Option[String], count: Int)
  val log = Logging(context.system, this)
  val agent = Agent(Status(None, 0))

  def receive = {
    case "tick" => pingMyself
    case _ => log.info("nothing")
  }

  def pingMyself = {
    WS.url("http://localhost:9000/cropsitedb/2/sync/ping").get().map { res =>
      log.info(res.body)
      agent send (x => Status(Some("alive"), x.count + 1))
      val cs = agent.get
      log.info(cs.count+" - "+cs.current.getOrElse("unknown"))
    }
  }
}
