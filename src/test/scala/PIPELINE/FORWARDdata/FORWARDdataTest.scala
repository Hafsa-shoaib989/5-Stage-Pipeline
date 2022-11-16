package practice1

import chisel3._
import org.scalatest.FreeSpec
import chiseltest._

class FORWARDdataTest extends FreeSpec with ChiselScalatestTester{
   "FORWARDdataTest test" in{
       test(new ForwardData ()){c =>
         c.clock.step(1) 
       }
   }
}