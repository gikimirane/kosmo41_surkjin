import java.util.Scanner;

public class Quiz0501 {

	public static void main(String[] args) {
		
		int num, sum = 0;
		Scanner s = new Scanner(System.in);
		
		do {
			System.out.println("���ڸ� �Է��Ͻÿ�");
		
			num = s.nextInt();
			sum = sum + num;
		
		} while (num != 0);
		System.out.println("�Է��� ������ ��: " + sum);
	}
}
