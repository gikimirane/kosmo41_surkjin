interface Calculate1{
	int cal(int a, int b);
}

public class A4_TwoParamAndReturn {

	public static void main(String[] args) {
		Calculate1 c;
		c = (a, b) -> {return a + b; }; //return문이면 중괄호 생략불가
		System.out.println(c.cal(4, 3));

		c = (a, b) -> a + b;
		System.out.println(c.cal(4, 3));
	}

}
