public class Register {

    public static enum RegisterType {
        MAR, MDR, PC, MBR, SP, LV, CPP, TOS, OPC, H;
    }

    private int value;
    private RegisterType registerType;

    public Register(RegisterType registerType) {
        this.value = 0;
        this.registerType = registerType;
    }

    public void write(int value) {
        this.value = value;
    }

    public int read() {
        return value;
    }
    
    public void putOn(Bus bus) {
        bus.write(value);
    }
}
