package cropsitedb.helpers

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

/*** Cluster-specific messages ***/
object ClusterMessages {
  case class ClientJoinMessage(connector: String, cluster: String, node: String)
  case class ClusterJoinMessage(cluster: String, node: String) 

  implicit val ClientJoinMessageReads = Json.reads[ClientJoinMessage]
  implicit val ClusterJoinMessageFormat = Json.format[ClusterJoinMessage]
}
