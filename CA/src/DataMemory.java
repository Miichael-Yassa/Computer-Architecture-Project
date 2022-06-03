public class DataMemory {
    static String[] data;/////////////////
    DataMemory(){
        data = new String[2048];
        String zero = "00000000";
        for (int i = 0; i < data.length; i++) {
            data[i] = zero;
        }
    }
    public static String getData(int pos){
        return data[pos];////////////////
    }
    public static void setData(int pos,String value){
        if(value.length()!=8) throw new IllegalArgumentException(" Value is not 8 ");
        data[pos] = value;
    }
    public static void main(String[] args) {

    }
}
