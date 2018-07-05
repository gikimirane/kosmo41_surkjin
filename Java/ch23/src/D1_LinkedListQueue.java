import java.util.LinkedList;
import java.util.Queue;

public class D1_LinkedListQueue {

	public static void main(String[] args) {

		Queue<String> que = new LinkedList<>();
		que.offer("Box");
		que.offer("Toy");
		que.add("Robot");
		
		System.out.println("next: " + que.peek());
		
		System.out.println(que.poll());
		System.out.println(que.remove());

		System.out.println("next: " + que.element());

		System.out.println(que.poll());
		
		System.out.println("next: " + que.peek());
		//System.out.println("next:1 " + que.element());
		System.out.println(que.poll());
		//System.out.println(que.remove());

	}

}
