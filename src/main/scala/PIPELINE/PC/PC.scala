package practice1

import chisel3._
import chisel3.util._

class PC_1 ( ) extends Module {
    val io = IO (new Bundle {
        val in = Input ( SInt (32.W ) )
        val out = Output ( SInt ( 32.W ) )
})
val reg = RegInit(0.S(32.W))
io.out := reg
reg := io.in
}