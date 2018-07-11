import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class B1_ExecutorsDemo {

	public static void main(String[] args) {

		Runnable task = () -> {
			int n1 = 10, n2 = 20;
			String name = Thread.currentThread().getName();
			System.out.println(name + " : " + (n1+n2));
		};
		
		ExecutorService exr = Executors.newSingleThreadExecutor();
		exr.submit(task);
		
		System.out.println("End " + Thread.currentThread().getName());
		exr.shutdown();
	}

}
