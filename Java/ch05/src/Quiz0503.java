
public class Quiz0503 {

	public static void main(String[] args) {
		
		System.out.println("7의 배수 또는 9의 배수");
		System.out.println("----------------------");
		for (int i=1; i<100; i++) {
			
			if( i%7 == 0 || i%7 == 9) {
				System.out.println(i);
			}
		}
		
		System.out.print('\n');
		System.out.println("7의 배수이면서 9의 배수");
		System.out.println("-----------------------");
		for (int i=1; i<100; i++) {
				
			if( i%7 == 0 && i%9 == 0) {
					System.out.println(i);
			}	
		}

	}
}

