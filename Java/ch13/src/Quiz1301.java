import java.util.Scanner;

class calculs
{
	static int maxNum(int[] num)
	{
		int nMax = num[0];
		
		for (int i=0; i<num.length ;i++)
		{
			if(num[i] > nMax)   nMax=num[i];
		}
		return nMax;

	}
	
	static int minNum(int[] num)
	{
		int nMin = num[0];
		
		for (int i=0; i<num.length ;i++)
		{
			if(num[i] <nMin) nMin  = num[i];
		}
		return nMin;
	}
	
	static int sumNum(int[] num)
	{
		int nSum = 0;
		
		for (int i=0; i<num.length ;i++)
		{
			nSum += num[i];
		}
		return nSum;
	}
}

public class Quiz1301 {

	public static void main(String[] args) {

		int[] num = new int[5];
		
		Scanner s = new Scanner(System.in);
			
		for(int i=0; i<num.length; i++) {
			System.out.println("숫자를 입력하시오");
			num[i] = s.nextInt();
		}

		System.out.println("최대값: " + calculs.maxNum(num));
		System.out.println("최소값: " + calculs.minNum(num));
		System.out.println("합  계: " + calculs.sumNum(num));
	}

}
