import java.util.Random;

class MyRandom
{
		void print()
	{
		for(;;) 
		{
			Random randomv = new Random();
	
			int rnd =randomv.nextInt(900)+100;
			//System.out.println(rnd);
			int n1 = rnd/100;
			int n2 = (rnd%100)/10;
			int n3 = rnd%10;
	
			if((n1 != n2) && (n2 != n3) && (n3 != n1)) 
			{
				System.out.println(rnd);
				break;
			}
		}
	}
}

public class Quiz0515 {

	public static void main(String[] args) {

		MyRandom randmv = new MyRandom();
		randmv.print();
	}

}
