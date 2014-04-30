package mcross1882.cookieclicker

import java.io.File
import java.io.PrintWriter
import java.util.Scanner
import scala.collection.mutable.ArrayBuffer

object Application {
  
  protected val BaseCookieRate = 2.0d
  
  protected def printResults(testCase: Int, writer: PrintWriter, minTime: Double) = writer.write(f"Case #$testCase%d: $minTime%.7f\n")
  
  protected def readTest(testCase: Int, farmCost: Double, farmRate: Double, winningTotal: Double): Double = {
    var currentRate = BaseCookieRate
    var currentTime: Double = 0
    var finished: Boolean = false
    
    while (!finished) {
      if (((farmCost / currentRate) + (winningTotal / (currentRate+farmRate))) < (winningTotal / currentRate)) {
        currentTime += farmCost / currentRate
        currentRate += farmRate
      } else {
        currentTime += (winningTotal / currentRate)
        finished = true
      }
    }
    currentTime
  }
  
  protected def readFile(input: String, output: String) {
    val reader = new Scanner(new File(input))
    val writer = new PrintWriter(new File(output))
    
    val testCases = reader.nextInt
    for (index <- 1 to testCases) {
      printResults(index, writer, readTest(index, reader.nextDouble, reader.nextDouble, reader.nextDouble))
    }
    
    writer.flush
    writer.close
    reader.close
  }
  
  def main(args: Array[String]) = readFile(args(0), args(1))
}
