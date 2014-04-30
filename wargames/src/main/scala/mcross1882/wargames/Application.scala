package mcross1882.wargames

import java.io.File
import java.io.PrintWriter
import java.util.Scanner
import scala.collection.mutable.ArrayBuffer

object Application {

  protected def calcDeceitfulWar(naomi: Array[Double], ken: Array[Double]): Int = {
    var points: Int = 0
    var kenMutable = ken
    var naomiMutable = naomi
    
    while (!naomiMutable.isEmpty) {
      println(f"Comparing " + (naomiMutable.head - 0.1d) + " to " + kenMutable.head)
      
      if (naomiMutable.head - 0.1d > kenMutable.head && naomiMutable.drop(1).exists(_ > kenMutable.head)) {
        points += 1
        naomiMutable = naomiMutable.map{
          if (_ > kenMutable.head) {
            var head = kenMutable.head
            kenMutable.drop(1)
          }
          head
        }
      } else if (naomiMutable.head > kenMutable.head) {
        points += 1
      }
      kenMutable = kenMutable.dropRight(1)
    }
    points
  }
  
  protected def calcWar(naomi: Array[Double], ken: Array[Double]): Int = {
    var points: Int = 0
    var kenMutable = ken
    for (n <- naomi) {
      if (n > kenMutable.head) {
        points += 1
        kenMutable = kenMutable.dropRight(1)
      } else {
        kenMutable = kenMutable.drop(1)
      }
    }
    points
  }
  
  protected def readBlocks(blockCount: Int, reader: Scanner): Array[Double] = {
    val buffer = new ArrayBuffer[Double]
    for (index <- 1 to blockCount) {
      buffer append reader.nextDouble
    }
    buffer.toArray.sortWith(_ > _)
  }
  
  protected def readFile(input: String, output: String) {
    val reader = new Scanner(new File(input))
    val writer = new PrintWriter(new File(output))
   
    val testCases = reader.nextInt
    var naomi: Array[Double] = null
    var ken: Array[Double] = null
    var blockCount: Int = 0
    
    for (index <- 1 to testCases) {
      blockCount = reader.nextInt
      naomi = readBlocks(blockCount, reader)
      ken = readBlocks(blockCount, reader)
      writer.write(f"Case #$index%d: " + calcDeceitfulWar(naomi, ken) + " " + calcWar(naomi, ken) + "\n")
    }
    
    writer.flush
    writer.close
    reader.close
  }
  
  def main(args: Array[String]) = readFile(args(0), args(1))
}
