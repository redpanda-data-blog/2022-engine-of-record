
import org.apache.spark.sql.SparkSession
import com.google.cloud.spark.bigquery._
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object RedpandaSpark {
  def main(args:Array[String]):Unit= {
    val spark = SparkSession
      .builder
      .appName("Redpanda Stream")
      .config("spark.master", "local")
      .getOrCreate()
    spark.conf.set("viewsEnabled","true")
    spark.conf.set("credentialsFile","<path-to-json-key-file>")
    spark.conf.set("materializationDataset","sensor_data")


    val redpanda_read = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "sensor_stream").option("startingOffsets","earliest")
      .load()


    val sensor_data=redpanda_read.selectExpr( "CAST(value AS STRING)")
    val stream_1=sensor_data.withColumn("value", split(col("value"), ","))
      .select(col("value")(0).as("id"),col("value")(3).as("temperature")
    )
    val stream_2=sensor_data.withColumn("value",split(col("value"),","))
      .select(col("value")(0).as("id"),col("value")(3).as("temperature"))

    val joined_stream=stream_1.union(stream_2)

    val redpanda_write=joined_stream.write.format("bigquery")
      .option("table", "sensor_data.sensor_table").save()

  }
}
