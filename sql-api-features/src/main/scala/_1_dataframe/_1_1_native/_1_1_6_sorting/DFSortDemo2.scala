package _1_dataframe._1_1_native._1_1_6_sorting

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DFSortDemo2 {

  def main(args: Array[String]): Unit = {

    // Create instance of SparkConf
    val conf = new SparkConf().
      setAppName("Sort Demo 2 - Using DataFrame SQLContext").
      setMaster("local[2]")

    // Create instance of SparkContext
    val sc = new SparkContext(conf)

    // Create instance of SQLContext
    val sqlContext = new SQLContext(sc)

    sqlContext.
      setConf("spark.sql.shuffle.partitions", "2")

    // Below is used to convert RDD to DataFrame
    import sqlContext.implicits._

    println("***** Problem Statement : Sort order data based on customer id & status both in ascending order *****")

    val ordersDF = sqlContext.
      read.
      parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/parquet")

    println("===== Approach 1 - Using DSL Way =====")
    ordersDF.
      sort(
        $"order_customer_id" asc,
        $"order_status" asc
      ).
      show(20)

    println("===== Approach 2 - Using SQL Way =====")
    ordersDF.
      registerTempTable("ORDERS")

    sqlContext.
      sql(
        " SELECT "+
        "   *  "+
        " FROM "+
        "   ORDERS "+
        " ORDER BY "+
        "   order_customer_id asc, "+
        "   order_status asc"
      ).
      show(20)
  }
}
