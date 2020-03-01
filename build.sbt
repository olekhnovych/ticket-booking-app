import sbt.Keys.version

organization in ThisBuild := "com.github.olekhnovych"
scalaVersion in ThisBuild := "2.12.8"

resolvers in ThisBuild += "confluent" at "https://packages.confluent.io/maven/"

javacOptions in ThisBuild ++= Seq("-source", "1.9", "-target", "1.9")
testOptions in Test += Tests.Argument("-oD")

lazy val ticketbooking = (project in file("."))
  .settings(
    name := "ticket-booking",
    version := "0.1.0",
    libraryDependencies ++= {

      val akkaVersion              = "2.5.19"
      val akkaHttpVersion          = "10.1.5"
      val scalaTestVersion         = "3.0.8"
      val scalaMockVersion         = "4.4.0"
      val slickVersion             = "3.3.1"
      val slickPgVersion           = "0.18.1"
      val postgresqlVersion        = "42.2.5"
      val monocleVersion           = "2.0.0"
      val testContainersVersion    = "0.35.2"
      val loggingVersion           = "3.9.2"
      val logbackVersion           = "1.2.3"

      Seq(
        "com.typesafe.akka" %% "akka-http-core"       % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
        "com.typesafe.akka" %% "akka-stream"          % akkaVersion,

        "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
        "org.scalamock" %% "scalamock" % scalaMockVersion % Test,

        "com.typesafe.slick"  %% "slick"               % slickVersion,
        "com.typesafe.slick"  %% "slick-hikaricp"      % slickVersion,
        "com.github.tminglei" %% "slick-pg"            % slickPgVersion,
        "com.github.tminglei" %% "slick-pg_joda-time"  % slickPgVersion,
        "com.github.tminglei" %% "slick-pg_spray-json" % slickPgVersion,
        "org.postgresql"      %  "postgresql"          % postgresqlVersion,

        "com.github.julien-truffaut" %%  "monocle-core"  % monocleVersion,
        "com.github.julien-truffaut" %%  "monocle-macro" % monocleVersion,
        "com.github.julien-truffaut" %%  "monocle-law"   % monocleVersion % Test,

        "com.dimafeng" %% "testcontainers-scala-scalatest"  % testContainersVersion % Test,
        "com.dimafeng" %% "testcontainers-scala-postgresql" % testContainersVersion % Test,

        "com.typesafe.scala-logging" %% "scala-logging"   % loggingVersion,
        "ch.qos.logback"             %  "logback-classic" % logbackVersion,
      )
    }
  )
