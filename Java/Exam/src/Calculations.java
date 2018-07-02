abstract class Clc
{
	int a, b;
	abstract void answr();
	
	void setData(int m, int n)
	{
		a=m; b=n;
	}
}

class Plus extends Clc
{
	void answr()
	{
		System.out.println( a + " + " + b + " = " + (a+b));
	}
}


public class Calculations {

	public static void main(String[] args) {

		Plus plus = new Plus();
		plus.setData(27, 32);
		plus.answr();
	}

}
