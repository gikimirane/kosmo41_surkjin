import java.util.Scanner;

public class QuizCh0301 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		System.out.println("ù ��° ���ڸ� �Է��Ͻÿ�");
		
		int num1 = s.nextInt();
		
		System.out.println("�� ��° ���ڸ� �Է��Ͻÿ�");
		
		int num2 = s.nextInt();
		
		System.out.println("����� �Է��� ���ڴ� " + num1 + "," + num2);
		
		System.out.println("������ ���: " + (num1 + num2));
		System.out.println("������ ���: " + (num1 - num2));
		System.out.println("������ ���: " + (num1 * num2));
		System.out.println("�������� ���: " + ((double)num1 / (double)num2));

	}

}
