
public class LogicalOp {

	public static void main(String[] args) {
		int num1 = 11;
		int num2 = 22;
		boolean rslt;
		
		rslt = (1 < num1) && (num1 < 100);
		System.out.println("1 �ʰ� 100 �̸��ΰ�? " + rslt);
		
		rslt = ((num2 % 2) == 0) || ((num2 % 3) == 0);
		System.out.println("2 �Ǵ� 3�� ����ΰ�? " + rslt);
		
		rslt = !(num1 !=0);
		System.out.println("0 �ΰ�? " + rslt);

	}

}
