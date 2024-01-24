import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;

public class Mic1 {
    
    public HashMap<Register.RegisterType,Register> registers;
    private ALU alu;
    public RAM ram;
    public ROM rom;
    private Bus ABus;
    private Bus BBus;
    private Bus CBus;

    private Register.RegisterType[] registerTypes = Register.RegisterType.values();

    public Mic1() {
        ABus = new Bus();
        BBus = new Bus();
        CBus = new Bus();

        registers = new HashMap<Register.RegisterType,Register>();
        for (int i = 0; i < registerTypes.length; i++) {
            registers.put(registerTypes[i], new Register(registerTypes[i], BBus, ABus));
        }
        registers.replace(Register.RegisterType.H, new Register(Register.RegisterType.H, ABus, CBus);
        
        alu = new ALU();
        ram = new RAM(registers.get(Register.RegisterType.MAR), registers.get(Register.RegisterType.MDR), registers.get(Register.RegisterType.PC), registers.get(Register.RegisterType.MBR));
        rom = new ROM();
    }

    public void cycle() {
        System.out.println("Mic1: cycle()");
        // Fetch
        ram.fetch();

        // Decode
        MicroInstruction microInstruction = rom.decode(registers.get(Register.RegisterType.IR).getValue());

    }

    public static void main(String[] args) {

    }
}
