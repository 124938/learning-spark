package _1_dataframe._1_1_native._1_1_1_transformation

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DFFilterDemo {

  def main(args: Array[String]): Unit = {
    // Create instance of SparkConf
    val conf = new SparkConf().
      setAppName("Filter Demo - Using DataFrame - SQLContext").
      setMaster("local[2]")

    // Create instance of SparkContext
    val sc = new SparkContext(conf)

    // Create instance of SQLContext
    val sqlContext = new SQLContext(sc)
    sqlContext.
      setConf("spark.sql.shuffle.partitions", "2")

    println("***** Problem Statement : Filter out all NEW & PENDING orders *****")

    // Create DataFrame of orders from parquet data source
    val ordersDF = sqlContext.
      read.
      parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/parquet")

    // Print Schema
    ordersDF.
      printSchema

    // Preview Data
    ordersDF.
      show(20)

    println("===== Approach 1 - Using DSL Way (filter) =====")
    ordersDF.
      filter( (ordersDF.apply("order_status").===("NEW")).or(ordersDF.apply("order_status").contains("PENDING"))).
      show(20)

    println("===== Approach 2 - Using SQL Way (WHERE) =====")
    ordersDF.
      registerTempTable("ORDERS")

    sqlContext.
      sql("SELECT * "+
        "FROM ORDERS "+
        "WHERE (order_status = 'NEW' OR order_status like '%PENDING%')").
      show(20)
  }

}
