name := "Redpanda"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.13.8"


libraryDependencies++=Seq("org.apache.spark" %% "spark-sql" % "3.2.1",
  "org.apache.spark" %% "spark-streaming" % "3.2.1",
  "org.apache.kafka"%"kafka-clients"%"3.1.0",
  "org.apache.spark" %% "spark-core" % "3.2.1",
  "org.apache.spark"%"spark-sql-kafka-0-10_2.13"%"3.2.1",
  "com.google.cloud.spark" %% "spark-bigquery-with-dependencies" % "0.26.0"

)
