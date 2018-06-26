
public class Quiz0514 {

	public static void main(String[] args) {
				
		for(int i=0; i<10; i++) 
		{
			for(int j=9; j>=0; j--)
			{
				 if(i+j == 9) {
					 System.out.println(i*10 + j);
					 System.out.println("+" + (j*10 + i));
					 System.out.println("---" + ((i*10 + j) + (j*10 + i)));
					 System.out.println();
				 }
			}
		}

	}
}
