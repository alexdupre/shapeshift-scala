lazy val commonSettings = Seq(
  organization := "com.alexdupre.shapeshift",
  version := "2.5",
  scalaVersion := "2.12.10",
  crossScalaVersions := Seq(scalaVersion.value, "2.13.1"),
  scalacOptions ++= List("-feature", "-unchecked", "-deprecation", "-explaintypes", "-encoding", "UTF8", "-Xlint", "-language:_"),
  buildInfoKeys := Seq[BuildInfoKey](version),
  buildInfoPackage := organization.value,
  resolvers += Resolver.typesafeRepo("releases"),
  libraryDependencies ++= List(
    "com.typesafe.play" %% "play-json"      % "2.8.1",
    "org.slf4j"         % "slf4j-api"       % "1.7.30",
    "ch.qos.logback"    % "logback-core"    % "1.2.3" % "test",
    "ch.qos.logback"    % "logback-classic" % "1.2.3" % "test"
  ),
  unmanagedSourceDirectories in Compile += baseDirectory.value / ".." / "common" / "src" / "main" / "scala",
  unmanagedSourceDirectories in Test += baseDirectory.value / ".." / "common" / "src" / "test" / "scala",
  unmanagedResourceDirectories in Test += baseDirectory.value / ".." / "common" / "src" / "test" / "resources",
  publishTo := sonatypePublishToBundle.value,
  publishMavenStyle := true,
  licenses := Seq("BSD-style" -> url("http://www.opensource.org/licenses/bsd-license.php")),
  sonatypeProfileName := "com.alexdupre",
  sonatypeProjectHosting := Some(xerial.sbt.Sonatype.GitHubHosting("alexdupre", "shapeshift-scala", "Alex Dupre", "ale@FreeBSD.org"))
)

def provider(id: String) =
  Project(id, file(id)).settings(name := s"shapeshift-$id").enablePlugins(BuildInfoPlugin).settings(commonSettings: _*)

lazy val root = (project in file("."))
  .settings(name := "shapeshift-scala")
  .settings(commonSettings: _*)
  .settings(packagedArtifacts := Map.empty)
  .aggregate(gigahorse)

lazy val gigahorse = provider("gigahorse").settings(
  libraryDependencies ++= List(
    "com.eed3si9n" %% "gigahorse-okhttp" % "0.5.0"
  )
)
