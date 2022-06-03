import java.util.Arrays;

public class Registers {
    static int Size = 8;
    static String[] GPRS = new String[64];
    static short PC = 0;
    public static SREG SR = new SREG();
    static class SREG{
        char[] value =("00000000").toCharArray();
        public void setC(char c) {
            value[4] = c;
        }

        public void setV(char v) {
            value[3] = v;
        }

        public void setN(char n) {
            value[2] = n;
        }

        public void setS(char s) {
            value[1] = s;
        }

        public void setZ(char z) {
            value[0] = z;
        }
        public char isC() {
            return value[4];
        }

        public char isV() {
            return value[3];
        }

        public char isN() {
            return value[2];
        }

        public char isS() {
            return value[1];
        }

        public char isZ() {
            return value[0];
        }
        public void print(){
            System.out.println("C "+isC()+" V "+isV()+" N "+isN()+" S "+isS()+" Z "+isZ());
        }
    }
    public static void print(){
        System.out.println("PC "+PC);
        System.out.println("SREG "+SR.value);
        System.out.println("Registers "+Arrays.toString(GPRS));
    }
    public Registers(){
        for (int i = 0; i < 64; i++) {
            this.GPRS[i] = "00000000";
        }
    }
}
