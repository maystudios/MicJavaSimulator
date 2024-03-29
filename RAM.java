public class RAM {

    public static enum MemOperation {
        READ, WRITE, FETCH, NONE;
    }

    private byte[] memory = new byte[(int) Math.pow(2, 12)]; // 2^32 = 4.294.967.296
    private Register MAR_Ref;
    private Register MDR_Ref;
    private Register PC_Ref;
    private Register MBR_Ref;

    public RAM(Register MAR_Ref, Register MDR_Ref, Register PC_Ref, Register MBR_Ref) {
        // Initialisiere den Speicher mit 0
        for (int i = 0; i < memory.length; i++) {
            memory[i] = 0;
        }

        this.MAR_Ref = MAR_Ref;
        this.MDR_Ref = MDR_Ref;
        this.PC_Ref = PC_Ref;
        this.MBR_Ref = MBR_Ref;

        // BaseProgramm
        memory[0] = 0x00; // goto Main1
        memory[1] = 0x01; // NOP
        memory[2] = 0x00; // goto Main1
        memory[3] = 0x00; // goto Main1
        memory[4] = 0x00; // goto Main1
        memory[5] = 0x00; // goto Main1
        memory[6] = 0x00; // goto Main1
        memory[7] = 0x00; // goto Main1
        memory[8] = 0x00; // goto Main1

    }

    public void setMethodArea(byte[] methodArea) {
        // Kopiere den Methodenbereich in den Speicher
        for (int i = 0; i < methodArea.length; i++) {
            memory[i] = methodArea[i];
        }
    }

    private void write(int address, byte value) {
        memory[address] = value;
    }

    private void write32(int address, int value) {
        memory[address] = (byte) (value >> 24);
        memory[address + 1] = (byte) (value >> 16);
        memory[address + 2] = (byte) (value >> 8);
        memory[address + 3] = (byte) (value);
    }

    private int read(int address) {
        return memory[address];
    }

    private int read32(int address) {
        return (memory[address] << 24) | (memory[address + 1] << 16) | (memory[address + 2] << 8)
                | (memory[address + 3]);
    }

    public void fetch() {
        int address = PC_Ref.read();
        MBR_Ref.write(read(address));
    }

    public void read() {
        int address = MAR_Ref.read();
        MDR_Ref.write(read32(address));
    }

    public void write() {
        write32(MAR_Ref.read(), MDR_Ref.read());
    }
}
