package cropsitedb.actors

import akka.actor.{Actor,Props}
import akka.event.Logging
import play.api.libs.ws._

import scala.concurrent.Future
import play.api.Play.current

object SyncMessages {
}

class SyncActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case "tick" => pingMyself
    case _ => log.info("nothing")
  }

  def pingMyself = {
    import scala.concurrent.ExecutionContext.Implicits.global
    WS.url("http://localhost:9000/cropsitedb/2/sync/ping").get().map { res =>
      log.info(res.body)
    }
  }
}
