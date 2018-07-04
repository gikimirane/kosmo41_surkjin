import java.math.BigDecimal;

public class D2_WowBigDecimal {

	public static void main(String[] args) {

		BigDecimal d1 = new BigDecimal("1.6");
		BigDecimal d2 = new BigDecimal("0.1");
		
		double dd1 = 1.6;
		double dd2 = 0.1;

		System.out.printf("덧셈 결과: %4.2f\n" , (dd1+dd2));
		System.out.printf("곱셈 결과: %4.2f\n" , (dd1*dd2));
		System.out.println("덧셈 결과: " + d1.add(d2));
		System.out.println("덧셈 결과1: " + d1 + d2);
		System.out.println("곱셈 결과: " + d1.multiply(d2));

	}

}
