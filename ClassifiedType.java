public class ClassifiedType {
    /**
      * ใช้ฟังก์ชันนี้ในการ assign ตัวเลขของ registers จาก machine code R-Type ลง argument
      * @param MachineCode ที่เป็น array ของชุดเลข Binary Machine Code
      * @param argument ที่เป็น array ในการบันทึกเลขของ registers ที่ดึงมาจาก machine code
      */
    public static void R_Type (int[] MachineCode, int[] argument)
    {
        argument[0] = Integer.parseInt(getRs(MachineCode) ,2); //รับค่า rs จาก getRs() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[0]
        argument[1] = Integer.parseInt(getRt(MachineCode), 2); //รับค่า rt จาก getRt() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[1]
        argument[2] = Integer.parseInt(getRd(MachineCode), 2); //รับค่า rd จาก getRd() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[2]
    }
    
    /**
      * ใช้ฟังก์ชันนี้ในการ assign ตัวเลขของ registers จาก machine code I-Type ลง argument
      * @param MachineCode ที่เป็น array ของชุดเลข Binary Machine Code
      * @param argument ที่เป็น array ในการบันทึกเลขของ registers ที่ดึงมาจาก machine code
      */
    public static void I_Type (int[] MachineCode, int[] argument)
    {
        argument[0] = Integer.parseInt(getRs(MachineCode) ,2); //รับค่า rs จาก getRs() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[0]
        argument[1] = Integer.parseInt(getRt(MachineCode), 2); //รับค่า rt จาก getRt() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[1]
        argument[2] = Integer.parseInt(getOffset(MachineCode), 2); //รับค่า offsetField จาก getOffset() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[2]
    }
    
    /**
      * ใช้ฟังก์ชันนี้ในการ assign ตัวเลขของ registers จาก machine code J-Type ลง argument
      * @param MachineCode ที่เป็น array ของชุดเลข Binary Machine Code
      * @param argument ที่เป็น array ในการบันทึกเลขของ registers ที่ดึงมาจาก machine code
      */
    public static void J_Type (int[] MachineCode, int[] argument)
    {
        argument[0] = Integer.parseInt(getRs(MachineCode), 2); //รับค่า rs จาก getRs() โดยที่นำเข้าด้วย MachineCode บันทึกลง argument[0]
        argument[1] = Integer.parseInt(get_J_Rd(MachineCode), 2); //รับค่า rd จาก get_J_Rd() ที่เป็นของ J-typr โดยเฉพาะ ซึ่งได้นำเข้าด้วย MachineCode บันทึกลง argument[1]

    }
    
    /**
      * ใช้ฟังก์ชันนี้ในการรับค่า opcode ที่อยู่ใน Machine Code
      * @param BinaryCode ที่เป็น array ของชุดเลข Binary Machine Code
      * @return String ของเลข opcode
      */
    public static String getOp(int[] BinaryCode)
    {
        StringBuffer opNum = new StringBuffer(); //สร้าง StringBuffer เพื่อเก็บค่าในบิตที่ต้องการ
        for(int i = 24 ; i >= 22 ; i--)
        {
            opNum.append(BinaryCode[i]);
        }
        
        return opNum.toString(); // return เป็น String โดยใช้ toString(); เพื่อแปลง StringBuffer ให้เป็น String
    }
    
    /**
      * ใช้ฟังก์ชันนี้ในการรับค่า Rs(regA) ที่อยู่ใน Machine Code
      * @param BinaryCode ที่เป็น array ของชุดเลข Binary Machine Code
      * @return String ซึ่งเป็นเลขของ regA
      */
    public static String getRs(int[] BinaryCode)
    {
        StringBuffer RS = new StringBuffer(); //สร้าง StringBuffer เพื่อเก็บค่าในบิตที่ต้องการ
        for(int i = 21 ; i >= 19 ; i--)
        {
            RS.append(BinaryCode[i]);
        }
        
        return RS.toString(); // return เป็น String โดยใช้ toString(); เพื่อแปลง StringBuffer ให้เป็น String
    }
    
    /**
      * ใช้ฟังก์ชันนี้ในการรับค่า Rt(regB) ที่อยู่ใน Machine Code
      * @param BinaryCode ที่เป็น array ของชุดเลข Binary Machine Code
      * @return String ซึ่งเป็นเลขของ regB
      */
    public static String getRt(int[] BinaryCode)
    {
        StringBuffer Rt = new StringBuffer(); //สร้าง StringBuffer เพื่อเก็บค่าในบิตที่ต้องการ
        for(int i = 18 ; i >= 16 ; i--)
        {
            Rt.append(BinaryCode[i]);
        }
        
        return Rt.toString(); // return เป็น String โดยใช้ toString(); เพื่อแปลง StringBuffer ให้เป็น String
    }
    
    /**
      * ใช้ฟังก์ชันนี้ในการรับค่า Rd(destReg) ที่อยู่ใน Machine Code
      * @param BinaryCode ที่เป็น array ของชุดเลข Binary Machine Code
      * @return String ซึ่งเป็นเลขของ destReg
      */
    public static String getRd(int[] BinaryCode)
    {
        StringBuffer Rd = new StringBuffer(); //สร้าง StringBuffer เพื่อเก็บค่าในบิตที่ต้องการ
        for(int i = 2 ; i >= 0 ; i--)
        {
            Rd.append(BinaryCode[i]);
        }
        
        return Rd.toString(); // return เป็น String โดยใช้ toString(); เพื่อแปลง StringBuffer ให้เป็น String
    }
    
    /**
      * ใช้ฟังก์ชันนี้ในการรับค่า Rd(destReg) ที่อยู่ใน Machine Code J-Type
      * @param BinaryCode ที่เป็น array ของชุดเลข Binary Machine Code
      * @return String ซึ่งเป็นเลขของ destReg ของ machine code J-type
      */
    public static String get_J_Rd(int[] BinaryCode)
    {
        StringBuffer J_Rd = new StringBuffer(); //สร้าง StringBuffer เพื่อเก็บค่าในบิตที่ต้องการ
        for(int i = 18 ; i >= 16; i--)
        {
            J_Rd.append(BinaryCode[i]);
        }
        
        return J_Rd.toString(); // return เป็น String โดยใช้ toString(); เพื่อแปลง StringBuffer ให้เป็น String
    }
    
    /**
      * ใช้ฟังก์ชันนี้ในการรับค่า OffsetField ที่อยู่ใน Machine Code
      * @param BinaryCode ที่เป็น array ของชุดเลข Binary Machine Code
      * @return String ซึ่งเป็นเลขของ Offset 16 bit
      */
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