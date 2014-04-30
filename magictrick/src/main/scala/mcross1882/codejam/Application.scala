package mcross1882.magictrick

import java.io.File
import java.io.PrintWriter
import java.util.Scanner
import scala.collection.mutable.ArrayBuffer

object Application {

  protected def readGrid(reader: Scanner): List[Int] = {
    val buffer = new ArrayBuffer[Int]
    for (row <- 1 to 4) {
      for (col <- 1 to 4) {
        buffer append reader.nextInt
      }
    }
    buffer.toList
  }
  
  protected def readRowOffset(reader: Scanner): Int = (reader.nextInt-1) * 4
  
  protected def printResult(testCase: Int, writer: PrintWriter, badMagician: Boolean, chosenCard: Int) {
    writer.write(f"Case #$testCase%d: ")
    
    if (badMagician) {
      writer.write("Bad magician!")
    } else if (chosenCard == -1) {
      writer.write("Volunteer cheated!")
    } else {
      writer.write(f"$chosenCard%d")
    }
    
    writer.write("\n")
  }
  
  protected def readFile(input: String, output: String) {
    val reader = new Scanner(new File(input))
    val writer = new PrintWriter(new File(output))
    
    val testCases = reader.nextInt
    var grid: List[Int] = null
    var row: List[Int] = null
    var compareRow: List[Int] = null
    var rowOffset: Int = -1
    
    var badMagician: Boolean = false
    var chosenCard: Int = -1
    
    println(f"Running $testCases%d...")
    
    for (index <- 1 to testCases) {
      badMagician = false
      chosenCard = -1
      
      // Read the first user choice and grid
      rowOffset = readRowOffset(reader)
      grid = readGrid(reader)
      
      // Slice off the chosen row
      row = grid.slice(rowOffset, rowOffset+4)
      
      println("Row")
      row foreach println
      
      //Read the second user choice and grid
      rowOffset = readRowOffset(reader)
      grid = readGrid(reader)
      
      grid.slice(rowOffset, rowOffset+4).foreach{ col =>
        if (row.contains(col)) {
          if (chosenCard != -1) {
            badMagician = true
          }
          chosenCard = col
        }
      }
      
      printResult(index, writer, badMagician, chosenCard)
    }
    
    writer.flush
    writer.close
    
    reader.close
  }
  
  def main(args: Array[String]) = readFile(args(0), args(1))
}
