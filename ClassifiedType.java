public class ClassifiedType {
    //รับ machine code 32 bit และ array ของ argument ที่จะเก็บเลชตำแหน่ง reg ที่จะนำไปใช้ต่อของ instruntion R-Type 
    public static void R_Type (int[] MachineCode, int[] argument)
    {
        argument[0] = Integer.parseInt(getRs(MachineCode) ,2); //รับค่า rs จาก getRs() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[0]
        argument[1] = Integer.parseInt(getRt(MachineCode), 2); //รับค่า rt จาก getRt() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[1]
        argument[2] = Integer.parseInt(getRd(MachineCode), 2); //รับค่า rd จาก getRd() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[2]
    }

    //รับ machine code 32 bit และ array ของ argument ที่จะเก็บเลชตำแหน่ง reg ที่จะนำไปใช้ต่อของ instruntion I-Type 
    public static void I_Type (int[] MachineCode, int[] argument)
    {
        argument[0] = Integer.parseInt(getRs(MachineCode) ,2); //รับค่า rs จาก getRs() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[0]
        argument[1] = Integer.parseInt(getRt(MachineCode), 2); //รับค่า rt จาก getRt() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[1]
        argument[2] = Integer.parseInt(getOffset(MachineCode), 2); //รับค่า offsetField จาก getOffset() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[2]
    }

    //รับ machine code 32 bit และ array ของ argument ที่จะเก็บเลชตำแหน่ง reg ที่จะนำไปใช้ต่อของ instruntion J-Type 
    public static void J_Type (int[] MachineCode, int[] argument)
    {
        argument[0] = Integer.parseInt(getRs(MachineCode), 2); //รับค่า rs จาก getRs() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[0]
        argument[1] = Integer.parseInt(get_J_Rd(MachineCode), 2); //รับค่า rd จาก get_J_Rd() ที่เป็นของ J-typr โดยเฉพาะ ซึ่งได้นำเข้าด้วย MachineCode บันทึกลง argument[1]

    }

    //รัย array ของ binary machine code แล้ว return เป็น String ของ binary opcode ที่อยู่ในตำแหน่งบิตที่ 24-22
    public static String getOp(int[] BinaryCode)
    {
        StringBuffer opNum = new StringBuffer(); //สร้าง StringBuffer เพื่อเก็บค่าในบิตที่ต้องการ
        for(int i = 24 ; i >= 22 ; i--)
        {
            opNum.append(BinaryCode[i]);
        }
        
        return opNum.toString(); // return เป็น String โดยใช้ toString(); เพื่อแปลง StringBuffer ให้เป็น String
    }
    //รัย array ของ binary machine code แล้ว return เป็น String ของ binary rs(regA) ที่อยู่ในตำแหน่งบิตที่ 21-19
    public static String getRs(int[] BinaryCode)
    {
        StringBuffer RS = new StringBuffer(); //สร้าง StringBuffer เพื่อเก็บค่าในบิตที่ต้องการ
        for(int i = 21 ; i >= 19 ; i--)
        {
            RS.append(BinaryCode[i]);
        }
        
        return RS.toString(); // return เป็น String โดยใช้ toString(); เพื่อแปลง StringBuffer ให้เป็น String
    }
    //รัย array ของ binary machine code แล้ว return เป็น String ของ binary rt(regB) ที่อยู่ในตำแหน่งบิตที่ 18-16
    public static String getRt(int[] BinaryCode)
    {
        StringBuffer Rt = new StringBuffer(); //สร้าง StringBuffer เพื่อเก็บค่าในบิตที่ต้องการ
        for(int i = 18 ; i >= 16 ; i--)
        {
            Rt.append(BinaryCode[i]);
        }
        
        return Rt.toString(); // return เป็น String โดยใช้ toString(); เพื่อแปลง StringBuffer ให้เป็น String
    }
    //รัย array ของ binary machine code แล้ว return เป็น String ของ binary rd(destReg) ที่อยู่ในตำแหน่งบิตที่ 2-0
    public static String getRd(int[] BinaryCode)
    {
        StringBuffer Rd = new StringBuffer(); //สร้าง StringBuffer เพื่อเก็บค่าในบิตที่ต้องการ
        for(int i = 2 ; i >= 0 ; i--)
        {
            Rd.append(BinaryCode[i]);
        }
        
        return Rd.toString(); // return เป็น String โดยใช้ toString(); เพื่อแปลง StringBuffer ให้เป็น String
    }

    //รัย array ของ binary machine code แล้ว return เป็น String ของ binary J-type rd(regB) ที่อยู่ในตำแหน่งบิตที่ 18-16
    public static String get_J_Rd(int[] BinaryCode)
    {
        StringBuffer J_Rd = new StringBuffer(); //สร้าง StringBuffer เพื่อเก็บค่าในบิตที่ต้องการ
        for(int i = 18 ; i >= 16; i--)
        {
            J_Rd.append(BinaryCode[i]);
        }
        
        return J_Rd.toString(); // return เป็น String โดยใช้ toString(); เพื่อแปลง StringBuffer ให้เป็น String
    }

    //รัย array ของ binary machine code แล้ว return เป็น String ของ binary offsetField ที่อยู่ในตำแหน่งบิตที่ 15-0
    public static String getOffset(int[] BinaryCode)
    {
        StringBuffer Offset = new StringBuffer(); //สร้าง StringBuffer เพื่อเก็บค่าในบิตที่ต้องการ
        for(int i = 15 ; i >= 0 ; i--)
        {
            Offset.append(BinaryCode[i]);
        }
        
        return Offset.toString(); // return เป็น String โดยใช้ toString(); เพื่อแปลง StringBuffer ให้เป็น String
    }
}