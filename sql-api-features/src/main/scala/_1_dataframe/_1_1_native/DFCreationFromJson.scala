package _1_dataframe._1_1_native

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DFCreationFromJson {

  def main(args: Array[String]): Unit = {

    // Create Spark Configuration
    val conf = new SparkConf().
      setAppName("DF Creation From Json").
      setMaster("local[2]")

    // Create Spark Context
    val sc = new SparkContext(conf)

    // Create SQL Context
    val sqlContext = new SQLContext(sc)

    println("**** Problem Statement : Create DataFrame - Using JSON ****")
    val orderDF1 = sqlContext.
      read.
      json("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/json")

    // Preview schema
    orderDF1.
      printSchema

    // Preview data
    orderDF1.
      show(20)
  }
}
