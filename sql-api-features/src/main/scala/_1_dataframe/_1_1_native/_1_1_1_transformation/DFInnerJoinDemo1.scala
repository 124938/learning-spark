package _1_dataframe._1_1_native._1_1_1_transformation

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DFInnerJoinDemo1 {

  def main(args: Array[String]): Unit = {

    // Create instance of SparkConf
    val conf = new SparkConf().
      setAppName("Join Demo 1 - Using DataFrame SQLContext").
      setMaster("local[2]")

    // Create instance of SparkContext
    val sc = new SparkContext(conf)

    // Create instance of SQLContext
    val sqlContext = new SQLContext(sc)
    sqlContext.
      setConf("spark.sql.shuffle.partitions", "2")

    // Below is used to convert RDD to DataFrame implicitly
    import sqlContext.implicits._

    println("***** Problem Statement : Generate order revenue per order date ******")

    // Create DataFrame for orders
    val ordersDF = sqlContext.
      read.
      parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/parquet")

    // Print schema
    ordersDF.
      printSchema

    // Create DataFrame for order items
    val orderItemsDF = sqlContext.
      read.
      parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/order_items/parquet")

    // Print schema
    orderItemsDF.
      printSchema

    println("===== Approach 1 - Using DSL Way (join, groupBy, agg [sum], orderBy, select) =====")
    ordersDF.
      join(orderItemsDF, ordersDF("order_id") === orderItemsDF("order_item_order_id")).
      groupBy($"order_date").
      agg(org.apache.spark.sql.functions.sum($"order_item_subtotal") as "order_revenue").
      orderBy($"order_date" desc).
      select(
        org.apache.spark.sql.functions.from_unixtime(($"order_date" / 1000) cast "BIGINT", "YYYY-MM-dd") as "order_date",
        org.apache.spark.sql.functions.round($"order_revenue" cast "FLOAT", 3) as "order_revenue").
      show(20)

    println("===== Approach 2 - Using SQL Way (JOIN, GROUP BY, [sum], ORDER BY, SELECT) =====")
    ordersDF.
      registerTempTable("ORDERS")

    orderItemsDF.
      registerTempTable("ORDER_ITEMS")

    sqlContext.
      sql(" SELECT "+
          "   FROM_UNIXTIME(CAST(o.order_date / 1000 as BIGINT), 'YYYY-MM-dd') as order_date, " +
          "   ROUND(CAST(SUM(oi.order_item_subtotal) as FLOAT), 3) as order_revenue " +
          " FROM "+
          "   ORDERS o JOIN ORDER_ITEMS oi ON (o.order_id = oi.order_item_order_id) "+
          " GROUP BY "+
          "   o.order_date "+
          " ORDER BY order_date DESC ").
      show(20)
  }
}
