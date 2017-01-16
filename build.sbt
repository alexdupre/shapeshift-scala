lazy val commonSettings = Seq(
  organization := "com.alexdupre.shapeshift",
  version := "1.0",
  scalaVersion := "2.11.8",
  scalacOptions ++= List("-feature", "-unchecked", "-deprecation", "-explaintypes", "-encoding", "UTF8", "-Xlint", "-language:_"),
  buildInfoKeys := Seq[BuildInfoKey](version),
  buildInfoPackage := organization.value,
  resolvers += Resolver.typesafeRepo("releases"),
  libraryDependencies ++= List(
    "org.slf4j" % "slf4j-api" % "1.7.22",
    "ch.qos.logback" % "logback-core" % "1.1.8" % "test",
    "ch.qos.logback" % "logback-classic" % "1.1.8" % "test"
  ),
  unmanagedSourceDirectories in Compile += baseDirectory.value / ".." / "common" / "src" / "main" / "scala",
  unmanagedSourceDirectories in Test += baseDirectory.value / ".." / "common" / "src" / "test" / "scala",
  unmanagedResourceDirectories in Test += baseDirectory.value / ".." / "common" / "src" / "test" / "resources",
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  pomIncludeRepository := { _ => false },
  pomExtra := (
    <url>https://github.com/alexdupre/shapeshift-scala</url>
    <licenses>
      <license>
        <name>BSD-style</name>
        <url>http://www.opensource.org/licenses/bsd-license.php</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:alexdupre/shapeshift-scala.git</url>
      <connection>scm:git:git@github.com:alexdupre/shapeshift-scala.git</connection>
    </scm>
    <developers>
      <developer>
        <id>alexdupre</id>
        <name>Alex Dupre</name>
        <url>http://www.alexdupre.com</url>
      </developer>
    </developers>)
)

def provider(id: String) = Project(id, file(id)).
  settings(name := s"shapeshift-$id").
  enablePlugins(BuildInfoPlugin).
  settings(commonSettings: _*)

lazy val root = (project in file(".")).
  settings(name := "shapeshift-scala").
  settings(commonSettings: _*).
  settings(packagedArtifacts := Map.empty).
  aggregate(dispatch0112, dispatch0113, gigahorse, playws23, playws24, playws25)

lazy val dispatch0112 = provider("dispatch0112").
  settings(
    libraryDependencies ++= List(
      "com.typesafe.play" %% "play-json" % "2.3.10",
      "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
      "com.ning" % "async-http-client" % "1.8.17"
    )
  )

lazy val dispatch0113 = provider("dispatch0113").
  settings(
    libraryDependencies ++= List(
      "com.typesafe.play" %% "play-json" % "2.4.8",
      "net.databinder.dispatch" %% "dispatch-core" % "0.11.3",
      "com.ning" % "async-http-client" % "1.9.40"
    )
  )

lazy val gigahorse = provider("gigahorse").
  settings(
    libraryDependencies ++= List(
      "com.typesafe.play" %% "play-json" % "2.5.10",
      "com.eed3si9n" %% "gigahorse-asynchttpclient" % "0.2.0",
      "org.asynchttpclient" % "async-http-client" % "2.0.26"
    )
  )

lazy val playws23 = provider("playws23").
  settings(
    libraryDependencies ++= List(
      "com.typesafe.play" %% "play-ws" % "2.3.10",
      "com.ning" % "async-http-client" % "1.8.17"
    )
  )

lazy val playws24 = provider("playws24").
  settings(
    libraryDependencies ++= List(
      "com.typesafe.play" %% "play-ws" % "2.4.8",
      "com.ning" % "async-http-client" % "1.9.40"
    )
  )

lazy val playws25 = provider("playws25").
  settings(
    libraryDependencies ++= List(
      "com.typesafe.play" %% "play-ws" % "2.5.10",
      "org.asynchttpclient" % "async-http-client" % "2.0.26"
    )
  )
