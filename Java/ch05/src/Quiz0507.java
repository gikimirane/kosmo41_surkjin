import java.util.Scanner;

public class Quiz0507 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		
		System.out.println("국어 점수를 입력하시오");
		int nKorScore = s.nextInt();
		System.out.println("영어 점수를 입력하시오");
		int nEngScore = s.nextInt();
		System.out.println("수학 점수를 입력하시오");
		int nMathScore = s.nextInt();
		
		double nAvg = (nKorScore + nEngScore + nMathScore)/3.0;
		
		System.out.print("성적: ");
		if(nAvg >= 80.0) {
			System.out.println("A");
		} else if(nAvg >= 70.0) {
			System.out.println("B");
		} else	{
			System.out.println("C");
		}
	}
}
