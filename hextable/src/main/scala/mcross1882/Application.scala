package mcross1882.hextable

import java.io.File
import java.io.PrintWriter

object Application {
  protected val MaxRowCount = 15
  protected val MaxColCount = 15
  protected val LineLength  = 95
  
  protected val divider = ("-" * LineLength) + "\n"
  
  def main(args: Array[String]) {
    var temp: Int = 0
    val writer = new PrintWriter(new File("hextable.out"))
    
    writer.write("    ")
    for (col <- 1 to MaxColCount) {
      writer.write(f"| 0x$col%x ")
    }
    writer.write(f"|\n" + divider)
    
    for (row <- 1 to MaxRowCount) {
      writer.write(f"0x$row%x ")
      for (col <- 1 to MaxColCount) {
        temp = row * col
        writer.write(f"| $temp%03x ")
      }
      writer.write("|\n" + divider)
    }
    
    writer.flush
    writer.close
  }
}
