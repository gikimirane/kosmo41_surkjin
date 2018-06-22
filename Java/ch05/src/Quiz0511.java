import java.util.Scanner;

public class Quiz0511 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		
		System.out.println("정수를 입력하시오");
		int num = s.nextInt();
		
		long nMul=1;
		while (num > 0) {
			System.out.print(num);
			nMul = nMul * num;
			num--;
			if(num > 0) System.out.print("*");
		}
		System.out.println(" = " + nMul);
		
	}

}
