
public class Quiz0505 {

	public static void main(String[] args) 
	{
		
		for (int i = 2; i < 10; i++) 
		{
			
			if(i%2 == 1) continue;
			
			System.out.println("±¸±¸ " + i + " ´Ü");
			System.out.println("----------");
			
			for (int j = 1; j <= i; j++) 
			{
				System.out.println(i + " X " + j + " = " + i*j + " ");
			}
			System.out.print('\n');
		}

	}

}
