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
      // https://mvnrepository.com/artifact/io.getquill/quill-jdbc-zio
      "io.getquill" %% "quill-jdbc-zio" % "4.2.0",
      "org.postgresql"       %  "postgresql"     % "42.4.1"
    )
  )