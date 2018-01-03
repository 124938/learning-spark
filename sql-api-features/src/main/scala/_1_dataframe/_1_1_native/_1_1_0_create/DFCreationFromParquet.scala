package _1_dataframe._1_1_native._1_1_0_create

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DFCreationFromParquet {

  def main(args: Array[String]): Unit = {

    // Create Spark Configuration
    val conf = new SparkConf().
      setAppName("DF Creation From Parquet File").
      setMaster("local[2]")

    // Create Spark Context
    val sc = new SparkContext(conf)

    // Create SQL Context
    val sqlContext = new SQLContext(sc)

    println("**** Problem Statement : Create DataFrame - Using Parquet File ****")
    val orderDF1 = sqlContext.
      read.
      parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/parquet")

    // Preview schema
    orderDF1.
      printSchema

    // Preview data
    orderDF1.
      show(20)
  }
}
