public class ROM {

    private MicroInstruction[] microProgram;

    public ROM() {
        microProgram = new MicroInstruction[512];

        // Initialize all micro instructions to goto Main1
        MicroInstruction main1 = new MicroInstruction(0, MicroInstruction.JAM.NONE,
                new ALU.ALUInstruction(true, true, false, true, false, true, false, false),
                new Register.RegisterType[] { Register.RegisterType.PC }, RAM.MemOperation.FETCH,
                Register.RegisterType.PC, "Main1", "PC = PC + 1; fetch goto MBR");

        MicroInstruction nop = new MicroInstruction(0, MicroInstruction.JAM.NONE,
                new ALU.ALUInstruction(false, false, false, false, false, false, false, false),
                new Register.RegisterType[] {}, RAM.MemOperation.NONE, Register.RegisterType.PC, "NOP", "goto Main1");

        // PC = PC + 1; fetch goto MBR
        microProgram[0] = main1;

        // goto Main1
        microProgram[1] = nop;

    }

    public MicroInstruction decode(int address) {
        return read(address);
    }

    public void setMicroProgram(MicroInstruction[] microProgram) {
        this.microProgram = microProgram;
    }

    private MicroInstruction read(int address) {
        return microProgram[address];
    }

}
