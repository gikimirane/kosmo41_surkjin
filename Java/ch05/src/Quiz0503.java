
public class Quiz0503 {

	public static void main(String[] args) {
		
		System.out.println("7�� ��� �Ǵ� 9�� ���");
		System.out.println("----------------------");
		for (int i=1; i<100; i++) {
			
			if( i%7 == 0 || i%7 == 9) {
				System.out.println(i);
			}
		}
		
		System.out.print('\n');
		System.out.println("7�� ����̸鼭 9�� ���");
		System.out.println("-----------------------");
		for (int i=1; i<100; i++) {
				
			if( i%7 == 0 && i%9 == 0) {
					System.out.println(i);
			}	
		}

	}
}

