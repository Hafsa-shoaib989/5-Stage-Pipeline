package practice1

import chisel3._
import org.scalatest.FreeSpec
import chiseltest._

class BRANCHforwardTest extends FreeSpec with ChiselScalatestTester{
   "BRANCHforwardTest test" in{
       test(new BranchForward ()){c =>
         c.clock.step(1) 
       }
   }
}
