
public class Quiz0509 {

	public static void main(String[] args) {

		int num = 1;
		int nSum = 0;
		
		do {
			if(num%2 == 0)
			nSum = nSum + num;
			num ++;
		} while (num <= 100);
		System.out.println("Â¦¼öÀÇ ÇÕ: " + nSum);
	}

}
