class Calcs
{
	int add(int a, int b)
	{
		return a + b;
	}
	
	int add(int a)
	{
		return a + 1;
	}
	
	double add(double a, double b)
	{
		return a + b;
	}
}

public class Calculates 
{

	public static void main(String[] args) 
	{

		Calcs calc = new Calcs();
		int nRtn1 = calc.add(3, 9);
		int nRtn2 = calc.add(3);
		double nRtn3 = calc.add(3.0, 9.0);
		
		System.out.println("Rtn1 = " + nRtn1);
		System.out.println("Rtn2 = " + nRtn2);
		System.out.println("Rtn3 = " + nRtn3);
	}

}
