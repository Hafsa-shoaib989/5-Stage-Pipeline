package practice1

import chisel3._
import chisel3.util._

class ID_EX extends Module {
    val io = IO(new Bundle {
        val rs1_in = Input (UInt(5.W))
        val rs2_in = Input (UInt(5.W))
        val rs1_data_in = Input (SInt(32.W))
        val rs2_data_in = Input (SInt(32.W))
        val imm = Input (SInt(32.W))
        val rd_in = Input (UInt(5.W))
        val func3_in = Input (UInt(3.W))
        val func7_in = Input (Bool())
        val ctrl_MemWr_in = Input (Bool())
        val ctrl_Branch_in = Input(Bool())
        val ctrl_MemRd_in = Input(Bool())
        val ctrl_Reg_W_in = Input(Bool())
        val ctrl_MemToReg_in = Input (Bool())
        val ctrl_AluOp_in = Input (UInt(3.W))
        val ctrl_OpA_in = Input (UInt(2.W))
        val ctrl_OpB_in = Input (Bool())
        val ctrl_nextpc_in = Input (UInt(2.W))
        val IFID_pc4_in = Input (UInt(32.W))

        val rs1_out = Output ((UInt(5.W)))
        val rs2_out = Output ((UInt(5.W)))
        val rs1_data_out = Output (SInt(32.W))
        val rs2_data_out = Output (SInt(32.W))
        val rd_out = Output (UInt(5.W))
        val imm_out = Output (SInt(32.W))
        val func3_out = Output (UInt(3.W))
        val func7_out = Output (Bool())
        val ctrl_MemWr_out = Output (Bool())
        val ctrl_Branch_out = Output(Bool())
        val ctrl_MemRd_out = Output (Bool())
        val ctrl_Reg_W_out = Output (Bool())
        val ctrl_MemToReg_out = Output (Bool())
        val ctrl_AluOp_out = Output (UInt(3.W))
        val ctrl_OpA_out = Output (UInt(2.W))
        val ctrl_OpB_out = Output (Bool())
        val ctrl_nextpc_out = Output (UInt(2.W))
        val IFID_pc4_out = Output (UInt(32.W))
})

val rs1_reg = RegInit(0.U(5.W))
val rs2_reg = RegInit(0.U(5.W))
val rs1_data_reg = RegInit(0.S(32.W))
val rs2_data_reg = RegInit(0.S(32.W))
val imm_reg = RegInit(0.S(32.W))
val rd_reg = RegInit(0.U(5.W))
val func3_reg = RegInit(0.U(3.W))
val func7_reg = RegInit(0.U(1.W))
val ctrl_MemWr_reg = RegInit(0.U(1.W))
val ctrl_MemRd_reg = RegInit(0.U(1.W))
val ctrl_Reg_W_reg = RegInit(0.U(1.W))
val ctrl_Branch_reg = RegInit(0.U(1.W))
val ctrl_MemToReg_reg = RegInit(0.U(1.W))
val ctrl_AluOp_reg = RegInit(0.U(3.W))
val ctrl_OpA_reg = RegInit(0.U(2.W))
val ctrl_OpB_reg = RegInit(0.U(1.W))
val ctrl_nextpc_reg = RegInit(0.U(2.W))
val IFID_pc4_reg = RegInit(0.U(32.W))

rs1_reg := io.rs1_in
rs2_reg := io.rs2_in
rs1_data_reg := io.rs1_data_in
rs2_data_reg := io.rs2_data_in
imm_reg := io.imm
rd_reg := io.rd_in
func3_reg := io.func3_in
func7_reg := io.func7_in
ctrl_MemWr_reg := io.ctrl_MemWr_in
ctrl_Branch_reg := io.ctrl_Branch_in
ctrl_MemRd_reg := io.ctrl_MemRd_in
ctrl_Reg_W_reg := io.ctrl_Reg_W_in
ctrl_MemToReg_reg := io.ctrl_MemToReg_in
ctrl_AluOp_reg := io.ctrl_AluOp_in
ctrl_OpA_reg := io.ctrl_OpA_in
ctrl_OpB_reg := io.ctrl_OpB_in
ctrl_nextpc_reg := io.ctrl_nextpc_in
IFID_pc4_reg := io.IFID_pc4_in

io.rs1_out := rs1_reg
io.rs2_out := rs2_reg
io.rs1_data_out := rs1_data_reg
io.rs2_data_out := rs2_data_reg
io.imm_out := imm_reg
io.rd_out := rd_reg
io.func3_out := func3_reg
io.func7_out := func7_reg
io.ctrl_MemWr_out := ctrl_MemWr_reg
io.ctrl_Branch_out := ctrl_Branch_reg
io.ctrl_MemRd_out := ctrl_MemRd_reg
io.ctrl_Reg_W_out := ctrl_Reg_W_reg
io.ctrl_MemToReg_out := ctrl_MemToReg_reg
io.ctrl_AluOp_out := ctrl_AluOp_reg
io.ctrl_OpA_out := ctrl_OpA_reg
io.ctrl_OpB_out := ctrl_OpB_reg
io.ctrl_nextpc_out := ctrl_nextpc_reg
io.IFID_pc4_out := IFID_pc4_reg
}
