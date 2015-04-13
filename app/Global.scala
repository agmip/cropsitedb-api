import play.api.GlobalSettings
import play.api.libs.concurrent.Akka
import akka.actor.{ Actor, Props }
import play.api.Play.current
import play.api.mvc._
import cropsitedb.actors._
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.duration._

class CorsFilter extends EssentialFilter {
  def apply(next: EssentialAction) = new EssentialAction {
    def apply(requestHeader: RequestHeader) = {
      if(play.api.Play.isDev(play.api.Play.current)) {
      next(requestHeader).map { result =>
        result.withHeaders("Access-Control-Allow-Origin" -> "*",
          "Access-Control-Allow-Methods" -> "POST, GET, OPTIONS, PUT, DELETE",
          "Access-Control-Allow-Headers" -> "Origin, Content-Type, Accept, Authorization, Referer, Host, DNT, Accept-Encoding, Accept-Language, User-Agent, Cache-Control, X-Requested-With")
      }
      } else {
        next(requestHeader).map { result =>
          result
        }
      }
    }
  }
}


object Global extends WithFilters(new CorsFilter) with GlobalSettings {
  override def onStart(application : play.api.Application) {
    val acebProc = Akka.system.actorOf(Props[ProcessACEB], name="process-aceb")
    val domeProc = Akka.system.actorOf(Props[ProcessDOME], name="process-dome")
    val acmoProc = Akka.system.actorOf(Props[ProcessACMO], name="process-acmo")
    val alnkProc = Akka.system.actorOf(Props[ProcessALINK], name="process-alnk")
    val nodeProc = Akka.system.actorOf(Props[NodeActor], name="node-handler")
  }
}
