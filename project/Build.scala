import sbt._
import Keys._

object ApplicationBuild extends Build {

  val appName         = "sherpa"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // DB
    //, "mysql" % "mysql-connector-java" % "5.1.23"
    "postgresql" % "postgresql" % "9.1-901.jdbc4"

    // Modules
    //,"com.feth" %% "play-authenticate" % "0.2.5-SNAPSHOT"
    ,"securesocial" %% "securesocial" % "master-SNAPSHOT"

    // enables logging
    , "org.slf4j" % "slf4j-api" % "1.7.2"
    , "ch.qos.logback" % "logback-classic" % "1.0.9"

    // metrics
    , "nl.grons" % "metrics-scala_2.10" % "2.2.0"
    //    , "com.yammer.metrics" % "metrics-scala_2.10" % "2.2.0"
    //    , "com.yammer.metrics" % "metrics-graphite" % "2.2.0"

    // Webjars
    , "org.webjars" % "webjars-play" % "2.1.0"
    //    , "org.webjars" % "requirejs" % "2.1.1"
    , "org.webjars" % "bootstrap" % "2.3.1"
    , "org.webjars" % "momentjs" % "1.7.2"
    , "org.webjars" % "angularjs" % "1.1.3"
    , "org.webjars" % "angular-ui" % "0.3.2-1"
    , "org.webjars" % "angular-strap" % "0.6.6"
    , "org.webjars" % "font-awesome" % "3.0.0"
    , "org.webjars" % "jquery-ui" % "1.9.2"
    , "org.webjars" % "tinymce-jquery" % "3.4.9"

  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    resolvers += Resolver.url("sbt-plugin-snapshots", url("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns)
    ,resolvers += Resolver.url("sbt-plugin-releases", url("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)
    ,resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
    ,resolvers += "OSS Sonatype Public" at "https://oss.sonatype.org/content/groups/public/"
    ,resolvers += "spray repo" at "http://repo.spray.io"
    ,resolvers += "Twitter Repo" at "http://maven.twttr.com/"
    ,resolvers += "Maven2" at "http://repo1.maven.org/maven2/"
    //,resolvers += "sbt-plugin-snapshots" at "http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"
    //,resolvers += "sbt-plugin-releases" at "http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases"
    ,resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns)
    ,resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns)
    //,resolvers += Resolver.url("play-authenticate (release)", url("http://joscha.github.com/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns)
    //,resolvers += Resolver.url("play-authenticate (snapshot)", url("http://joscha.github.com/play-authenticate/repo/snapshots/"))(Resolver.ivyStylePatterns)
  ).dependsOn(RootProject( uri("git://github.com/freekh/play-slick.git") ))
}
