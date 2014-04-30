package mcross1882.minesweeper

import java.io.File
import java.io.PrintWriter
import java.util.Scanner
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Stack

object Application {

  protected val EmptyMarker    = '.'
  protected val MineMarker     = '*'
  protected val UsedMarker     = '0'
  protected val StartingMarker = 'c'

  protected val areaMatrix = Array(
    (-1, -1), (-1, 0), (-1, 1),
    (0, -1),  (0, 0),  (0, 1),
    (1, -1),  (1, 0),  (1, 1)
  )
  
  protected def shouldIgnore(row: Int, col: Int, grid: Array[Array[Char]]): Boolean = {
    row < 0 || col < 0 || row >= grid.length || col >= grid(row).length || (grid(row)(col) equals UsedMarker)
  }
  
  /**
   * Mark cells checks to see if an individual cell and all of its surrounding
   * cells that have not been checked can be marked. If they can it will set them
   * as used.
   */
  protected def markCells(row: Int, col: Int, grid: Array[Array[Char]]) {
    val stack = new Stack[(Int,Int)]
    var marker = UsedMarker
    var newRow, newCol = 0
    
    for (mod <- areaMatrix) {
      newRow = row + mod._1
      newCol = col + mod._2
      
      if (!shouldIgnore(newRow, newCol, grid)) {
        if (grid(newRow)(newCol).equals(EmptyMarker)) {
          stack.push((newRow, newCol))
        } else {
          marker = EmptyMarker
        }
      }
    }
    
    var cell = (0, 0)
    while (!stack.isEmpty) {
      cell = stack.pop
      grid(cell._1)(cell._2) = marker
    }
  }
  
  /**
   * Finds the first optimal position in the grid
   */
  protected def findStartingPosition(grid: Array[Array[Char]]) {
    var x, y, weight = 0
    var hWeight, hRow, hCol = 0
    
    for (row <- 0 until grid.length; col <- 0 until grid(row).length) {
      weight = 0
      
      for (mod <- areaMatrix) {
        x = row + mod._1
        y = col + mod._2
        if (!shouldIgnore(x, y, grid) && grid(x)(y).equals(UsedMarker)) {
          weight += 1
        }
      }
      
      if (weight >= hWeight) {
        hWeight = weight
        hRow    = row
        hCol    = col
      }
    }
    
    grid(hRow)(hCol) = StartingMarker
  }
  
  /**
   * Converts our one dimensional string into a 2 dimensional character array
   *
   * | e.g. 
   * | this 
   * | *.....**.
   * | becomes
   * | * . .
   * | . . .
   * | * * .
   *
   */
  protected def textToGrid(rows: Int, cols: Int, text: String): Array[Array[Char]] = {
    val buffer = new ArrayBuffer[Array[Char]](rows)
    for (row <- 0 until rows) {
      val offset = row * cols
      buffer append text.substring(offset, offset+cols).toCharArray
    }
    buffer.toArray
  }

  protected def buildGrid(rows: Int, cols: Int, mines: Int): String = {
    val gridString = "%s%s".format("*" * mines, "." * ((rows * cols) - mines))
    
    for (textGrid <- gridString.permutations) {
      var grid = textToGrid(rows, cols, textGrid)
      
      for (row <- 0 until rows; col <- 0 until cols) {
        markCells(row, col, grid)
      }
      
      if (grid.count(_.contains(EmptyMarker)) == 0) {
        findStartingPosition(grid)
        return grid.map(_.mkString.replace(UsedMarker, EmptyMarker)).mkString("\n")
      }
    }
    "Impossible"
  }
  
  protected def readFile(input: String, output: String) {
    val reader = new Scanner(new File(input))
    val writer = new PrintWriter(new File(output))
    
    for (index <- 1 to reader.nextInt) {
      writer.write("Case #%d:\n%s\n".format(index, buildGrid(reader.nextInt, reader.nextInt, reader.nextInt)))
    }
    
    writer.flush
    writer.close
    reader.close
  }
  
  def main(args: Array[String]) = readFile(args(0), args(1))
}
