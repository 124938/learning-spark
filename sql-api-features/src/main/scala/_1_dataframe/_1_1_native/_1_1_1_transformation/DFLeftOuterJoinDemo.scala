package _1_dataframe._1_1_native._1_1_1_transformation

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DFLeftOuterJoinDemo {

  def main(args: Array[String]): Unit = {

    // Create instance of SparkConf
    val conf = new SparkConf().
      setAppName("Left Outer Join Demo - Using DataFrame SQLContext").
      setMaster("local[2]")

    // Create instance of SparkContext
    val sc = new SparkContext(conf)

    // Create instance of SQLContext
    val sqlContext = new SQLContext(sc)
    sqlContext.
      setConf("spark.sql.shuffle.partitions", "2")

    // Below is used to convert RDD to DataFrame implicitly
    import sqlContext.implicits._

    println("***** Problem Statement : Find out number of orders, which does not have any order items *****")

    // Create DataFrame using JSON of order
    val ordersDF = sqlContext.
      read.
      json("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/json")

    // Print schema
    ordersDF.
      printSchema

    // Create DataFrame using JSON of order items
    val orderItemsDF = sqlContext.
      read.
      json("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/order_items/json")

    // Print schema
    orderItemsDF.
      printSchema

    println("===== Approach 1 - Using DSL Way (join [leftouter], where) =====")
    ordersDF.
      join(orderItemsDF, $"order_id" === $"order_item_order_id", "leftouter").
      where($"order_item_id" isNull).
      select(
        org.apache.spark.sql.functions.count($"order_id") as "order_count"
      ).
      show

    println("===== Approach 2 - Using SQL Way =====")
    ordersDF.
      registerTempTable("ORDERS")

    orderItemsDF.
      registerTempTable("ORDER_ITEMS")

    sqlContext.
      sql(
        " SELECT "+
        "   COUNT(1) as order_count"+
        " FROM "+
        "   ORDERS o LEFT OUTER JOIN ORDER_ITEMS oi ON (o.order_id = oi.order_item_order_id) "+
        " WHERE " +
        "   oi.order_item_order_id IS NULL").
      show
  }
}
