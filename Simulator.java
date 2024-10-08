import java.io.*;
import java.util.Scanner;

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

    private static void printStateToFile(stateStruct state) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true))) {
            writer.println("\n@@@\nstate:");
            writer.printf("\tpc %d%n", state.pc);
            writer.println("\tmemory:");
            for (int i = 0; i < state.numMemory; i++) {
                writer.printf("\t\tmem[ %d ] %d%n", i, state.mem[i]);
            }
            writer.println("\tregisters:");
            for (int i = 0; i < NUMREGS; i++) {
                writer.printf("\t\treg[ %d ] %d%n", i, state.reg[i]);
            }
            writer.println("end state");
        } catch (IOException e) {
            System.out.println("error: can't write to file output.txt");
            e.printStackTrace();
        }
    }

    private static void writeMemoryToFile(stateStruct state, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            for (int i = 0; i < state.numMemory; i++) {
                writer.printf("memory[%d]=%d%n", i, state.mem[i]);
            }
        } catch (IOException e) {
            System.out.println("error: can't write to file " + filename);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        stateStruct state = new stateStruct();

        Scanner scanner = new Scanner(System.in);
        System.out.println("1.test \n2.Multiplier \n3.Combination \n4.sum");
        System.out.print("Enter your file option: ");
        String option = scanner.nextLine();
        String filename = "";

        if(option.equals("1")) {
            filename = "test.txt";
        }else if(option.equals("2")){
            filename = "Multiplier.txt";
        }else if(option.equals("3")){
            filename = "Combination.txt";
        }else if(option.equals("4")){
            filename = "sum.txt";
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
            System.out.printf("error: can't open file %s%n", filename);
            e.printStackTrace();
            System.exit(1);
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
            // This will clear output before using new one
        } catch (IOException e) {
            System.out.println("error: can't clear file " + "output.txt");
            e.printStackTrace();
        }
        writeMemoryToFile(state,"output.txt");//write to output file

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
            printStateToFile(state);
            int next_pc = state.pc + 1;

            int[] BinaryMachineCode = BinaryConvert.ConvertToBinary(state.mem[state.pc]);

            String opcode = ClassifiedType.getOp(BinaryMachineCode);



            switch (opcode.toString()) {
                case "000": //add
                    ClassifiedType.R_Type(BinaryMachineCode, argu);
                    rs = state.reg[argu[0]];
                    rt = state.reg[argu[1]];
                    state.reg[argu[2]] = rs + rt;
                    state.pc++;
                    break;

                case "001": //nand
                    ClassifiedType.R_Type(BinaryMachineCode, argu);
                    rs = state.reg[argu[0]];
                    rt = state.reg[argu[1]];
                    state.reg[argu[2]] = ~(rs & rt);
                    state.pc++;
                    break;

                case "010"://lw
                    ClassifiedType.I_Type(BinaryMachineCode, argu);
                    rs = state.reg[argu[0]];
                    offset = convertNum(argu[2]);
                    state.reg[argu[1]] = state.mem[rs + offset];
                    state.pc++;
                    break;

                case "011"://sw
                    ClassifiedType.I_Type(BinaryMachineCode, argu);
                    rs = state.reg[argu[0]];
                    offset = convertNum(argu[2]);
                    state.mem[offset + rs] = state.reg[argu[1]];
                    state.pc++;
                    break;

                case "100"://beq
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

                case "101"://jalr
                    ClassifiedType.J_Type(BinaryMachineCode, argu);
                    state.reg[argu[1]] = next_pc;
                    state.pc = state.reg[argu[0]];
                    break;

                case "110"://halt
                    i = -1;
                    state.pc++;
                    break;

                case "111"://noop
                    break;

            }

            if(i != -1){
                total++;
            }else{
                total++;
                try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true))){
                    writer.println("machine halted");
                    writer.println("total of "+ total + " instructions executed");
                }catch (IOException e) {
                    System.out.println("error: can't write to file " + "output.txt");
                    e.printStackTrace();
                }
                System.out.println("machine halted");
                System.out.println("total of " + total + " instructions executed");
            }


            if(total + 1 > MAXLINELENGTH)
            {
                i = -1;
                try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true))){
                    writer.println("machine halted");
                    writer.println("total of "+ total + " instructions executed");
                }catch (IOException e) {
                    System.out.println("error: can't write to file " + "output.txt");
                    e.printStackTrace();
                }
                System.out.println("machine out of line");
                System.out.println("total of " + total + " instructions executed");
            }

        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true))){
            writer.println("final state of machine:");
        }catch (IOException e) {
            System.out.println("error: can't write to file " + "output.txt");
            e.printStackTrace();
        }
        System.out.println("final state of machine:");
        printState(state);
        printStateToFile(state);

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

