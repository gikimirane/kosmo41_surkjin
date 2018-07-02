abstract class Calc2
{
	int a, b;
	
	abstract int result();
	
	void printRslt()
	{
		System.out.println(result());
	}
	
	void setData(int m, int n)
	{
		a = m; b=n;
	}
}

class Plus1 extends Calc2

{
	int result() {return a+b;}
}

class Minus extends Calc2
{
	int result() {return a-b;}
}

public class Calculation2 {

	public static void main(String[] args) {

		int x=54, y=12;
		
		Calc2 calc1 = new Plus1();
		Calc2 calc2 = new Minus();
		
		calc1.setData(x, y);
		calc2.setData(x, y);
		
		System.out.print(x + " + " + y + " = ");
		calc1.printRslt();
		
		System.out.print(x + " - " + y + " = ");
		calc2.printRslt();

	}

}
