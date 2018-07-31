

public class Main {

	public static void main(String[] args) {

		for (int i=0; i<100; i++) {
			TestThread test = new TestThread(i);
			test.start();
		}
	}

}
