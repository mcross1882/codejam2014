package mcross1882.codejam2014.chargingchaos

import java.io.{File, PrintWriter}
import java.util.Scanner
import scala.collection.mutable.ArrayBuffer

object Application {
  
  protected var _buffer = new ArrayBuffer[String]

  def main(args: Array[String]) {
    val reader = new Scanner(new File(args(0)))
    val writer = new PrintWriter(new File(args(1)))
    
    val testCases = reader.nextInt
    var outletCount: Int = 0
    var outletSize: Int = 0
    
    for (index <- 1 to testCases) {
      outletCount = reader.nextInt
      outletSize = reader.nextInt
      
      val circuits = removeCompletedCircuits(readList(outletCount, reader), readList(outletCount, reader))
      var flips: Int = 0
      
      for (index <- 0 until circuits._1.length) {
        flips += countSwitches(circuits._1(index), circuits._2(index))
      }
      
      if (flips >= outletSize) {
        writer.write(f"Case #$index%d: NOT POSSIBLE\n")
      } else {
        writer.write(f"Case #$index%d: $flips%d\n")
      }
    }
    
    writer.flush
    writer.close
    reader.close
  }
  
  protected def readList(numberOfOutlets: Int, reader: Scanner): Array[String] = {
    _buffer.clear
    for (index <- 1 to numberOfOutlets) {
      _buffer.append(reader.next)
    }
    _buffer.toArray
  }
  
  protected def removeCompletedCircuits(plugs: Array[String], outlets: Array[String]): (Array[String], Array[String]) = {
    val remainingPlugs = plugs.filterNot(outlets.contains)
    val remainingOutlets = outlets.filterNot(plugs.contains)
    (remainingPlugs.sortWith(_ < _), remainingOutlets.sortWith(_ < _))
  }
  
  protected def countSwitches(plug: String, outlet: String): Int = {
    var count: Int = 0
    for (index <- 0 until plug.length) {
      if (plug(index) != outlet(index)) {
        count += 1
      }
    }
    count
  }
}
