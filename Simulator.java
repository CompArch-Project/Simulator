import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Simulator {
    private static final int NUMMEMORY = 65536; // maximum number of words in memory
    private static final int NUMREGS = 8; // number of machine registers
    private static final int MAXLINELENGTH = 1000;

    static class stateStruct {
        int pc;
        int[] mem = new int[NUMMEMORY];
        int[] reg = new int[NUMREGS];
        int numMemory;
    }

    private static void printState(stateStruct state) {
        System.out.println("\n@@@\nstate:");
        System.out.printf("\tpc %d%n", state.pc);
        System.out.println("\tmemory:");
        for (int i = 0; i < state.numMemory; i++) {
            System.out.printf("\t\tmem[ %d ] %d%n", i, state.mem[i]);
        }
        System.out.println("\tregisters:");
        for (int i = 0; i < NUMREGS; i++) {
            System.out.printf("\t\treg[ %d ] %d%n", i, state.reg[i]);
        }
        System.out.println("end state");
    }

    public static void main(String[] args) {
        stateStruct state = new stateStruct();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your file option: ");
        String option = scanner.nextLine();
        
        String filename = "";
        
        if(option.equals("1"))
        {
            filename = "test.txt"; //รอการพูดคุยนัดแนะกำหนดชื่อไฟล์
        }else{
            System.out.printf("error: usage: java Simulator <machine-code file>%n");
            System.exit(1);
        }

        // if (args.length != 1) {
        //     System.out.printf("error: usage: java Simulator <machine-code file>%n");
        //     System.exit(1);
        // }
        // BufferedReader reader = new BufferedReader(new FileReader(args[0]))
        // BufferedReader reader = new BufferedReader(new FileReader(filename))
        
        System.out.println();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // read in the entire machine-code file into memory
            while ((line = reader.readLine()) != null) {
                try {
                    state.mem[state.numMemory] = Integer.parseInt(line.trim());
                    System.out.printf("memory[%d]=%d%n", state.numMemory, state.mem[state.numMemory]);
                    state.numMemory++;
                } catch (NumberFormatException e) {
                    System.out.printf("error in reading address %d%n", state.numMemory);
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.out.printf("error: can't open file %s%n", args[0]);
            e.printStackTrace();
            System.exit(1);
        }

        state.pc = 0;
        int rs = 0;
        int rt = 0;
        int offset = 0;
        int[] argu = new int[3];
        int total = 0;
        int i = 1;
        while(i != -1){
            
            try {
                handleStateUpdate(state);
            } catch (PCOutOfBoundsException e) {
                e.printStackTrace();
            }

            printState(state);

            int next_pc = state.pc + 1;

            int[] BinaryMachineCode = BinaryConvert.ConvertToBinary(state.mem[state.pc]);
            
            String opcode = ClassifiedType.getOp(BinaryMachineCode);
            

            
            switch (opcode.toString()) {
                case "000":
                    ClassifiedType.R_Type(BinaryMachineCode, argu); 
                    rs = state.reg[argu[0]];
                    rt = state.reg[argu[1]];
                    state.reg[argu[2]] = rs + rt;
                    state.pc++;
                    break;
                
                case "001":
                    ClassifiedType.R_Type(BinaryMachineCode, argu);
                    rs = state.reg[argu[0]];
                    rt = state.reg[argu[1]];
                    state.reg[argu[2]] = ~(rs & rt);
                    state.pc++;
                    break;
                
                case "010":
                    ClassifiedType.I_Type(BinaryMachineCode, argu);
                    rs = state.reg[argu[0]];
                    offset = convertNum(argu[2]);
                    state.reg[argu[1]] = state.mem[rs + offset];
                    state.pc++;
                    break;

                case "011":
                    ClassifiedType.I_Type(BinaryMachineCode, argu);
                    rs = state.reg[argu[0]];
                    offset = convertNum(argu[2]);
                    state.mem[offset + rs] = state.reg[argu[1]];
                    state.pc++;
                    break;

                case "100":
                    ClassifiedType.I_Type(BinaryMachineCode, argu);
                    rs = state.reg[argu[0]];
                    rt = state.reg[argu[1]];
                    offset = convertNum(argu[2]);
                    if(rs == rt)
                    {
                        state.pc = next_pc + offset;
                    }else
                    {
                        state.pc++;
                    }
                    break;

                case "101":
                    ClassifiedType.J_Type(BinaryMachineCode, argu);
                    state.reg[argu[1]] = next_pc;
                    state.pc = state.reg[argu[0]];
                    break;

                case "110":
                    i = -1;
                    state.pc++;
                    break;

                case "111":
                    break;
                
            }
            
            if(i != -1){
                total++;
            }else{
                total++;
                System.out.println("machine halted");
                System.out.println("total of " + total + " instructions executed");
            }
            
            
            if(total > MAXLINELENGTH)
            {
                i = -1;
                System.out.println("machine out of line");
                System.out.println("total of " + total + " instructions executed");
            }

        }
        
        
        System.out.println("final state of machine:");
        printState(state);
        
    }

    public static int convertNum(int num) {
        if ((num & (1 << 15)) != 0) {
            num -= (1 << 16);
        }
        return num;
    }

    public static void handleStateUpdate(stateStruct state) {
        if (state.pc < 0) {
            System.out.println("error: out of address");
            throw new PCOutOfBoundsException("Program counter is less than 0");
        }
    }
    
}