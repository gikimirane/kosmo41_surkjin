import java.util.Scanner;

public class Quiz0501 {

	public static void main(String[] args) {
		
		int num, sum = 0;
		Scanner s = new Scanner(System.in);
		
		do {
			System.out.println("숫자를 입력하시오");
		
			num = s.nextInt();
			sum = sum + num;
		
		} while (num != 0);
		System.out.println("입력한 숫자의 합: " + sum);
	}
}
