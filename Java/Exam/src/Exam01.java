class calcl
{
	int x1 = 0;
	int x2 = 0;
	
	calcl()
	{
	}
	
	calcl(int n1, int n2){
		x1 = x1 + n1;
		x2 = x2 + n2;
	}
	
	int Add(int n1, int n2)
	{
		return ((n1 + x1) + (n2 + x2));
	}
}

public class Exam01 {

	public static void main(String[] args) {

		int num1 = 5;
		int num2 = 7;
		
		calcl mycalc = new calcl();
		int num3 = mycalc.Add(num1, num2);

		int num4 = num3 * 10 -20;
		System.out.println(num4);
	}

}
