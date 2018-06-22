
public class Quiz0513 {

	public static void main(String[] args) {
		
		System.out.println("가로");
		for (int i = 1; i < 10; i++) {
			for (int j = 2; j < 10; j++) {
				System.out.print(j + "X" + i + "=" + j*i + " ");
			}
			System.out.print('\n');
		}

		System.out.println('\n' + "세로");
		for (int i = 2; i < 10; i++) {
			for (int j = 1; j < 10; j++) {
				System.out.print(i + "X" + j + "=" + i*j + " ");
			}
			System.out.print('\n');
		}
	}

}
