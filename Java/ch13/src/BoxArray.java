//값의 저장과 참조

public class BoxArray {

	public static void main(String[] args) {

		Box[] ar = new Box[3];
		
		ar[0] = new Box(1, "First");
		ar[1] = new Box(2, "Second");
		ar[2] = new Box(3, "Third");
		
		System.out.println(ar[0]);
		System.out.println(ar[1]);
		System.out.println(ar[2]);
		
	}

}