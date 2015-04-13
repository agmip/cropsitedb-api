package cropsitedb.helpers

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

import scala.concurrent.Promise

/*** Cluster-specific messages ***/
object ClusterMessages {
  case class ClientJoinMessage(connector: String, cluster: String, node: String)
  case class ClusterJoinMessage(cluster: String, node: String) 
  case class ClusterJoin(in: ClusterJoinMessage, out: Promise[String])

  implicit val ClientJoinMessageReads = Json.reads[ClientJoinMessage]
  implicit val ClusterJoinMessageFormat = Json.format[ClusterJoinMessage]
}
