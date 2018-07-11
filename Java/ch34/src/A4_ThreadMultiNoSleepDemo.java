
class Task extends Thread{
	public void run() {
		int n1 = 10, n2 = 20;
		String name = Thread.currentThread().getName();
		System.out.println(name + " : " + (n1+n2));
	}
}
public class A4_ThreadMultiNoSleepDemo {

	public static void main(String[] args) {

		Thread t1 = new Task();
		Thread t2 = new Task();
		t1.start();
		t2.start();
		System.out.println("End " + Thread.currentThread().getName());

	}

}
