public class EvalOpcode {
    public static int verify_Opcode(String input){
        switch (input) {
            case "010":
                return 2;
            case "001":
                return 1;
            default:
                return -1;
        }
    }
}
