package practice1

import chisel3._
import org.scalatest.FreeSpec
import chiseltest._ 

class INSTRmemTest extends FreeSpec with ChiselScalatestTester{
   "INSTRmemTest test" in{
       test(new InstMem ("C:/Users/Muhammad Sameed/Desktop/Desktop clean/Hafsa/Merl/input1.txt" ) ){c =>
          c.io.addr.poke(0.U)
          c.clock.step(1) 
          c.io.data.expect("h00200293".U)

       }
    }
}    
