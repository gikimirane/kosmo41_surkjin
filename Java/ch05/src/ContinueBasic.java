
public class ContinueBasic {

	public static void main(String[] args) {
		int num = 1;
		int count = 0;
		
		while (num < 100) {
			num++;
			if (!(((num % 5) == 0) && (num % 7) == 0)) {
				continue;
			}
			
			count++;
			System.out.println(num);
		}
		System.out.println("count: " + count);
	}

}
