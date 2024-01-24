public class ALU {

    public class ALUInstruction {

        private boolean F0;
        private boolean F1;
        private boolean ENA;
        private boolean ENB;
        private boolean INVA;
        private boolean INC;

        private boolean ShiftLeft;
        private boolean ShiftRight;

        public ALUInstruction(boolean F0, boolean F1, boolean ENA, boolean ENB, boolean INVA, boolean INC, boolean ShiftLeft, boolean ShiftRight) {
            this.F0 = F0;
            this.F1 = F1;
            this.ENA = ENA;
            this.ENB = ENB;
            this.INVA = INVA;
            this.INC = INC;
            this.ShiftLeft = ShiftLeft;
            this.ShiftRight = ShiftRight;
        }

        
    }
    private int A;
    private int B;

    // Diese Methode führt die Operationen basierend auf den Steuersignalen aus
    public int execute(boolean F0, boolean F1, boolean ENA, boolean ENB, boolean INVA, boolean INC) {
        int result = 0;

        // Ermittle den Wert von A
        int valueA = ENA ? (INVA ? ~A : A) : 0; // Berücksichtige ENA und INVA
        // Ermittle den Wert von B
        int valueB = ENB ? B : 0; // Berücksichtige ENB

        // F0 = 0, F1 = 0: And
        // F0 = 0, F1 = 1: Or
        // F0 = 1, F1 = 0: Not B
        // F0 = 1, F1 = 1: Add

        // Führe die Operation aus
        if (!F0 && !F1) {
            result = valueA & valueB;
        } else if (!F0 && F1) {
            result = valueA | valueB;
        } else if (F0 && !F1) {
            result = ~valueB;
        } else if (F0 && F1) {
            result = (INC ? valueA + valueB + 1 : valueA + valueB);
        }


        // Weitere Funktionen implementieren wie AND, OR etc.
        // ...

        return result;
    }

    // Setter für A und B
    public void setA(int A) {
        this.A = A;
    }

    public void setB(int B) {
        this.B = B;
    }

    // Ein einfaches Testbeispiel
    public static void main(String[] args) {
        ALU alu = new ALU();
        alu.setA(5);
        alu.setB(10);
        
        // Beispiel: Führe eine Addition aus (F0=1, F1=1, ENA=1, ENB=1, INVA=0, INC=0)
        int result = alu.execute(true, true, true, true, true, true);
        System.out.println("Ergebnis der Addition A+B: " + result);
    }
}
