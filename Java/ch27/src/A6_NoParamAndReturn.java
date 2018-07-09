import java.util.Random;

interface Generator{
	//int rand();
	void rand();
}

public class A6_NoParamAndReturn {

	public static void main(String[] args) {
		Generator gen = () -> {
			Random rand = new Random();
			//return rand.nextInt(50);
			System.out.println(rand.nextInt(100));
		};
	//	System.out.println(gen.rand());
		gen.rand();
	}

}
