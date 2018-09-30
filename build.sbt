import com.typesafe.config.ConfigFactory
import com.typesafe.sbt.packager.docker.Cmd

val conf = ConfigFactory.parseFile(new File("conf/application.conf")).resolve()

lazy val commonSettings = Seq(
  name := conf.getString("app.name"),
  organization := conf.getString("app.org"),
  version := conf.getString("app.version"),
  scalaVersion := "2.12.6",
  autoAPIMappings := true
)

lazy val dockerSettings = Seq(
  packageName in Docker := conf.getString("docker.package.name"),
  dockerBaseImage := conf.getString("docker.baseImage"),
  dockerLabels := Map(
    "maintainer" -> conf.getString("docker.maintainer")
  ),
  dockerCommands := Seq(
    Cmd("FROM", "openjdk:8-jre-alpine"),
    Cmd("RUN", "apk --no-cache add bash"),
    Cmd("WORKDIR", "/opt/docker"),
    Cmd("ADD", "--chown=daemon:daemon opt /opt"),
    Cmd("USER", "daemon"),
    Cmd("ENTRYPOINT", """["/opt/docker/bin/seed"]"""),
    Cmd("CMD", """[]""")
  )
)

// Dependencies
val config    = "com.typesafe" % "config" % "1.3.2"
val swaggerUi = "org.webjars" % "swagger-ui" % "2.2.0"
val scalaTest = "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2"

lazy val root = (project in file("."))
  .enablePlugins(
    PlayScala,
    DockerPlugin,
    SwaggerPlugin)
  .settings(
    commonSettings,
    dockerSettings,
    swaggerDomainNameSpaces := Seq("models"),
    libraryDependencies ++= Seq(
      guice,
      config,
      swaggerUi,
      scalaTest % Test
    )
  )

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "it.gumaz.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "it.gumaz.binders._"