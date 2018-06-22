
public class Quiz0512 {

	public static void main(String[] args) {
		
		int num = 1;
		int nSum = 0;
		
		do 
		{
			System.out.print(num);
			nSum = nSum + num;
			if(num < 1000) System.out.print("+");
			num ++;
		} while (num <= 1000);
		System.out.println(" = " + nSum);
	}

}
