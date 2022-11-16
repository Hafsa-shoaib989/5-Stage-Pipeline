package practice1

import chisel3._
import org.scalatest.FreeSpec
import chiseltest._

class MEMWBTest extends FreeSpec with ChiselScalatestTester{
   "MEMWBTest test" in{
       test(new MEM_WB ()){c =>
         c.clock.step(1) 
       }
   }
}