
public class ForInFor {

	public static void main(String[] args) {

		/*
		for (int i = 0; i < 3; i++) {
			System.out.println("---------------");
			for (int j = 0; j < 3; j++) {
				System.out.print("[" + i + "," + j + "]");
			}
			System.out.print('\n');
		}
		*/
		// 구구단 출력
		for (int i = 1; i < 10; i++) {
			for (int j = 2; j < 10; j++) {
				System.out.print(j + "X" + i + "=" + j*i + " ");
			}
			System.out.print('\n');
		}
		
		System.out.print('\n');
		int i = 1;
		
		while (i < 10) {
			int j = 2;
			while (j < 10) {
				System.out.print(j + "X" + i + "=" + j*i + " ");
				j++;
			}
			System.out.print('\n');
			i++;
		}
		
	}

}
