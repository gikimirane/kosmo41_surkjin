
public class EnhancedFor {

	public static void main(String[] args) {

		int[] ar = {5,6,7,8,9};
		
		for (int e: ar)
			System.out.print(e + " ");
		System.out.println();
		
		int sum = 0;
		
		for (int i: ar)
			sum += i;
		System.out.println("sum: " + sum);
	}

}
