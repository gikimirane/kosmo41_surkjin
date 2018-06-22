import java.util.Scanner;

public class QuizCh0301 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		System.out.println("첫 번째 숫자를 입력하시오");
		
		int num1 = s.nextInt();
		
		System.out.println("두 번째 숫자를 입력하시오");
		
		int num2 = s.nextInt();
		
		System.out.println("당신이 입력한 숫자는 " + num1 + "," + num2);
		
		System.out.println("덧셈한 결과: " + (num1 + num2));
		System.out.println("뺄셈한 결과: " + (num1 - num2));
		System.out.println("곱셈한 결과: " + (num1 * num2));
		System.out.println("나눗셈한 결과: " + ((double)num1 / (double)num2));

	}

}
