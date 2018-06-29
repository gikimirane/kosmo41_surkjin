
public class Quiz1307 {

	public static void main(String[] args) {

		int[][] arr1 = {{1,2,3,4},{5,6,7,8}};
		int[][] arr2 = new int[arr1[0].length][arr1.length];
	
		for(int i=0; i<arr1[0].length; i++)
		{
			for(int j=0; j<arr1.length; j++)
			{
				arr2[i][j] = arr1[i/2][(i*2+j)%4];
			}
		}
	}

}
