
version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
    .enablePlugins(PlayScala)
    .settings(
        name := """play-heroku-playground""",
        organization := "com.example",
        scalaVersion := "2.13.6",
        libraryDependencies ++= Seq (
            guice,
            "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
            "com.typesafe.play" %% "play-slick" % "5.0.0",
            "com.typesafe.slick" %% "slick" % "3.3.3",
            "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
            "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0"
        )

    )
    


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
