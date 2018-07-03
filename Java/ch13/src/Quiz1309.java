
public class Quiz1309 {

	 static void bsorting(int[] num)
		{
	        System.out.print("정렬이전: ");
			for(int i=0; i<num.length; i++)  System.out.print(num[i] + " ");
			System.out.println();
			int tmp;
			
			for(int i=0; i<num.length-1; i++)
	                       {
			     for(int j=i+1; j<num.length; j++)
			     {
			    	 if (num[i] > num[j])
			    	 {
			    		 tmp = num[i];
			    		 num[i] = num[j];
			    		 num[j]= tmp;
			    	 }
			     }
	         }
	               System.out.print("정렬결과: ");
	               for(int i=0; i<num.length; i++)   System.out.print(num[i] + " ");
		           System.out.println();
		}

		public static void main(String[] args) {

			int[] num = {210, 19, 72, 129, 34};
			
		            bsorting(num);
		}

}
