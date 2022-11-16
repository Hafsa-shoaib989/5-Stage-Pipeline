package practice1

import chisel3._
import org.scalatest.FreeSpec
import chiseltest._

class STRUCTURALhazardTest extends FreeSpec with ChiselScalatestTester{
   "STRUCTURALhazardTest test" in{
       test(new BranchForward ()){c =>
         c.clock.step(1) 
       }
   }
}
