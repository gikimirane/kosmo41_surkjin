
public class C1_Varargs {

	public static void showAll(String...vargs) {
		System.out.println("Len: " + vargs.length);
		
		for(String s : vargs)
			System.out.print(s + '\t');
		System.out.println();
	}
	
	public static void main(String[] args) {

		showAll("Box");
		showAll("Box", "Toy");
		showAll("Box", "Toy", "Apple");

	}

}
