package _1_dataframe._1_1_native._1_1_4_aggregation

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DFAggregationDemo3 {

  def main(args: Array[String]): Unit = {

    // Create instance of SparkConf
    val conf = new SparkConf().
      setAppName("Aggregation Demo 3 - Using DataFrame SQLContext").
      setMaster("local[2]")

    // Create instance of SparkContext
    val sc = new SparkContext(conf)

    // Create instance of SQLContext
    val sqlContext = new SQLContext(sc)
    sqlContext.
      setConf("spark.sql.shuffle.partitions", "2")

    // this is used to implicitly convert an RDD to DataFrame
    import sqlContext.implicits._

    // this is used to import
    import com.databricks.spark.avro._

    println("******** Problem Statement : Generate order revenue per order date & product name for NEW & COMPLETE orders only *******")

    // Create DataFrame for products
    val productsDF = sqlContext.
      read.
      avro("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/products/avro")

    // Print schema for DataFrame
    productsDF.
      printSchema

    // Create DataFrame for orders
    val ordersDF = sqlContext.
      read.
      avro("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/avro")

    // Print schema for DataFrame
    ordersDF.
      printSchema

    // Create DataFrame for order items
    val orderItemsDF = sqlContext.
      read.
      avro("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/order_items/avro")

    // Print schema from DataFrame
    orderItemsDF.
      printSchema()

    println("===== Approach 1 - Using DSL Way (groupBy, agg [sum, count], orderBy, select) ===")
    ordersDF.
      join(orderItemsDF, $"order_id" === $"order_item_order_id").
      join(productsDF, $"order_item_product_id" === $"product_id").
      groupBy(
        $"order_date",
        $"product_name"
      ).
      agg(
        org.apache.spark.sql.functions.round(org.apache.spark.sql.functions.sum($"order_item_subtotal") cast "FLOAT", 3) as "order_revenue"
      ).
      orderBy(
        $"order_date",
        $"order_revenue" desc
      ).
      select(
        org.apache.spark.sql.functions.from_unixtime(($"order_date" / 1000) cast "BIGINT", "YYYY-MM-dd") as "order_date",
        $"product_name",
        $"order_revenue"
      ).
      show(30)

    println("===== Approach 2 - Using SQL Way (GROUP BY, [SUM], ORDER BY) =====")
    productsDF.
      registerTempTable("PRODUCTS")

    ordersDF.
      registerTempTable("ORDERS")

    orderItemsDF.
      registerTempTable("ORDER_ITEMS")

    sqlContext.
      sql(
        " SELECT "+
        "   FROM_UNIXTIME(CAST(o.order_date / 1000 as BIGINT), 'YYYY-MM-dd') as order_date, "+
        "   p.product_name as product_name, "+
        "   ROUND(CAST(SUM(oi.order_item_subtotal) as FLOAT), 3) as order_revenue "+
        " FROM "+
        "   ORDERS o JOIN ORDER_ITEMS oi ON (o.order_id = oi.order_item_order_id) "+
        "            JOIN PRODUCTS p ON (oi.order_item_product_id = p.product_id) "+
        " GROUP BY "+
        "   o.order_date, p.product_name "+
        " ORDER BY "+
        "   o.order_date, order_revenue desc"
      ).
      show(30)
  }
}
