package _4_advance._4_3_prod_revenue_for_month

import com.typesafe.config.ConfigFactory
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

object ProductRevenueForMonthUpdated {

  def main(args: Array[String]): Unit = {
    println("========= Get Program Arguments ===========")
    if (args.length != 4) {
      println("Usage : <input_folder_path> <output_path> <month in yyyy-mm format> <local OR dev OR prd>")
      throw new RuntimeException("Arguments are not valid!!")
    }

    // Load property file from classpath
    val props = ConfigFactory.load()

    // Assign arguments to variables
    val inputFolderPath = args(0)
    val outputPath = args(1)
    val month = args(2)
    val environment = args(3)
    val executionMode = props.getConfig(environment).getString("execution-mode")

    println( "========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName(s"Product revenue for month - $month updated").
      setMaster(executionMode)

    val sc = new SparkContext(conf)

    println( "========= Validation on file path ========")

    // Validate input file path
    val fs = FileSystem.get(sc.hadoopConfiguration)

    val productsPath = inputFolderPath + "/products/part-00000"
    if (!fs.exists(new Path(productsPath))) {
      throw new RuntimeException(s"$productsPath does not exist")
    }

    val ordersPath = inputFolderPath + "/orders"
    if (!fs.exists(new Path(ordersPath))) {
      throw new RuntimeException(s"$ordersPath does not exist")
    }

    val orderItemsPath = inputFolderPath + "/order_items"
    if (!fs.exists(new Path(orderItemsPath))) {
      throw new RuntimeException(s"$orderItemsPath does not exist")
    }

    // Remove output path, if exists
    if (fs.exists(new Path(outputPath))) {
      fs.delete(new Path(outputPath), true)
    }

    println("======= Create accumulators  ========")
    val ordersCountForMonthAcc = sc.
      accumulator[Int](0, s"Number of orders for month -> $month")

    val orderItemsCountForMonthAcc = sc.
      accumulator[Int](0, s"Number of order items for month -> $month")

    println("======= Create broadcast variable ========")
    val productsMap = Source.
      fromFile(productsPath).
      getLines.
      toList.
      map((rec: String) => {
        val recArray = rec.split(",")
        (recArray(0).toInt, recArray(2))
      }).
      toMap

    val productsMapBrd = sc.
      broadcast[Map[Int, String]](productsMap)

    println("======= Create orders for month RDD by filtering provided month data ========")
    val ordersForMonthRdd = sc.
      textFile(ordersPath).
      filter((rec: String) => rec.split(",")(1).contains(month)).
      map((rec: String) => {
        ordersCountForMonthAcc += 1
        (rec.split(",")(0).toInt, rec)
      })

    println("======= Create order items RDD ========")
    val orderItemsRdd = sc.
      textFile(orderItemsPath).
      map((rec: String) => (rec.split(",")(1).toInt, rec))

    println("======= Create product revenue RDD by Joining orders for month RDD with order items RDD + performing aggregation based on order_item_product_id + order_item_sub_total ========")
    val productRevenueRDD = orderItemsRdd.
      join(ordersForMonthRdd).
      map((t: (Int, (String, String))) => {
        val orderItemsRecArray = t._2._1.split(",")
        val ordersRecArray = t._2._2.split(",")
        ((orderItemsRecArray(2).toInt, ordersRecArray(3).toUpperCase), orderItemsRecArray(4).toFloat)
      }).
      reduceByKey((totalRev, eleRev) => totalRev + eleRev, 1).
      map((t: ((Int, String), Float)) => {
        orderItemsCountForMonthAcc += 1
        (t._1._1, (t._1._2, t._2))
      })

    println("======= Save products revenue details by Joining product revenue RDD with products RDD ========")
    productRevenueRDD.
      map((t: (Int, (String, Float))) => {
        (productsMapBrd.value.get(t._1).get, t._2._1, t._2._2)
      }).
      map((t: (String, String, Float)) => t.productIterator.mkString("\t")).
      saveAsTextFile(outputPath)
  }
}
