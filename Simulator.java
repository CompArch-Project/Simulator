import java.util.Scanner;

public class Simulator {
    public static int[] memory = new int[65536];
    public static int[] reg = new int[8];


    public Simulator(){
        reg[0] = 0;
        memory[0] = 8454151;
        memory[1] = 9043971;
        memory[2]=655361;
        memory[3]=16842754;
        memory[4]=16842749;
        memory[5]=29360128;
        memory[6]=25165824;
        memory[7]=5;
        memory[8]=-1;
        memory[9]=2;
    }
    
    
    public static void main(String[] args) {
        
        Simulator t2 = new Simulator();
        int[] arrayB = BinaryConvert.ConvertBinary(t2.memory[0]);
        
        System.out.println("opcode : " + arrayB[24] + " " + arrayB[23] + " " + arrayB[22]);
        System.out.println("rs : " + arrayB[21] + " " + arrayB[20] +  " " +arrayB[19]);
        System.out.println("rt : " + arrayB[18] + " " +arrayB[17] + " " + arrayB[16]);
        System.out.print("offset : ");
        for (int i = 15; i >= 0; i--) {
            System.out.print(arrayB[i]);
        }
    }
}

