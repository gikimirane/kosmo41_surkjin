interface PrintableA{
	void print(String s);
}

class Printer implements PrintableA{
	public void print(String s) {
		System.out.println(s);
	}
}

public class D1_Lamda1 {

	public static void main(String[] args) {
		PrintableA prn = new Printer();
		prn.print("What is Lamda?");
	}
}
