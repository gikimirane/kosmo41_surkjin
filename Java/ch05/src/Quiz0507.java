import java.util.Scanner;

public class Quiz0507 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		
		System.out.println("���� ������ �Է��Ͻÿ�");
		int nKorScore = s.nextInt();
		System.out.println("���� ������ �Է��Ͻÿ�");
		int nEngScore = s.nextInt();
		System.out.println("���� ������ �Է��Ͻÿ�");
		int nMathScore = s.nextInt();
		
		double nAvg = (nKorScore + nEngScore + nMathScore)/3.0;
		
		System.out.print("����: ");
		if(nAvg >= 80.0) {
			System.out.println("A");
		} else if(nAvg >= 70.0) {
			System.out.println("B");
		} else	{
			System.out.println("C");
		}
	}
}
