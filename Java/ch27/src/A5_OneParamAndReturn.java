interface HowLong{
	int len(String s);
}

public class A5_OneParamAndReturn {

	public static void main(String[] args) {

		HowLong h1 = s -> s.length();
		//int num = h1.length("I am so happy.");
		System.out.println(h1.len("I am so happy."));
	}

}
