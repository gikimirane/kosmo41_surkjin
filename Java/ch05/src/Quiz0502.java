import java.util.Scanner;

public class Quiz0502 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("��հ��� ���� ������ ������ �Է��Ͻÿ�");
		int count = s.nextInt();
		
		int sum = 0;
		
		for (int i = 0; i < count; i++) {
			System.out.println((i+1) + " ��° ���ڸ� �Է��Ͻÿ�");
			int num = s.nextInt();
			sum += num;
		}
		System.out.println("�Է��� ������ ��հ�: " + (double)sum/count);
	}

}
