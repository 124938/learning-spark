package _1_dataframe._1_1_native._1_1_5_set

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DFUnionAllDemo {

  def main(args: Array[String]): Unit = {

    // Create instance of SparkConf
    val conf = new SparkConf().
      setAppName("Union Demo - Using DataFrame SQLContext").
      setMaster("local[2]")

    // Create instance of SparkContext
    val sc = new SparkContext(conf)

    // Create instance of SQLContext
    val sqlContext = new SQLContext(sc)

    sqlContext.
      setConf("spark.sql.shuffle.partitions", "2")

    // Below is used to convert RDD to DataFrame
    import sqlContext.implicits._

    println("***** Problem Statement : Find out all distinct product ids sold on '25-Dec-2013' & '26-Dec-2013' *****")

    // Create DataFrame from orders
    val ordersDF = sqlContext.
      read.
      parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/orders/parquet")

    // Create DataFrame from order items
    val orderItemsDF = sqlContext.
      read.
      parquet("/home/asus/source_code/github/124938/learning-spark/sql-api-features/src/main/resources/retail_db/order_items/parquet")

    println("===== Approach 1 - DSL Way (join, unionAll) =====")

    // Create DataFrame from orders present on '25-Dec-2013'
    val orders25dec2013DF = ordersDF.
      filter(
        org.apache.spark.sql.functions.from_unixtime(($"order_date" / 1000) cast "BIGINT", "YYYY-MM-dd") contains "2013-12-25"
      ).
      select(
        $"order_id",
        org.apache.spark.sql.functions.from_unixtime(($"order_date" / 1000) cast "BIGINT", "YYYY-MM-dd") as "order_date",
        $"order_status"
      )

    orders25dec2013DF.
      printSchema()

    orders25dec2013DF.
      show(20)

    // Create DataFrame from orders present on '26-Dec-2013'
    val orders26dec2013DF = ordersDF.
      filter(
        org.apache.spark.sql.functions.from_unixtime(($"order_date" / 1000) cast "BIGINT", "YYYY-MM-dd") contains "2013-12-26"
      ).
      select(
        $"order_id",
        org.apache.spark.sql.functions.from_unixtime(($"order_date" / 1000) cast "BIGINT", "YYYY-MM-dd") as "order_date",
        $"order_status"
      )

    orders26dec2013DF.
      printSchema()

    orders26dec2013DF.
      show(20)

    // Join 1
    val orders25dec2013OrderItemsJoinDF = orders25dec2013DF.
      join(orderItemsDF, $"order_id" === $"order_item_order_id").
      select(
        $"order_item_product_id" as "product_id"
      )

    orders25dec2013OrderItemsJoinDF.
      printSchema

    orders25dec2013OrderItemsJoinDF.
      show(20)

    // Join 2
    val orders26dec2013OrderItemsJoinDF = orders26dec2013DF.
      join(orderItemsDF, $"order_id" === $"order_item_order_id").
      select(
        $"order_item_product_id" as "product_id"
      )

    orders26dec2013OrderItemsJoinDF.
      printSchema

    orders26dec2013OrderItemsJoinDF.
      show(20)

    // Union all
    orders25dec2013OrderItemsJoinDF.
      unionAll(orders26dec2013OrderItemsJoinDF).
      distinct.
      orderBy($"product_id").
      show(100)

    println("===== Approach 2 - SQL Way (join, unionAll) =====")
    ordersDF.
      registerTempTable("ORDERS")

    orderItemsDF.
      registerTempTable("ORDER_ITEMS")

    sqlContext.
      sql(
        " ( "+
        "   SELECT "+
        "     order_item_product_id as product_id "+
        "   FROM "+
        "     ORDERS o JOIN ORDER_ITEMS oi ON (o.order_id = oi.order_item_order_id) "+
        "   WHERE "+
        "     FROM_UNIXTIME(CAST(o.order_date / 1000 as BIGINT),'YYYY-MM-dd') like '2013-12-25' "+
        " ) "+
        " UNION "+
        " (  "+
        "   SELECT "+
        "     order_item_product_id as product_id "+
        "   FROM "+
        "     ORDERS o JOIN ORDER_ITEMS oi ON (o.order_id = oi.order_item_order_id) "+
        "   WHERE "+
        "     FROM_UNIXTIME(CAST(o.order_date / 1000 as BIGINT),'YYYY-MM-dd') like '2013-12-26' "+
        "  ) "
      ).
      distinct.
      orderBy($"product_id" asc).
      show(100)
  }
}
