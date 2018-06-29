
public class Quiz1306 {

	public static void main(String[] args) {

		int[][] arr = new int[3][9];
		
		for (int i = 2; i < 5; i++) {
			for (int j = 1; j < 10; j++) {
				arr[i-2][j-1] = i*j;
				System.out.print(arr[i-2][j-1] + "\t");
			}
			System.out.print('\n');
		}
	}

}
