public class ClassifiedType {
    public static void R_Type (int[] MachineCode, int[] argument)
    {
        argument[0] = Integer.parseInt(getRs(MachineCode) ,2);
        argument[1] = Integer.parseInt(getRt(MachineCode), 2);
        argument[2] = Integer.parseInt(getRd(MachineCode), 2);
    }

    public static void I_Type (int[] MachineCode, int[] argument)
    {
        argument[0] = Integer.parseInt(getRs(MachineCode) ,2);
        argument[1] = Integer.parseInt(getRt(MachineCode), 2);
        argument[2] = Integer.parseInt(getOffset(MachineCode), 2);
    }

    public static void J_Type (int[] MachineCode, int[] argument)
    {
        argument[0] = Integer.parseInt(getRs(MachineCode), 2);
        argument[1] = Integer.parseInt(get_J_Rd(MachineCode), 2);

    }

    // public static void O_Type (int[] MachineCode, int[] argument)
    // {
        
    // }

    public static String getOp(int[] BinaryCode)
    {
        StringBuffer opNum = new StringBuffer();
        for(int i = 24 ; i >= 22 ; i--)
        {
            opNum.append(BinaryCode[i]);
        }

        return opNum.toString();
    }

    public static String getRs(int[] BinaryCode)
    {
        StringBuffer RS = new StringBuffer();
        for(int i = 21 ; i >= 19 ; i--)
        {
            RS.append(BinaryCode[i]);
        }

        return RS.toString();
    }

    public static String getRt(int[] BinaryCode)
    {
        StringBuffer Rt = new StringBuffer();
        for(int i = 18 ; i >= 16 ; i--)
        {
            Rt.append(BinaryCode[i]);
        }

        return Rt.toString();
    }

    public static String getRd(int[] BinaryCode)
    {
        StringBuffer Rd = new StringBuffer();
        for(int i = 2 ; i >= 0 ; i--)
        {
            Rd.append(BinaryCode[i]);
        }

        return Rd.toString();
    }

    public static String get_J_Rd(int[] BinaryCode)
    {
        StringBuffer J_Rd = new StringBuffer();
        for(int i = 18 ; i >= 16; i--)
        {
            J_Rd.append(BinaryCode[i]);
        }

        return J_Rd.toString();
    }

    public static String getOffset(int[] BinaryCode)
    {
        StringBuffer Offset = new StringBuffer();
        for(int i = 15 ; i >= 0 ; i--)
        {
            Offset.append(BinaryCode[i]);
        }

        return Offset.toString();
    }
}
