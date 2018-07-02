/*
class ShiftArray
{
	int[][] Shift(int[][] num)
	{
		int[][] temp =new int[4][4];
		for (int i=0; i<4; i++)
		{
			for (int j=3, k=0; j>=0; j--, k++)	
			{
				temp[i][k] = num[j][i];
				System.out.print(num[j][i] + "\t");
			}
			System.out.println();
		}
		System.out.println();
		return temp;
	}
}
*/

public class Quiz1310 {

	static int[][] shiftArray(int[][] num)
	{
		int[][] temp =new int[num.length][num.length];
		for (int i=0; i<num.length; i++)
		{
			for (int j=num.length-1, k=0; j>=0; j--, k++)	
			{
				temp[i][k] = num[j][i];
				System.out.print(num[j][i] + "\t");
			}
			System.out.println();
		}
		System.out.println();
		return temp;
	}

	public static void main(String[] args) {

		int[][] num = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
		
		for (int i=0; i<4; i++)
		{
			for (int j=0; j<4; j++)		System.out.print(num[i][j] + "\t");
			System.out.println();
		}
		System.out.println();
		
		for (int i=0; i<3; i++)			num = shiftArray(num);
	}

}
