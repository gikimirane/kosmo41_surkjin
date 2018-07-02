import java.util.Scanner;

public class QuizMindReader {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		
		String yn = "y", hl;
		int nUsr=50, nMax=101, nMin=0;
		
		System.out.println("Pick a number between 0 and 100.");
		System.out.println("If the number is higher than the guess, press h.");
		System.out.println("If the number is less than the guess, press l.");
		System.out.println("If my guess is correct, press h.");

		while(!(yn.equalsIgnoreCase("n")))
		{	
			System.out.println("Is it " + nUsr + "?");
			hl = s.next();
			
			if(hl.equalsIgnoreCase("h"))
			{
				nMin = nUsr;
				nUsr = nUsr + ((nMax-nMin)/2);		

			}
			else if(hl.equalsIgnoreCase("l"))	
			{
				nMax= nUsr;
				nUsr = nUsr - ((nMax-nMin)/2);	
			}
			else
			{
				System.out.println("You are not good enough to beat me, human.");
				System.out.println("계속하려면 아무 키나 누르시오.... ");
				yn = s.next();
				if(yn.equalsIgnoreCase("n"))				break;
				nMax=101; nMin=0;
			}		
		}
		s.close();
		System.out.println(".....Good Bye!..... ");
	}

}
