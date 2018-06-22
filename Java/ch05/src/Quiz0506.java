import java.util.Scanner;

public class Quiz0506 {

	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		
		System.out.println("구구단 숫자를 입력하시오");
		int num = s.nextInt();
		
		for (int i = 9; i >= 1; i--) 
		{
			System.out.println(num + " X " + i + " = " +num*i + " ");
		}
		

	}

}
