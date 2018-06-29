import java.util.Scanner;

public class Quiz1303 {

	public static void main(String[] args) {

		int[] num = new int[10];
		Scanner s = new Scanner(System.in);
		
		
		for(int i=0; i<num.length; i++) 
		{
			System.out.println("숫자를 입력하시오");
			num[i] = s.nextInt();
		}
		
		int n0=0, n1=0;
		
		for(int i=0; i<num.length; i++) 
		{
			if(num[i] % 2 == 0) n0++;
			else				n1++;
			
		}
		
		System.out.print("홀수 출력: ");
		for(int i=0, j=0; i<num.length; i++) 
		{
			
			if(num[i] % 2 == 1)
			{
				System.out.print(num[i]);
				j++;
				if(j < n1) System.out.print(", ");
			}
		}
		System.out.println();
		
		System.out.print("짝수 출력: ");
		for(int i=0, j=0; i<num.length; i++) 
		{
			
			if(num[i] % 2 == 0)
			{
				System.out.print(num[i]);
				j++;
				if(j < n0) System.out.print(", ");
			}
		}
		System.out.println();
	}

}
