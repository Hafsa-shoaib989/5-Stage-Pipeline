package practice1

import chisel3._
import chisel3.util._

class PIPELINE extends Module {
    val io = IO(new Bundle {
        val out = Output ( SInt(4.W) )
        val reg_Wdata_out = Output (SInt(32.W))
        val reg_out = Output (UInt(32.W))
        val data_mem_addr = Output (UInt(32.W))
        val data_mem_dataIn = Output (SInt(32.W))
        val imme_out = Output (SInt(32.W))
})

val PC_1_module = Module(new PC_1)
val P_Counter_module = Module(new P_Counter)
val InstMem_module =  Module(new InstMem ("C:/Users/Muhammad Sameed/Desktop/Desktop clean/Hafsa/Merl/input1.txt"))
val control_module = Module(new control)
val Immd_module =  Module(new Immd)
val Register_module = Module(new Register)
val AluControl_module = Module(new AluControl)
dontTouch(AluControl_module.io)
val ALU_module = Module(new ALU)
dontTouch(ALU_module.io)
val Branch_module = Module(new Branch)
val DataMem1_module = Module(new DataMem1)
val Jalr1_module = Module(new Jalr1)
val IF_ID_module = Module(new IF_ID)
val ID_EX_module = Module(new ID_EX)
val EX_MEM_module = Module(new EX_MEM)
val MEM_WB_module = Module(new MEM_WB)
val ForwardData_module = Module(new ForwardData)
val HazardDetection_module = Module(new HazardDetection)
val BranchForward_module = Module(new BranchForward)
val StructuralHazard_module = Module(new StructuralHazard)


val d = Wire(SInt(32.W))

// FETCH
val e = MuxLookup (HazardDetection_module.io.pc_forward, 0.S, Array (
    (0.U) -> P_Counter_module.io.out.asSInt,
    (1.U) -> HazardDetection_module.io.pc_out))

PC_1_module.io.in := e
P_Counter_module.io.pc := PC_1_module.io.out.asUInt
InstMem_module.io.addr := PC_1_module.io.out(21, 2)

val f = MuxLookup (HazardDetection_module.io.inst_forward, 0.S, Array (
    (0.U) -> PC_1_module.io.out,
    (1.U) -> HazardDetection_module.io.current_pc_out))

val g = MuxLookup (HazardDetection_module.io.inst_forward, 0.U, Array (
    (0.U) -> InstMem_module.io.data,
    (1.U) -> HazardDetection_module.io.inst_out))


// IF_ID PIPELINE
IF_ID_module.io.pc_in := PC_1_module.io.out
IF_ID_module.io.pc4_in := P_Counter_module.io.out
IF_ID_module.io.mux_f_pc_in := f
IF_ID_module.io.mux_g_inst_in := g


// DECODE
Immd_module.io.pc := IF_ID_module.io.mux_f_pc_out.asUInt
control_module.io.opcode := IF_ID_module.io.mux_g_inst_out(6, 0)
Register_module.io.rs1 := Mux(control_module.io.opcode === 51.U || control_module.io.opcode === 19.U || control_module.io.opcode === 35.U || control_module.io.opcode === 3.U || control_module.io.opcode === 99.U || control_module.io.opcode === 103.U, IF_ID_module.io.mux_g_inst_out(19, 15), 0.U )
Register_module.io.rs2 := Mux(control_module.io.opcode === 51.U || control_module.io.opcode === 35.U || control_module.io.opcode === 99.U, IF_ID_module.io.mux_g_inst_out(24, 20), 0.U)
Immd_module.io.instr := IF_ID_module.io.mux_g_inst_out

Register_module.io.reg_write := control_module.io.reg_write 

val a = MuxLookup (control_module.io.extend, 0.S, Array (
    (0.U) -> Immd_module.io.I_type,
    (1.U) -> Immd_module.io.S_type,
    (2.U) -> Immd_module.io.U_type))

StructuralHazard_module.io.rs1 := IF_ID_module.io.mux_g_inst_out(19, 15)
StructuralHazard_module.io.rs2 := IF_ID_module.io.mux_g_inst_out(24, 20)
StructuralHazard_module.io.MEM_WB_regWr := MEM_WB_module.io.EXMEM_REG_W
StructuralHazard_module.io.MEM_WB_Rd := MEM_WB_module.io.MEMWB_rd_out

ID_EX_module.io.rs1_data_in := MuxLookup (StructuralHazard_module.io.fwd_rs1, 0.S, Array (
    (0.U) -> Register_module.io.rdata1,
    (1.U) -> Register_module.io.w_data))

ID_EX_module.io.rs2_data_in := MuxLookup (StructuralHazard_module.io.fwd_rs2, 0.S, Array (
    (0.U) -> Register_module.io.rdata2,
    (1.U) -> Register_module.io.w_data))

when(HazardDetection_module.io.ctrl_forward === "b1".U) {
    ID_EX_module.io.ctrl_MemWr_in := 0.U
    ID_EX_module.io.ctrl_MemRd_in := 0.U
    ID_EX_module.io.ctrl_MemToReg_in := 0.U
    ID_EX_module.io.ctrl_Reg_W_in := 0.U
    ID_EX_module.io.ctrl_AluOp_in := 0.U
    ID_EX_module.io.ctrl_OpB_in := 0.U
    ID_EX_module.io.ctrl_Branch_in := 0.U
    ID_EX_module.io.ctrl_nextpc_in := 0.U
}.otherwise {
    ID_EX_module.io.ctrl_MemWr_in := control_module.io.mem_write
    ID_EX_module.io.ctrl_MemRd_in := control_module.io.mem_read
    ID_EX_module.io.ctrl_MemToReg_in := control_module.io.men_to_reg
    ID_EX_module.io.ctrl_Reg_W_in := control_module.io.reg_write 
    ID_EX_module.io.ctrl_AluOp_in := control_module.io.alu_operation
    ID_EX_module.io.ctrl_OpB_in := control_module.io.operand_B
    ID_EX_module.io.ctrl_Branch_in := control_module.io.branch
    ID_EX_module.io.ctrl_nextpc_in := control_module.io.next_pc_sel
}

HazardDetection_module.io.IF_ID_inst := IF_ID_module.io.mux_g_inst_out
HazardDetection_module.io.ID_EX_memRead := ID_EX_module.io.ctrl_MemRd_out
HazardDetection_module.io.ID_EX_rd := ID_EX_module.io.rd_out
HazardDetection_module.io.pc_in := IF_ID_module.io.pc4_out.asSInt
HazardDetection_module.io.current_pc := IF_ID_module.io.mux_f_pc_out

MEM_WB_module.io.EXMEM_MEMRD := EX_MEM_module.io.EXMEM_memRd_out

BranchForward_module.io.ID_EX_RD := ID_EX_module.io.rd_out
BranchForward_module.io.EX_MEM_RD := EX_MEM_module.io.EXMEM_rd_out 
BranchForward_module.io.MEM_WB_RD := MEM_WB_module.io.MEMWB_rd_out
BranchForward_module.io.ID_EX_memRd := ID_EX_module.io.ctrl_MemRd_out
BranchForward_module.io.EX_MEM_memRd := EX_MEM_module.io.EXMEM_memRd_out
BranchForward_module.io.MEM_WB_memRd := MEM_WB_module.io.MEMWB_memRd_out
BranchForward_module.io.rs1 := IF_ID_module.io.mux_g_inst_out(19, 15)
BranchForward_module.io.rs2 := IF_ID_module.io.mux_g_inst_out(24, 20)
BranchForward_module.io.ctrl_branch := control_module.io.branch

Branch_module.io.arg_x := MuxLookup (BranchForward_module.io.forward_rs1, 0.S, Array (
    (0.U) -> Register_module.io.rdata1,
    (1.U) -> ALU_module.io.out, 
    (2.U) -> EX_MEM_module.io.EXMEM_alu_out, 
    (3.U) -> Register_module.io.w_data, 
    (4.U) -> DataMem1_module.io.dataOut, 
    (5.U) -> Register_module.io.w_data,
    (6.U) -> Register_module.io.rdata1,
    (7.U) -> Register_module.io.rdata1,
    (8.U) -> Register_module.io.rdata1,
    (9.U) -> Register_module.io.rdata1,
    (10.U) -> Register_module.io.rdata1))

// for JALR
Jalr1_module.io.rdata1 := MuxLookup (BranchForward_module.io.forward_rs1, 0.U, Array (
    (0.U) -> Register_module.io.rdata1.asUInt,
    (1.U) -> Register_module.io.rdata1.asUInt, 
    (2.U) -> Register_module.io.rdata1.asUInt, 
    (3.U) -> Register_module.io.rdata1.asUInt, 
    (4.U) -> Register_module.io.rdata1.asUInt, 
    (5.U) -> Register_module.io.rdata1.asUInt,
    (6.U) -> ALU_module.io.out.asUInt,
    (7.U) -> EX_MEM_module.io.EXMEM_alu_out.asUInt,
    (8.U) -> Register_module.io.w_data.asUInt,
    (9.U) -> DataMem1_module.io.dataOut.asUInt,
    (10.U) -> Register_module.io.w_data.asUInt))

Jalr1_module.io.imme := a.asUInt

Branch_module.io.arg_y := MuxLookup (BranchForward_module.io.forward_rs2, 0.S, Array (
    (0.U) -> Register_module.io.rdata2,
    (1.U) -> ALU_module.io.out, 
    (2.U) -> EX_MEM_module.io.EXMEM_alu_out, 
    (3.U) -> Register_module.io.w_data, 
    (4.U) -> DataMem1_module.io.dataOut, 
    (5.U) -> Register_module.io.w_data))

Branch_module.io.fnct3 := IF_ID_module.io.mux_g_inst_out(14, 12)
Branch_module.io.branch := control_module.io.branch

when(HazardDetection_module.io.pc_forward === 1.B) {
    PC_1_module.io.in := HazardDetection_module.io.pc_out
}.otherwise {
    when(control_module.io.next_pc_sel === "b01".U) {
        when(Branch_module.io.br_taken === 1.B && control_module.io.branch === 1.B) {
            PC_1_module.io.in := Immd_module.io.SB_type
            IF_ID_module.io.pc_in := 0.S
            IF_ID_module.io.pc4_in := 0.U
            IF_ID_module.io.mux_f_pc_in := 0.S
            IF_ID_module.io.mux_g_inst_in := 0.U
        }.otherwise {
            PC_1_module.io.in := P_Counter_module.io.out.asSInt
        }
    }.elsewhen(control_module.io.next_pc_sel === "b10".U) {
        PC_1_module.io.in := Immd_module.io.UJ_type
        IF_ID_module.io.pc_in := 0.S
        IF_ID_module.io.pc4_in := 0.U
        IF_ID_module.io.mux_f_pc_in := 0.S
        IF_ID_module.io.mux_g_inst_in := 0.U
    }.elsewhen(control_module.io.next_pc_sel === "b11".U) {
        PC_1_module.io.in := Jalr1_module.io.out.asSInt
        IF_ID_module.io.pc_in := 0.S
        IF_ID_module.io.pc4_in := 0.U
        IF_ID_module.io.mux_f_pc_in := 0.S
        IF_ID_module.io.mux_g_inst_in := 0.U
    }.otherwise {
        PC_1_module.io.in := P_Counter_module.io.out.asSInt
    }
}


// ID_EX PIPELINE
ID_EX_module.io.rs1_in := Register_module.io.rs1
ID_EX_module.io.rs2_in := Register_module.io.rs2
ID_EX_module.io.imm := a 
ID_EX_module.io.func3_in := IF_ID_module.io.mux_g_inst_out(14, 12)
ID_EX_module.io.func7_in := IF_ID_module.io.mux_g_inst_out(30)
ID_EX_module.io.rd_in := IF_ID_module.io.mux_g_inst_out(11, 7)


// EXECUTION
ForwardData_module.io.IDEX_rs1 := ID_EX_module.io.rs1_out
ForwardData_module.io.IDEX_rs2 := ID_EX_module.io.rs2_out
ForwardData_module.io.EXMEM_rd := EX_MEM_module.io.EXMEM_rd_out
ForwardData_module.io.EXMEM_regWr := EX_MEM_module.io.EXMEM_reg_w_out
ForwardData_module.io.MEMWB_rd := MEM_WB_module.io.MEMWB_rd_out
ForwardData_module.io.MEMWB_regWr := MEM_WB_module.io.MEMWB_reg_w_out

ID_EX_module.io.ctrl_OpA_in := control_module.io.operand_A
ID_EX_module.io.IFID_pc4_in := IF_ID_module.io.pc4_out

when (ID_EX_module.io.ctrl_OpA_out === "b01".U) {
    ALU_module.io.in_A := ID_EX_module.io.IFID_pc4_out.asSInt
}.otherwise {
    when(ForwardData_module.io.forward_a === "b00".U) {
        ALU_module.io.in_A := ID_EX_module.io.rs1_data_out
    }.elsewhen(ForwardData_module.io.forward_a === "b01".U) {
        ALU_module.io.in_A := d
    }.elsewhen(ForwardData_module.io.forward_a === "b10".U) {
        ALU_module.io.in_A := EX_MEM_module.io.EXMEM_alu_out
    }.otherwise {
        ALU_module.io.in_A := ID_EX_module.io.rs1_data_out
    }
  }

val b = MuxLookup (ForwardData_module.io.forward_b, 0.S, Array (
    (0.U) -> ID_EX_module.io.rs2_data_out,
    (1.U) -> d,
    (2.U) -> EX_MEM_module.io.EXMEM_alu_out))

ALU_module.io.in_B := MuxLookup (ID_EX_module.io.ctrl_OpB_out, 0.S, Array (
    (0.U) -> b,
    (1.U) -> ID_EX_module.io.imm_out ))

AluControl_module.io.aluOp := ID_EX_module.io.ctrl_AluOp_out 
 
AluControl_module.io.func3 := ID_EX_module.io.func3_out
AluControl_module.io.func7 := ID_EX_module.io.func7_out
EX_MEM_module.io.IDEX_rd := ID_EX_module.io.rd_out
ALU_module.io.alu_Op := AluControl_module.io.out


// EX_MEM PIPELINE
EX_MEM_module.io.IDEX_MEMRD := ID_EX_module.io.ctrl_MemRd_out 
EX_MEM_module.io.IDEX_MEMWR := ID_EX_module.io.ctrl_MemWr_out
EX_MEM_module.io.IDEX_MEMTOREG := ID_EX_module.io.ctrl_MemToReg_out
EX_MEM_module.io.IDEX_REG_W := ID_EX_module.io.ctrl_Reg_W_out
 
EX_MEM_module.io.IDEX_rs2 := b
EX_MEM_module.io.alu_out := ALU_module.io.out


// MEMORY
DataMem1_module.io.mem_read := EX_MEM_module.io.EXMEM_memRd_out 
DataMem1_module.io.mem_write := EX_MEM_module.io.EXMEM_memWr_out
MEM_WB_module.io.EXMEM_MEMTOREG := EX_MEM_module.io.EXMEM_memToReg_out
MEM_WB_module.io.EXMEM_REG_W := EX_MEM_module.io.EXMEM_reg_w_out
DataMem1_module.io.dataIn := EX_MEM_module.io.EXMEM_rs2_out
MEM_WB_module.io.EXMEM_rd := EX_MEM_module.io.EXMEM_rd_out
DataMem1_module.io.addr := EX_MEM_module.io.EXMEM_alu_out.asUInt

Register_module.io.w_reg := MEM_WB_module.io.MEMWB_rd_out
Register_module.io.reg_write := MEM_WB_module.io.MEMWB_reg_w_out


// MEM_WB PIPELINE
MEM_WB_module.io.in_dataMem_out := DataMem1_module.io.dataOut
MEM_WB_module.io.in_alu_out := EX_MEM_module.io.EXMEM_alu_out


// WRITE_BACK
d := MuxLookup (MEM_WB_module.io.MEMWB_memToReg_out, 0.S, Array (
    (0.U) -> MEM_WB_module.io.MEMWB_alu_out,
    (1.U) -> MEM_WB_module.io.MEMWB_dataMem_out))

Register_module.io.w_data := d


io.out := 0.S
io.reg_out := Register_module.io.w_reg
io.reg_Wdata_out := Register_module.io.w_data
io.data_mem_addr := EX_MEM_module.io.EXMEM_alu_out.asUInt
io.data_mem_dataIn := EX_MEM_module.io.EXMEM_rs2_out
io.imme_out := ID_EX_module.io.imm_out
}








