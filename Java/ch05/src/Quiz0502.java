import java.util.Scanner;

public class Quiz0502 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("평균값을 구할 정수의 갯수를 입력하시오");
		int count = s.nextInt();
		
		int sum = 0;
		
		for (int i = 0; i < count; i++) {
			System.out.println((i+1) + " 번째 숫자를 입력하시오");
			int num = s.nextInt();
			sum += num;
		}
		System.out.println("입력한 숫자의 평균값: " + (double)sum/count);
	}

}
