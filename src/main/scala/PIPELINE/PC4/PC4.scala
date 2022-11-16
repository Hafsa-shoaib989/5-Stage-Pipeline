package practice1

import chisel3._
import chisel3.util._

class P_Counter ( ) extends Module {
    val io = IO (new Bundle {
        val pc = Input ( UInt (32.W ) )
        val out = Output ( UInt ( 32.W ) )
})
io.out := 0.U
io.out := io.pc + 4.U(32.W)
}
