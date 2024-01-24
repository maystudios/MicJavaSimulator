public class Bus {
    
    private int value;

    public Bus() {
        this.value = 0;
    }

    public void write(int value) {
        this.value = value;
    }

    public int read() {
        return value;
    }
}
