import java.util.Scanner;

public class Quiz1304 {

	public static void main(String[] args) {

		int[] num = new int[10];
		Scanner s = new Scanner(System.in);
		
		
		for(int i=0; i<num.length; i++) 
		{
			System.out.println("숫자를 입력하시오");
			num[i] = s.nextInt();
		}
		
		int[] num0 = new int[num.length];
		int[] num1 = new int[num.length];
		int n0=0, n1=0;
		
		for(int i=0; i<num.length; i++) 
		{
			
			if(num[i] % 2 == 1)
			{
				num1[n1] = num[i];
				n1++;
			}
		}
		
		for(int i=num.length-1; i>=0; i--) 
		{
			
			if(num[i] % 2 == 0)
			{
				num0[n0] = num[i];
				n0++;
			}
		}
		
		System.out.print("입력: ");
		for(int i=0; i<num.length; i++)
		{
			System.out.print(num[i] + " ");
		}
		System.out.println();
		
		System.out.print("출력: ");
		for(int i=0; i<n1; i++)
		{
			System.out.print(num1[i] + " ");
		}
		
		for(int i=0; i<n0; i++)
		{
			System.out.print(num0[i] + " ");
		}
		System.out.println();
	}

}
