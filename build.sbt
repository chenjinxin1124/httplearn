name := "httplearn"

version := "0.1"

scalaVersion := "2.12.8"

val http4sVersion = "0.20.10"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-server" % http4sVersion,
  "org.http4s" %% "http4s-dropwizard-metrics" % http4sVersion,
  "org.http4s" %% "http4s-prometheus-metrics" % http4sVersion
)

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-circe" % http4sVersion,
  // Optional for auto-derivation of JSON codecs
  "io.circe" %% "circe-generic" % "0.11.1",
  // Optional for string interpolation to JSON model
  "io.circe" %% "circe-literal" % "0.11.1"
)
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-argonaut" % http4sVersion,
  // Optional for auto-derivation of JSON codecs
  "com.github.alexarchambault" %% "argonaut-shapeless_6.2" % "1.2.0-M6"
)

scalacOptions ++= Seq("-Ypartial-unification")