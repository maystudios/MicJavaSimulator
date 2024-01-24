public class MicroInstruction {

    public static enum JAM {
        NONE, JMPC, JAMN, JAMZ;
    }
    
    private int nextAddress;
    private JAM jam;
    private ALU.ALUInstruction aluInstruction;
    private Register.RegisterType[] registerC;
    private RAM.MemOperation memOperation;
    private Register.RegisterType registerB;

    public MicroInstruction(int nextAddress, JAM jam, ALU.ALUInstruction aluInstruction, Register.RegisterTypeC[] registerC, RAM.MemOperation memOperation, Register.RegisterTypeB registerB) {
        this.nextAddress = nextAddress;
        this.jam = jam;
        this.aluInstruction = aluInstruction;
        this.registerC = registerC;
        this.memOperation = memOperation;
        this.registerB = registerB;
    }
}
