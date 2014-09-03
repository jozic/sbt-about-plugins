import xerial.sbt.Sonatype._

sonatypeSettings

pomExtra :=
  <url>http://github.com/jozic/sbt-about-plugins</url>
    <licenses>
      <license>
        <name>BSD-style</name>
        <url>http://www.opensource.org/licenses/BSD-3-Clause</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:jozic/sbt-about-plugins.git</url>
      <connection>scm:git:git@github.com:jozic/sbt-about-plugins.git</connection>
    </scm>
    <developers>
      <developer>
        <id>jozic</id>
        <name>Eugene Platonov</name>
        <url>http://github.com/jozic</url>
      </developer>
    </developers>
