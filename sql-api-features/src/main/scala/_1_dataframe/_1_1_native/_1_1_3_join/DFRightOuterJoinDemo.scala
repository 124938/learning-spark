package _1_dataframe._1_1_native._1_1_3_join

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DFRightOuterJoinDemo {

  def main(args: Array[String]): Unit = {

    // Create instance of SparkConf
    val conf = new SparkConf().
      setAppName("Right Outer Join Demo - Using DataFrame SQLContext").
      setMaster("local[2]")

    // Create instance of SparkContext
    val sc = new SparkContext(conf)

    // Create instance of SQLContext
    val sqlContext = new SQLContext(sc)
    sqlContext.
      setConf("spark.sql.shuffle.partitions", "2")

    // Below is used to convert RDD to DataFrame implicitly
    import com.databricks.spark.avro._
    import sqlContext.implicits._

    println("***** Problem Statement : Find out number of order items, which does not linked with order *****")

    // Create DataFrame using JSON of order
    val ordersDF = sqlContext.
      read.
      parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/parquet")

    // Print schema
    ordersDF.
      printSchema

    // Create DataFrame using JSON of order items
    val orderItemsDF = sqlContext.
      read.
      avro("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/order_items/avro")

    // Print schema
    orderItemsDF.
      printSchema

    println("===== Approach 1 - Using DSL Way (join [rightouter], where) =====")
    ordersDF.
      join(orderItemsDF, $"order_id" === $"order_item_order_id", "rightouter").
      where($"order_id" isNull).
      select(
        org.apache.spark.sql.functions.count($"order_item_id") as "order_item_count"
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
        "   COUNT(1) as order_item_count"+
        " FROM "+
        "   ORDERS o RIGHT OUTER JOIN ORDER_ITEMS oi ON (o.order_id = oi.order_item_order_id) "+
        " WHERE " +
        "   o.order_id IS NULL").
      show
  }
}
