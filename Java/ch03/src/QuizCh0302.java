import java.util.Scanner;

public class QuizCh0302 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		System.out.println("숫자를 입력하시오");
		
		int num = s.nextInt();
		
		System.out.println("당신이 입력한 숫자는 " + num);
		
		System.out.println("제곱한 결과: " + (num * num));

	}

}
