interface Printable{
	void print(String s);
}

public class A2_OneParamNoReturn {

	public static void main(String[] args) {
		Printable p;
		
		p = (String s) -> {System.out.println(s);};
		p.print("Lamda exp one.");
		
		p = (String s) -> System.out.println(s);
		p.print("Lamda exp two.");
		
		p = (s) -> System.out.println(s);
		p.print("Lamda exp three.");
		
		p = s -> System.out.println(s);
		p.print("Lamda exp four.");
	}

}
