class Calc
{
	int add(int a, int b)
	{
		return a + b;
	}
}
public class MathEx {

	public static void main(String[] args) {

		Calc calc = new Calc();
		int	nRtn = calc.add( 3, 9);
		
		System.out.println("3 + 9 = " + nRtn);
	}

}
