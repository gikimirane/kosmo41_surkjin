import java.util.Scanner;

public class Quiz0510 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		
		System.out.println("�ΰ��� ������ �Է��Ͻÿ�");
		int num1 = s.nextInt();
		int num2 = s.nextInt();
		
		if(num1 < num2) {
			for(;num1 <= num2; num1++) {
				System.out.print(num1);
				if(num1 < num2) System.out.print("+");
			}
		} 
		else {
			for(;num1 >= num2; num1--) {
				System.out.print(num1);
				if(num1 > num2) System.out.print("+");
			}
		
		}
	}

}
