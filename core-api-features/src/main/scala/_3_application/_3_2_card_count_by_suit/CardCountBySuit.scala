package _3_application._3_2_card_count_by_suit

import com.typesafe.config.ConfigFactory
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}

object CardCountBySuit {

  def main(args: Array[String]): Unit = {
    println("========= Get Program Arguments ===========")
    if (args.length != 3) {
      println("Usage : <input_path> <output_path> <local OR dev OR prd>")
      throw new RuntimeException("Arguments are not valid!!")
    }

    // Load property file from classpath
    val props = ConfigFactory.load()

    // Assign arguments to variables
    val inputPath = args(0)
    val outputPath = args(1)
    val environment = args(2)
    val executionMode = props.getConfig(environment).getString("execution-mode")

    println( "========= Creating SparkContext ========")
    val conf = new SparkConf().
      setAppName("Word Count").
      setMaster(executionMode)

    val sc = new SparkContext(conf)

    println( "========= Validation on file path ========")

    // Validate input file path
    val fs = FileSystem.get(sc.hadoopConfiguration)

    if (!fs.exists(new Path(inputPath))) {
      throw new RuntimeException(s"$inputPath does not exist")
    }

    // Remove output path, if exists
    if (fs.exists(new Path(outputPath))) {
      fs.delete(new Path(outputPath), true)
    }

    println("======= Card Count By Suit Logic ========")
    sc.
      textFile(inputPath).
      map((rec: String) => (rec.split("\\|")(1), 1)).
      reduceByKey((total, ele) => total + ele).
      saveAsTextFile(outputPath)
  }
}
