import java.util.Scanner;

public class Quiz0506 {

	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		
		System.out.println("������ ���ڸ� �Է��Ͻÿ�");
		int num = s.nextInt();
		
		for (int i = 9; i >= 1; i--) 
		{
			System.out.println(num + " X " + i + " = " +num*i + " ");
		}
		

	}

}
