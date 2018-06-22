
public class SCE {

	public static void main(String[] args) {
		int num1 = 0;
		int num2 = 0;
		boolean rslt;
		
		rslt = ((num1 += 10) > 0) && ((num2 += 10) > 0);
		System.out.println("rslt = " + rslt);
		System.out.println("num1 = " + num1);
		System.out.println("num2 = " + num2 + '\n');
		
		rslt = ((num1 += 10) < 0) || ((num2 += 10) > 0);
		System.out.println("rslt = " + rslt);
		System.out.println("num1 = " + num1);
		System.out.println("num2 = " + num2);

	}

}
