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

        //initialize ตัวแปรต่างๆ ในตอนเริ่มของโปรแกรมให้เป็นค่าตั้งต้น
        state.pc = 0; // pc ในตอนเริ่มเป็น 0
        int rs = 0; // initialize ตัวแปร rs
        int rt = 0; // initialize ตัวแปร rt
        int offset = 0; //initialize ตัวแปร  offset
        int[] argu = new int[3]; //initialize  array argu ที่เอาไว้เก็บค่าต่างๆ ที่ดึงมาได้จาก MachineCode ในตำแหน่งบิตที่ได้กำหนดเอาไว้
        int total = 0; //ตัวแปรที่เอาไว้เช็คจำนวน instruction ที่ใช้ไปในโปรแกรม
        int i = 1; //ตัวแปรที่เอาไว้เป็น condition ในการ loop โปรแกรมจนกว่าจะ halt
        while(i != -1){
            
            //รับมือการเปลี่ยน state ที่จะทำการเช็คว่า pc ของ state ไม่น้อยกว่า 0 ใช่หรือไม่
            try {
                handleStateUpdate(state);
            } catch (PCOutOfBoundsException e) {
                e.printStackTrace();
            }

            printState(state); //print state ก่อนที่จะคำนวณ instruction
            printStateToFile(state); //เขียนการ print state ในไฟล์ใหม่ .txt
            
            //initialize ตัวแปรที่เก็บค่า PC+1 ชื่อ next_pc
            int next_pc = state.pc + 1; 
            //แปลงค่า Decimal MachineCode ให้เป็น Binary MachineCode แล้วเก็บใน array integer 
            int[] BinaryMachineCode = BinaryConvert.ConvertToBinary(state.mem[state.pc]); 
            //ดึง opcode จาก MachineCode ผ่านฟังก์ชันที่อยู่ใน class ClassifiedType
            String opcode = ClassifiedType.getOp(BinaryMachineCode); 

            // เช็คเคสของ opcode ที่ดึงมาได้
            switch (opcode.toString()) {
                case "000": //หากเป็น 000 -> add
                    ClassifiedType.R_Type(BinaryMachineCode, argu); //ทำการดึงค่าต่างๆ ของ MachineCode ที่เป็นแบบ R-type ผ่านการเรียกฟังกชันใน ClassifiedType
                    rs = state.reg[argu[0]]; //เอาค่าที่เก็บใน reg A มาเก็บในตัวแปร rs
                    rt = state.reg[argu[1]]; // เอาค่าที่เก็บใน reg B มาเก็บในตัวแปร rt
                    state.reg[argu[2]] = rs + rt; //ทำการ add ค่าที่เก็บใน regA เข้ากับ regB เก็บใน destReg
                    state.pc++; //อัปเดต pc
                    break;

                case "001": //หากเป็น 001 -> nand
                    ClassifiedType.R_Type(BinaryMachineCode, argu); //ทำการดึงค่าต่างๆ ของ MachineCode ที่เป็นแบบ R-type ผ่านการเรียกฟังกชันใน ClassifiedType
                    rs = state.reg[argu[0]]; //เอาค่าที่เก็บใน reg A มาเก็บในตัวแปร rs
                    rt = state.reg[argu[1]]; // เอาค่าที่เก็บใน reg B มาเก็บในตัวแปร rt
                    state.reg[argu[2]] = ~(rs & rt); //ทำการ nand ระหว่าง regA และ regB เก็บใน destReg
                    state.pc++; //อัปเดต pc
                    break;

                case "010"://หากเป็น 010 -> lw
                    ClassifiedType.I_Type(BinaryMachineCode, argu); //ทำการดึงค่าต่างๆ ของ MachineCode ที่เป็นแบบ I-type ผ่านการเรียกฟังกชันใน ClassifiedType
                    rs = state.reg[argu[0]]; //เอาค่าที่เก็บใน reg A มาเก็บในตัวแปร rs
                    offset = convertNum(argu[2]); //นำเลข offset 16 bit มา extend เป็น 32 bit ด้วยฟังกชัน convertNum(int) เก็บในตัวแปร offset
                    state.reg[argu[1]] = state.mem[rs + offset]; //นำค่าใน memory ตำแหน่งที่มาจากการบวกกันของ rs + offset โหลดเข้าไปเก็บใน regฺB 
                    state.pc++; //อัปเดต pc
                    break;

                case "011"://หากเป็น 011 -> sw
                    ClassifiedType.I_Type(BinaryMachineCode, argu); //ทำการดึงค่าต่างๆ ของ MachineCode ที่เป็นแบบ I-type ผ่านการเรียกฟังกชันใน ClassifiedType
                    rs = state.reg[argu[0]]; //เอาค่าที่เก็บใน reg A มาเก็บในตัวแปร rs
                    offset = convertNum(argu[2]); //นำเลข offset 16 bit มา extend เป็น 32 bit ด้วยฟังกชัน convertNum(int) เก็บในตัวแปร offset
                    state.mem[offset + rs] = state.reg[argu[1]]; // นำค่าของ regB ไป store ใน memory ตำแหน่งที่มาจากการบวกกันของ rs + offset
                    state.pc++; //อัปเดต pc
                    break;

                case "100"://หากเป็น 100 -> beq
                    ClassifiedType.I_Type(BinaryMachineCode, argu); //ทำการดึงค่าต่างๆ ของ MachineCode ที่เป็นแบบ I-type ผ่านการเรียกฟังกชันใน ClassifiedType
                    rs = state.reg[argu[0]]; //เอาค่าที่เก็บใน reg A มาเก็บในตัวแปร rs
                    rt = state.reg[argu[1]]; // เอาค่าที่เก็บใน reg B มาเก็บในตัวแปร rt
                    offset = convertNum(argu[2]); //นำเลข offset 16 bit มา extend เป็น 32 bit ด้วยฟังกชัน convertNum(int) เก็บในตัวแปร offset
                    //เช็คว่า rs เท่ากันกับ rt หรือไม่
                    if(rs == rt)
                    {
                        state.pc = next_pc + offset; //ถ้า true จะทำการกระโดดไปที่ PC+1+offset 
                    }else
                    {
                        state.pc++; //ถ้า false ให้ไปยังตำแหน่งถัดไปของ pc
                    }
                    break;

                case "101"://หากเป็น 101 -> jalr
                    ClassifiedType.J_Type(BinaryMachineCode, argu); //ทำการดึงค่าต่างๆ ของ MachineCode ที่เป็นแบบ J-type ผ่านการเรียกฟังกชันใน ClassifiedType
                    state.reg[argu[1]] = next_pc; //เก็บค่า PC+1 ไว้ใน regB
                    state.pc = state.reg[argu[0]]; //กระโดดไปที่ address ซึ่งเก็บไว้ใน regA 
                    break;

                case "110"://หากเป็น 110 -> halt
                    i = -1; //ให้ i เป็น -1 เพื่อที่จะทำให้ while loop จบการืำงาน
                    state.pc++; //อัปเดต pc
                    break;

                case "111"://หากเป็น 111 -> noop
                    break;

            }

            //เช็คว่าโปรแกรมได้ halt หรือยัง 
            if(i != -1){
                total++; //หากยังไม่ halt ก็นับจำนวน instruction ตามปกติ
            }else{
                // หาก halt แล้วก็ยังต้องนับจำนวน instruction เหมือนเดิม
                // เพียงแต่จะมีการ print ข้อความปิดการทำงานของโปรแกรม พร้อมสรุปจำนวน instruction ที่ใช้ไป 
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

            //เช็คว่าคำสั่ง instruction ถัดไปเกินกว่าขอบเขตของโปรแกรมหรือยัง
            if(total + 1 > MAXLINELENGTH)
            {
                //หากเกินกว่าขอบเขตแล้วก็จะหยุดการทำงานโปรแกรมเลย
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

        //หลังจากที่โปรแกรมทำงานจนเสร็จสิ้นแล้วก็จะทำการ print state สุดท้ายอีกครั้งหนึ่งหลังจบโปรแกรม
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

    /**
      * ใช้ฟังก์ชันนี้ในการขยายบิตของ offset 
      * @param num ที่เป็น OffsetField 16 bit
      * @return interger ของ OffsetField ที่ขยายเป็น 32 bit
      */
    public static int convertNum(int num) {
        if ((num & (1 << 15)) != 0) {
            num -= (1 << 16);
        }
        return num;
    }

    /**
      * ใช้ฟังก์ชันนี้ในการเช็ค exception ของการที่ pc < 0
      * @param state ณ ปัจจุบันของโปรแกรม
      */
    public static void handleStateUpdate(stateStruct state) {
        //หาก pc < 0 จริง
        if (state.pc < 0) {
            //ทำการ throw exception ที่ได้เขียนขึ้นมา
            System.out.println("error: out of address");
            throw new PCOutOfBoundsException("Program counter is less than 0");
        }
    }

}

