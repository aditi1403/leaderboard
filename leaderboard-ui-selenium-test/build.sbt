name := "leaderboard-ui-selenium-test"
version := "0.1"
scalaVersion := "2.12.10"
val typesafeVersion = "1.2.1"
val seleniumChromeDriverVersion="4.0.0-alpha-4"
val seleniumJavaVersion="4.0.0-alpha-4"
val testNGVersion="6.10"
val scalaTestVersion="3.0.8"
libraryDependencies += "org.scalatest" % "scalatest_2.12" % scalaTestVersion % "test"
libraryDependencies += "org.seleniumhq.selenium" % "selenium-chrome-driver" % seleniumChromeDriverVersion
libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % seleniumJavaVersion
libraryDependencies += "org.testng" % "testng" % testNGVersion
libraryDependencies += "com.typesafe" % "config" % typesafeVersion



//libraryDependencies += "com.typesafe.sbt" % "sbt-interface" % typesafeVersion