lazy val commonSettings = Seq(
  organization := "com.alexdupre",
  version := "0.9.0-SNAPSHOT",
  scalaVersion := "2.11.8",
  scalacOptions ++= List("-feature", "-unchecked", "-deprecation", "-explaintypes", "-encoding", "UTF8", "-Xlint", "-language:_"),
  buildInfoKeys := Seq[BuildInfoKey](version),
  buildInfoPackage := s"${organization.value}.shapeshift",
  resolvers += Resolver.typesafeRepo("releases"),
  libraryDependencies ++= List(
    "org.slf4j" % "slf4j-api" % "1.7.21",
    "ch.qos.logback" % "logback-core" % "1.1.7" % "test",
    "ch.qos.logback" % "logback-classic" % "1.1.7" % "test"
  ),
  unmanagedSourceDirectories in Compile += baseDirectory.value / ".." / "common" / "src" / "main" / "scala",
  unmanagedSourceDirectories in Test += baseDirectory.value / ".." / "common" / "src" / "test" / "scala",
  unmanagedResourceDirectories in Test += baseDirectory.value / ".." / "common" / "src" / "test" / "resources"
)

def provider(id: String) = Project(id, file(id)).
  settings(name := s"shapeshift-$id").
  enablePlugins(BuildInfoPlugin).
  settings(commonSettings: _*)

lazy val root = (project in file(".")).
  settings(name := "shapeshift-scala").
  settings(commonSettings: _*).
  settings(packagedArtifacts := Map.empty).
  aggregate(dispatch0112, dispatch0113, playws23, playws24, playws25)

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
      "com.typesafe.play" %% "play-json" % "2.4.6",
      "net.databinder.dispatch" %% "dispatch-core" % "0.11.3",
      "com.ning" % "async-http-client" % "1.9.38"
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
      "com.typesafe.play" %% "play-ws" % "2.4.6",
      "com.ning" % "async-http-client" % "1.9.38"
    )
  )

lazy val playws25 = provider("playws25").
  settings(
    libraryDependencies ++= List(
      "com.typesafe.play" %% "play-ws" % "2.5.3",
      "org.asynchttpclient" % "async-http-client" % "2.0.2"
    )
  )
