import java.util.Scanner;

public class QuizCh0303 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		System.out.println("�ΰ��� ������ �Է��Ͻÿ�");
		
		int num1 = s.nextInt();	
		int num2 = s.nextInt();
		
		System.out.println(num1 + "������ " + num2 + " �� ���� " + (num1 / num2));
		System.out.println(num1 + "������ " + num2 + " �� �������� " + (num1 % num2));
	}

}
