import java.util.Scanner;

public class Quiz1308 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		String[] sName = {"이순신","강감찬","을지문","권율", "총점"};
		String[] sStd = {"국어","영어","수학","국사", "총점"};
		int[][] nScore = new int[sName.length][sStd.length];
		
		for(int i=0; i < sStd.length-1; i++)
		{
			System.out.println(sStd[i] + " 점수를 입력하시오");
			
			for(int j=0; j<sName.length-1; j++) {
				nScore[i][j] = s.nextInt();
				nScore[i][sName.length-1] += nScore[i][j];
				nScore[sStd.length-1][j] += nScore[i][j];
			}		
		}
		
		for(int i=0; i<sName.length+1; i++) {
			if(i == 0) System.out.print("구 분"+"\t");
			if(i >  0) System.out.print(sName[i-1] + "\t");
		}
		System.out.println();
		
		for(int i=0; i < sStd.length; i++)
		{
			System.out.print(sStd[i] + "\t");
			for(int j=0; j<sName.length; j++)
			{
				if(i==sStd.length-1 && j==sName.length-1) break;
				System.out.print(nScore[i][j] + "\t");
			}
			System.out.println();
		}
	}

}
