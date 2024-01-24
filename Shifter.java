public class Shifter {

    public int input;
    public int output;

    public Shifter() {
        this.input = 0;
        this.output = 0;
    }

    public void setInput(int input) {
        this.input = input;
        this.output = input;
    }

    public void putOn(Bus bus) {
        bus.write(output);
    }

    public void shiftLeft() {
        output = input << 8;
    }

    public void shiftRight() {
        output = input >> 1;
    }

}
