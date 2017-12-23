package _4_advance._4_1_word_count

import com.typesafe.config.ConfigFactory
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}

object WordCountUpdated {
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

    println("======= Word Count Program ========")
    sc.
      textFile(inputPath).
      flatMap((rec: String) => rec.split(" ")).
      map((word: String) => (word.toLowerCase, 1)).
      reduceByKey((total, ele) => total + ele, 1).
      saveAsTextFile(outputPath)
  }
}
