import java.util.Scanner;

public class BinaryConvert {
    

    public static int[] ConvertToBinary(int x){
        // Create an array to store 32-bit binary digits
        int[] binaryArray = new int[32];  // Fixed size for 32-bit integers
        
        // Handle signed 2's complement
        if (x >= 0) {
            // For positive numbers: direct binary conversion
            for (int i = 0; i <= 31; i++) {
                binaryArray[i] = x % 2;
                x = x / 2;
            }
        } else {
            // For negative numbers: handle 2's complement
            // Negative numbers are stored as 2's complement in Java
            // So we can work with the binary directly from the internal representation
            int mask = 1;  // Used to extract each bit
            for (int i = 0; i <= 31; i++) {
                binaryArray[i] = (x & mask) == 0 ? 0 : 1;
                mask <<= 1;  // Move mask to the left to check the next bit
            }
        }
        
        return binaryArray;

    }

    public static int convertTwoComplementToDecimal(String binary) {
        int length = binary.length();
        
        if (binary.charAt(0) == '1') {
            StringBuilder invertedBinary = new StringBuilder();
            for (int i = 0; i < length; i++) {
                invertedBinary.append(binary.charAt(i) == '0' ? '1' : '0');
            }
            int invertedDecimal = Integer.parseInt(invertedBinary.toString(), 2);
            return -(invertedDecimal + 1); 
        } else {
            return Integer.parseInt(binary, 2);
        }
    }
    
    // public static void main(String[] args) {
    //     // Scanner scanner = new Scanner(System.in);
    //     // System.out.print("Enter a decimal number: ");
    //     // int decimal = scanner.nextInt();
        

    //     // Print the 32-bit binary representation
    //     // System.out.println("32-bit signed 2's complement binary representation: ");
    //     // System.out.println("opcode : " + binaryArray[24] + " " + binaryArray[23] + " " + binaryArray[22]);
    //     // System.out.println("rs : " + binaryArray[21] +" " + binaryArray[20] + " " +binaryArray[19]);
    //     // System.out.println("rt : " + binaryArray[18] + " " +binaryArray[17] +" " + binaryArray[16]);
    //     // System.out.print("offset : ");
    //     // for (int i = 15; i >= 0; i--) {
    //     //     System.out.print(binaryArray[i]);
    //     // }
    //     // System.out.println();
    // }
    
}
