import java.util.Scanner;

public class ScanningString {

	public static void main(String[] args) {

		String src = "1 2 3";
		Scanner sc = new Scanner(src);
		
		int n1 = sc.nextInt();
		int n2 = sc.nextInt();
		int n3 = sc.nextInt();
		
		int sum = n1 + n2 +n3;
		System.out.printf("%d + %d + %d = %d \n", n1, n2, n3, sum);
		sc.close();
	}
	
}
