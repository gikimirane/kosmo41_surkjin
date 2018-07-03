interface Printable4
{
	void print(String doc);
	default void printCMYK(String doc) {}
}

class Prn731Drv4 implements Printable4
{
	@Override
	public void print(String doc)
	{
		System.out.println("From MD-731 printer");
		System.out.println(doc);
	}
}

class Prn909Drv4 implements Printable4
{
	@Override
	public void print(String doc)
	{
		System.out.println("From MD-909 black & while ver");
		System.out.println(doc);
	}
	
	@Override
	public void printCMYK(String doc)
	{
		System.out.println("From MD-909 CMYK ver");
		System.out.println(doc);
	}
}

public class PrintDriver4 {

	public static void main(String[] args) {

		String myDoc = "This is a report about Printer...";
		
		Printable4 prn = new Prn909Drv4();
		prn.print(myDoc);
		
		System.out.println();
		prn.printCMYK(myDoc);
		
		Printable4 prn1 = new Prn731Drv4();
		System.out.println();
		prn1.print(myDoc);
	}

}
