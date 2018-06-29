import java.util.Scanner;

public class ReadString {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		System.out.print("문자열 입력: ");
		String s1 = sc.nextLine();
		
		System.out.print("문자열 입력: ");
		String s2 = sc.nextLine();
				
		System.out.printf("입력된 문자열 1: %s \n", s1);
		System.out.printf("입력된 문자열 2: %s \n", s2);

		sc.close();
	}

}

//int nextInt();
//byte nextByte();
//String nextLine();
//double nextIDouble();
//Boolean nextBoolean();