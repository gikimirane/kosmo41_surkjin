
public class A1_CurrentThreadName {

	public static void main(String[] args) {

		Thread ct = Thread.currentThread();
		System.out.println(ct.getName());
	}

}
