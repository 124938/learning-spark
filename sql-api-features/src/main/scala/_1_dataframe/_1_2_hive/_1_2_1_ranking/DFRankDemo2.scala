package _1_dataframe._1_2_hive._1_2_1_ranking

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

object DFRankDemo2 {

  def main(args: Array[String]): Unit = {

    // Create instance of SparkConf
    val conf = new SparkConf().
      setAppName("Rank Demo 2 - Using DataFrame HiveContext").
      setMaster("local[2]")

    // Create instance of SparkContext
    val sc = new SparkContext(conf)

    // Create instance of HiveContext
    val hiveContext = new HiveContext(sc)

    hiveContext.
      setConf("spark.sql.shuffle.partitions", "2")

    println("***** Problem Statement : Find out top 5 revenue generated product with rank per day (include product id, product price, product category id) *****")
    hiveContext.
      sql(
        " SELECT " +
        "  * " +
        " FROM " +
        " ( "+
        "   SELECT " +
        "     FROM_UNIXTIME( CAST(order_date/1000 as BIGINT), 'YYYY-MM-dd') as order_date, " +
        "     product_category_id, " +
        "     product_id, " +
        "     product_name, " +
        "     product_price, " +
        "     ROUND(order_revenue, 3) as order_revenue, " +
        "     DENSE_RANK() OVER (PARTITION BY order_date ORDER BY order_revenue DESC) as product_rank " +
        "   FROM " +
        "   ( "+
        "     SELECT "+
        "       distinct * "+
        "     FROM "+
        "     ( "+
        "       SELECT " +
        "         o.order_date, "+
        "         p.product_category_id, "+
        "         p.product_id, "+
        "         p.product_name, " +
        "         p.product_price, " +
        "         sum(oi.order_item_subtotal) over (PARTITION BY o.order_date, p.product_id) as order_revenue " +
        "       FROM " +
        "         retail_db_avro.orders o JOIN retail_db_parquet.order_items oi ON (o.order_id = oi.order_item_order_id) " +
        "                                 JOIN retail_db_avro.products p ON (oi.order_item_product_id = p.product_id) " +
        "       WHERE " +
        "         o.order_status IN ('COMPLETE', 'CLOSED') " +
        "     ) q1 "+
        "   ) q2 "+
        "  ) q3 " +
        "  WHERE product_rank <= 5 "
      ).
      show(50)
  }
}
