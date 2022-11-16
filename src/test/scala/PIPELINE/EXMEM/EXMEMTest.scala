package practice1

import chisel3._
import org.scalatest.FreeSpec
import chiseltest._

class EXMEMTest extends FreeSpec with ChiselScalatestTester{
   "EXMEMTest test" in{
       test(new EX_MEM ()){c =>
         c.clock.step(1) 
       }
   }
}