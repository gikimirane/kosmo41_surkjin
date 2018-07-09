@FunctionalInterface
interface Calculate3<T>{ //제너릭기반 함수형 인터페이스
	T cal(T a, T b);
}

public class B_LamdaGeneric {

	public static void main(String[] args) {
		Calculate3<Integer> ci = (a, b) -> a + b; 
		System.out.println(ci.cal(4, 3));

		Calculate3<Double> cd = (a, b) -> a + b; 
		System.out.println(cd.cal(4.32, 3.45));
	}

}
