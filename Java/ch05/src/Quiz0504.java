import java.util.Scanner;

public class Quiz0504 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		
		System.out.println("�ΰ��� ������ �Է��Ͻÿ�");
		int nInp1 = s.nextInt();
		int nInp2 = s.nextInt();

		System.out.println("�� ������ ��: " + (nInp1 > nInp2 ? nInp1-nInp2 : nInp2-nInp1));
	}

}
