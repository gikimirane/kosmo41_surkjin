interface Printable3
{
	void print(String doc);
}

interface ColorPrintable3 extends Printable3
{
	void printCMYK(String doc);
}

class Prn909Drv3 implements ColorPrintable3
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

public class PrintDriver3 {

	public static void main(String[] args) {

		String myDoc = "This is a report about Printer...";
		
		ColorPrintable3 prn = new Prn909Drv3();
		prn.print(myDoc);
		
		System.out.println();
		prn.printCMYK(myDoc);
	}

}
