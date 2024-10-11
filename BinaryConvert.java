public class BinaryConvert {
    /**
      * ใช้ฟังก์ชันนี้ในการแปลงเลข decimal number ให้เป็นเลข Binary
      * @param x ที่เป็น Decimal Number
      * @return Array Interger ของ Binary Machine Code 32 bit
      */
    public static int[] ConvertToBinary(int x){ 
        
        int[] binaryArray = new int[32];  

        // ทำการเช็คว่าค่าที่รับมาเป็น positive หรือไม่ เพราะต้องการให้ฟังก์ขันนี้สามารถ handle 2's complement
        if (x >= 0) {
            // สำหรับกรณีที่เป็นเลข positive ก็ทำตามวิธีแปลงเลขฐาน 10 ไป 2 ตามปกติ
            for (int i = 0; i <= 31; i++) {
                binaryArray[i] = x % 2;
                x = x / 2;
            }
        } else {
            // สำหรับกรณีที่เป็นเลข negative
            // เนื่องจาก java จะเก็บเลข negative เป็น 2's complement อยู่แล้ว
            // เราก็จะทำการค่อยๆ เลื่อนที่ละบิตเพื่อบันทึกบิตเหล่านั้นเก็บลง array
            int mask = 1;  
            for (int i = 0; i <= 31; i++) {
                binaryArray[i] = (x & mask) == 0 ? 0 : 1;
                mask <<= 1;  // เลื่อน mask ไปทางซ้ายเพื่อทำการเช็คบิตถัดไป
            }
        }

        return binaryArray;

    }
    /**
      * ใช้ฟังก์ชันนี้ในการแปลง 2's complement binary เป็น decimal
      * @param binary ที่เป็น String 2's complement Binary number
      * @return interger ของ decimal number
      */
    public static int convertTwoComplementToDecimal(String binary) { 
        int length = binary.length();

        //ทำการเช็คว่าเลข binary ที่ได้รับมาว่าเป็น negative หรือไม่ เนื่องจากเลขของเราเป็นแบบ 2's complement ก็จะทำการเช็คเลขบิตแรกสุดว่าเป็น 1 หรือไม่
        if (binary.charAt(0) == '1') {
            //หากบิตแรกเป็น 1 ก็ทำการ flip ตัวเลขทั้งหมดของเลข binary 
            StringBuilder invertedBinary = new StringBuilder(); 
            for (int i = 0; i < length; i++) {
                invertedBinary.append(binary.charAt(i) == '0' ? '1' : '0');
            }
            //จากนั้นก็แปลง String เป็น integer ผ่าน parseInt ที่กำหนด radix เป็น 2 เพื่อที่จะแปลงเลขฐานสองให้เป็นฐานสิบ 
            int invertedDecimal = Integer.parseInt(invertedBinary.toString(), 2);
            // จากนั้นทำการ return ด้วยค่าที่นำเอาไปบวก 1 แล้วติดเครื่องลบเพื่อทำให้คงค่าที่เป็นของมันจริงๆ นั่นคือ negative
            return -(invertedDecimal + 1);
        } else {
            // หากบิตแรกเป็น 0 แสดงว่าเป็นเลย positive ก็ไม่ทำอันใดนอกจาก parseInt ตามปกติ
            return Integer.parseInt(binary, 2); //
        }
    }

}