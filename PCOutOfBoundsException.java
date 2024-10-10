//PCOutOfBoundsException เป็น class ที่ถูกสร้างมาเพื่อทำการรับมือ exception ในกรณีที่ pc < 0 
public class PCOutOfBoundsException extends RuntimeException {
    
    public PCOutOfBoundsException(String message) { //เป็นฟังก์ชันที่ะทำการส่ง message ที่นำเข้ามาส่งขึ้น terminal
        super(message);
    }
}
