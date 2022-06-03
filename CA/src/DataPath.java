import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

public class DataPath {
	public static InstructionMemory instMemory;
	public static String r1 = null;
	public static String r2 =null;
	public static String opcode = null;
	public static String imm;
	public static String instruction;
	public static boolean flag = true;
	public static boolean end = false;
	int cycles = 1;
	public static int[] Stages = new int[3];
	void Start(){
		Arrays.fill(Stages,-1);
		Stages[0] = 0;
		int idx = 0;
        while(Registers.PC<(instMemory.add-1)||idx<3){
			System.out.println("Cycle"+cycles);
			if(Stages[0]!=-1)
				System.out.println("Fetch Stage "+Stages[0]);
			else
				System.out.println("Fetch Stage None");
			if(Stages[1]!=-1)
				System.out.println("Decode Stage "+(Stages[1]));
			else
				System.out.println("Decode Stage None");

			if(Stages[2]!=-1)
				System.out.println("Execute Stage "+(Stages[2]));
			else
				System.out.println("Execute Stage None");
            Stages[2]= Stages[1];
            Stages[1]= Stages[0];
            if(opcode!=null){
            	excute();
            	if(end) break;
			}
            if(instruction!=null){
            	decode(instruction);
			}
            if(Registers.PC!=instMemory.add-1) {
				if(flag)
					Fetch(Registers.PC);
				else flag = true;
				Stages[0] = Registers.PC;
			}
            else{
            	Stages[0] = -1;
            	idx++;
			}
			cycles++;
			System.out.println();
			System.out.println();
		}
		System.out.println("end of the program");
		System.out.println("the content of Instrucction memory " +Arrays.toString(instMemory.inst));
		System.out.println("the content of memory " +Arrays.toString(DataMemory.data));
		Registers.print();
	}
	
	void Fetch(int pc){
		instruction=instMemory.getInstruction(pc);
		Registers.PC++;
	}
	void decode(String st){
		opcode= st.substring(0, 4);
		r1= st.substring(4,10);
		r2= st.substring(10,16);
		imm= st.substring(10,16);
	}
	void excute() {
		int op = Integer.parseInt(opcode, 2);
		int r1 = 0;
		if(this.r1!=null)
		    r1 = Instructions.toDic(this.r1);
		int r2 = 0;
		if(this.r2!=null)
		     r2 = Instructions.toDic(this.r2);
		int IMM = 0;
		if(this.imm!=null)
		     IMM = Instructions.toDic(imm);
		switch (op) {
			case 0: {
				System.out.println("Value in R" + r1 + " Added to value in R"+r2+" and Stored in R"+ r1);
				Instructions.ADD(r1, r2);
				break;
			}
			case 1: {
				System.out.println("Value in R" + r1 + " subtracted to value in R"+r2+" and Stored in R"+ r1);
				Instructions.SUB(r1, r2);
				break;
			}
			case 2: {
				System.out.println("Value in R" + r1 + " multiplied to value in R"+r2+" and Stored in R"+ r1);
				Instructions.MUL(r1, r2);
				break;
			}
			case 3: {
				System.out.println("Load "+IMM+" in R"+ r1);
				Instructions.LDI(r1, IMM);
				break;
			}
			case 4: {
				System.out.println("Branch Instruction "+IMM+" is added to the pc");
				Instructions.BEQZ(r1, IMM);
				break;
			}
			case 5: {
				System.out.println("Value in R" + r1 + " Anded to value in R"+r2+" and Stored in R"+ r1);
				Instructions.AND(r1, r2);
				break;
			}
			case 6: {
				System.out.println("Value in R" + r1 + " Ored to value in "+r2+" and Stored in R"+ r1);
				Instructions.OR(r1, r2);
				break;
			}
			case 7: {
				System.out.println("Jump Instruction Value in R" + r1 + " || to value in R"+r2+" and Stored in PC");
				Instructions.JR(r1, r2);
				break;
			}
			case 8: {
				System.out.println("Value in "+r1+" is shift to left circular with value "+ IMM);
				Instructions.SLC(r1, IMM);
				break;
			}
			case 9: {
				System.out.println("Value in "+r1+" is shift to Right circular with value "+ IMM);
				Instructions.SRC(r1, IMM);
				break;
			}
			case 10: {
				System.out.println("Load byte from the memory in location " +IMM+" to "+r1);
				Instructions.LB(r1, IMM);
				break;
			}
			case 11: {
				System.out.println("Store byte in the memory in location " +IMM+" from "+r1);
				Instructions.SB(r1, IMM);
				break;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		instMemory = new InstructionMemory();
		FileReader fr = new FileReader("Program.txt");
		HashMap<String,String> map = new HashMap<>();
		map.put("add","0000");
		map.put("sub","0001");
		map.put("mul","0010");
		map.put("ldi","0011");
		map.put("beqz","0100");
		map.put("and","0101");
		map.put("or","0110");
		map.put("jr","0111");
		map.put("slc","1000");
		map.put("src","1001");
		map.put("lb","1010");
		map.put("sb","1011");
		map.put("end","1100");
		BufferedReader br = new BufferedReader(fr);
		while(br.ready()){
			StringTokenizer st = new StringTokenizer(br.readLine());
			System.out.println();
			String inst = st.nextToken();
			System.out.print(inst);
            String opcode = map.get(inst.toLowerCase());
            String R1 = st.nextToken();
			System.out.print(" "+R1);
            String R2 = st.nextToken();
			System.out.println(" "+R2);
			R1 = Instructions.toBin(Integer.parseInt(R1.substring(1)));
            try {
				R2 = Instructions.toBin(Integer.parseInt(R2));
			}
            catch (Exception e){
				R2 = Instructions.toBin(Integer.parseInt(R2.substring(1)));
			}
			R1 = R1.substring(R1.length()-6);
            R2 = R2.substring(R2.length()-6);
			instMemory.addInstruction(opcode+R1+R2);
			System.out.println(opcode+"  "+ R1+"  "+R2);
		}
		DataPath dataPath = new DataPath();
		Registers r = new Registers();
		dataPath.Start();
		System.out.println(Arrays.toString(instMemory.inst));
	}
}