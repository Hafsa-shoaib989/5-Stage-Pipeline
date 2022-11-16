package practice1

import chisel3._
import org.scalatest.FreeSpec
import chiseltest._

class IFIDTest extends FreeSpec with ChiselScalatestTester{
   "IFIDTest test" in{
       test(new IF_ID ()){c =>
         c.clock.step(1) 
       }
   }
}