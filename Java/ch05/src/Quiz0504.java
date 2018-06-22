import java.util.Scanner;

public class Quiz0504 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		
		System.out.println("두개의 정수를 입력하시오");
		int nInp1 = s.nextInt();
		int nInp2 = s.nextInt();

		System.out.println("두 정수의 차: " + (nInp1 > nInp2 ? nInp1-nInp2 : nInp2-nInp1));
	}

}
