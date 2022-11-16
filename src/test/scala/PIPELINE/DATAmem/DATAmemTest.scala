package practice1

import chisel3._
import org.scalatest.FreeSpec
import chiseltest._

class DATAmemTest extends FreeSpec with ChiselScalatestTester{
   "DATAmemTest test" in{
       test(new DataMem1 ( ) ){c =>
          c.io.dataIn.poke(2.S)
          c.io.addr.poke(4.U)
          c.io.mem_write.poke(1.B)
          c.clock.step(1) 
          c.io.mem_write.poke(0.B)
          c.io.mem_read.poke(1.B)
          c.io.addr.poke(4.U)
          c.clock.step(5)
          c.io.dataOut.expect(2.S)
       }
    }
}    
