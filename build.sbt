lazy val `protoquill-example` = project
  .in(file("."))
  .settings(
    name := "zio2-protoquill-example",
    version := "0.1.0",
    resolvers ++= Seq(
      Resolver.mavenLocal,
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
      "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases"
    ),
    scalaVersion := "3.1.3",
    scalacOptions ++= Seq(
      "-language:implicitConversions",
    ),
    libraryDependencies ++= Seq(
      "io.getquill" %% "quill-jdbc-zio" % "4.2.0",
      "org.postgresql"       %  "postgresql"     % "42.3.6",
      "com.github.ghostdogpr" %% "caliban" % "2.0.1",
      "com.github.ghostdogpr" %% "caliban-zio-http"   % "2.0.1",
      "ch.qos.logback" % "logback-classic" % "1.2.11" % Test,
      "io.d11" %% "zhttp"      % "2.0.0-RC10" % Test,
      "org.scalatest" %% "scalatest" % "3.2.12" % Test,
      "org.scalatest" %% "scalatest-mustmatchers" % "3.2.12" % Test,
    )
  )