interface PrintableD{
	void print(String s);
}

public class D4_Lamda4 {
	public static void showString(PrintableD p, String s) {
		p.print(s);
	}

	public static void main(String[] args) {
		showString((s) -> {System.out.println(s);}, "What is Lamda?");
	}

}
