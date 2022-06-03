public class InstructionMemory {
    public static String[] inst;
    public static int add = 0;
    InstructionMemory(){
        inst = new String[1024];
        String zero = "";
        for(int i=0;i<16;i++) zero=zero+"0";
        for(int i=0;i<inst.length;i++){
            inst[i] = zero;
        }
    }
    String getInstruction(int pc){
        return inst[pc];
    }
    void setInstruction(int pc,String value){
        if(value.length()!=16){
            throw new IllegalArgumentException("Value is not 16 bit");
        }
        inst[pc] = value;
    }
    void addInstruction(String inst){
        this.inst[add++] = inst;
    }
    public static void main(String[] args) {

    }
}
