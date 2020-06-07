package kr.co.jongnomilk.test;



public class BitTest {

	public BitTest() {
//		1,2/3/4/5/6/7/8
//		buzzer¼Ò¸®/led1/led2/led3/heatpad/motor1/motor2
		
		byte control = 0b00000000;
		byte buzzer = 0b11;
		byte led1 = 0b1;
		byte led2 = 0b1;
		byte led3 = 0b1;
		byte heatpad = 0b1;
		byte motor1 = 0b1;
		byte motor2 = 0b1;

		control = (byte)(control | buzzer << 6 | led1 << 5 | led2 << 4 | led3 << 3 | heatpad << 2 | motor2 << 1 | motor1);
		
		
		System.out.println(control);
		int a = Byte.toUnsignedInt(control);
		byte b = (byte)Byte.toUnsignedInt(control);
		System.out.println(Integer.toBinaryString(a));
		System.out.println((byte)(a&0xff));
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BitTest();
	}

}
