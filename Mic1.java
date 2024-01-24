import java.util.ArrayList;
import java.util.LinkedList;

import java.util.HashMap;

public class Mic1 {

    public HashMap<Register.RegisterType, Register> registers;
    private ALU alu;
    private Shifter shifter;
    public RAM ram;
    public ROM rom;
    private Bus ABus;
    private Bus BBus;
    private Bus CBus;

    private Register MicroCodeRegisterAddress;

    private Register.RegisterType[] registerTypes = Register.RegisterType.values();

    public Mic1() {
        ABus = new Bus();
        BBus = new Bus();
        CBus = new Bus();

        registers = new HashMap<Register.RegisterType, Register>();
        for (int i = 0; i < registerTypes.length; i++) {
            registers.put(registerTypes[i], new Register(registerTypes[i]));
        }
        MicroCodeRegisterAddress = new Register(Register.RegisterType.MicroCodeRegisterAddress);

        alu = new ALU(); // Fix: Instantiate ALU using Mic1 as the enclosing instance
        shifter = new Shifter();
        ram = new RAM(registers.get(Register.RegisterType.MAR), registers.get(Register.RegisterType.MDR),
                registers.get(Register.RegisterType.PC), registers.get(Register.RegisterType.MBR));
        rom = new ROM();
    }

    public void cycle(int i) {
        System.out.println("\u001B[32mMic1: start cycle() : " + i + "\u001B[0m"); // Green

        // Fetch
        System.out.println("MicroCodeRegisterAddress: " + MicroCodeRegisterAddress.read());

        // Decode
        MicroInstruction microInstruction = rom.decode(MicroCodeRegisterAddress.read());
        System.out.println(microInstruction);

        // Put on ABus and BBus
        registers.get(Register.RegisterType.H).putOn(ABus);
        registers.get(microInstruction.registerB).putOn(BBus);
        System.out.println("ABus: " + ABus.read() + ", BBus: " + BBus.read());

        // Execute
        alu.setA(ABus.read());
        alu.setB(BBus.read());
        int result = alu.execute(microInstruction.aluInstruction);
        System.out.println("ALU: " + result);
        // Shift
        shifter.setInput(result);
        if (microInstruction.aluInstruction.ShiftLeft) {
            shifter.shiftLeft();
        } else if (microInstruction.aluInstruction.ShiftRight) {
            shifter.shiftRight();
        }

        // Put on CBus
        System.out.println("Shifter: " + shifter.output);
        shifter.putOn(CBus);
        System.out.println("CBus: " + CBus.read());
        // Write
        String writtenRegister = "Written Registers: ";
        for (Register.RegisterType registerType : microInstruction.registerC) {
            registers.get(registerType).writeFrom(CBus);
            writtenRegister += registerType + " = " + registers.get(registerType).read() + ", ";
        }
        System.out.println(writtenRegister);

        // Ram Operation
        String ramOperation = "RAM Operation: ";
        if (microInstruction.memOperation == RAM.MemOperation.READ) {
            ram.read();
            ramOperation += "READ";
        } else if (microInstruction.memOperation == RAM.MemOperation.WRITE) {
            ram.write();
            ramOperation += "WRITE";
        } else if (microInstruction.memOperation == RAM.MemOperation.FETCH) {
            ram.fetch();
            ramOperation += "FETCH";
        }
        System.out.println(ramOperation);

        // Jump
        String jump = "Jump: ";
        if (microInstruction.jam == MicroInstruction.JAM.JAMN) {
            if (alu.getN()) {
                MicroCodeRegisterAddress.write(microInstruction.nextAddress + 256);
                jump += "JAMN : nextAddress = " + MicroCodeRegisterAddress.read() + 256;
            }
        } else if (microInstruction.jam == MicroInstruction.JAM.JAMZ) {
            if (alu.getZ()) {
                MicroCodeRegisterAddress.write(microInstruction.nextAddress + 256);
                jump += "JAMZ : nextAddress = " + MicroCodeRegisterAddress.read() + 256;
            }
        } else if (microInstruction.jam == MicroInstruction.JAM.JMPC) {
            MicroCodeRegisterAddress.write(microInstruction.nextAddress + 256);
            jump += "JMPC : nextAddress = " + MicroCodeRegisterAddress.read() + 256;
        } else {
            MicroCodeRegisterAddress.write(microInstruction.nextAddress);
            jump += "NONE : nextAddress = " + MicroCodeRegisterAddress.read();
        }
        System.out.println(jump);

        System.out.println("PC: " + registers.get(Register.RegisterType.PC).read());

        if (i < 3) {
            System.out.println("MicroCodeRegisterAddress: " + MicroCodeRegisterAddress.read());
            System.out.println("\u001B[31mMic1: end cycle() : " + i + "\u001B[0m");
            System.out.println();
            System.out.println();
            cycle(i + 1);
        }

    }

    public static void main(String[] args) {
        Mic1 mic1 = new Mic1();

        // First Fetch
        mic1.ram.fetch();

        mic1.cycle(1);
    }
}
