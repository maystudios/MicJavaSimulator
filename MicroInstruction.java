import java.util.Arrays;

public class MicroInstruction {

    public static enum JAM {
        NONE, JMPC, JAMN, JAMZ;
    }

    public int nextAddress;
    public JAM jam;
    public ALU.ALUInstruction aluInstruction;
    public Register.RegisterType[] registerC;
    public RAM.MemOperation memOperation;
    public Register.RegisterType registerB;

    public String name;
    public String operation;

    public MicroInstruction(int nextAddress, JAM jam, ALU.ALUInstruction aluInstruction,
            Register.RegisterType[] registerC, RAM.MemOperation memOperation, Register.RegisterType registerB,
            String name, String operation) {
        this.nextAddress = nextAddress;
        this.jam = jam;
        this.aluInstruction = aluInstruction;
        this.registerC = registerC;
        this.memOperation = memOperation;
        this.registerB = registerB;

        this.name = name;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "Name: " + name + "  " + operation;
    }
}
