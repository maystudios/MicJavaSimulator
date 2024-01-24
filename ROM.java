public class ROM {

    private MicroInstruction[] microProgram;

    public ROM() {
        microProgram = new MicroInstruction[512];
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
