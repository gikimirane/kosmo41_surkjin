import java.util.Scanner;

public class Quiz0508 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
				
		int i = 0;
		int nSum = 0;
		Boolean err = true;
		
		do {
			if(err)	System.out.println("숫자를 입력하시오");
			int num = s.nextInt();
			if(num < 1)
			{
				System.out.println("0 이상의 숫자를 입력하시오");
				err = false;
				continue;
			}
			nSum += num;
			i++;
			err = true;	
		} while (i < 5);
		
		System.out.println("입력한 숫자의 합: " + nSum);
	}

}
