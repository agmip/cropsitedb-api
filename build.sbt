name := "cropsitedb-api"

version := "2.0.6.2"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.postgresql"   % "postgresql"          % "9.3-1102-jdbc41",
  "org.mariadb.jdbc" % "mariadb-java-client" % "1.1.8",
  "org.agmip.ace"    % "ace-core"            % "2.0.1-SNAPSHOT",
  "org.apache.tika"  % "tika-core"           % "1.6",
  "org.agmip"        % "dome"                % "1.4.9",
  "org.agmip"        % "acmo"                % "1.1.4",
  "org.agmip.tools"  %% "data-seam"           % "0.1.0-SNAPSHOT"
)

doc in Compile <<= target.map(_ / "none")

mappings in Universal := {
	val orig = (mappings in Universal).value
	orig.filterNot { case (_, file) => file.endsWith("application.conf") || file.endsWith(".DS_Store")  }
}
