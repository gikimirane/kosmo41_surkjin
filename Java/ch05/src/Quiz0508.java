import java.util.Scanner;

public class Quiz0508 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
				
		int i = 0;
		int nSum = 0;
		Boolean err = true;
		
		do {
			if(err)	System.out.println("���ڸ� �Է��Ͻÿ�");
			int num = s.nextInt();
			if(num < 1)
			{
				System.out.println("0 �̻��� ���ڸ� �Է��Ͻÿ�");
				err = false;
				continue;
			}
			nSum += num;
			i++;
			err = true;	
		} while (i < 5);
		
		System.out.println("�Է��� ������ ��: " + nSum);
	}

}
