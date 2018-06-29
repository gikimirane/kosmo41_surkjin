import java.util.Random;
import java.util.Scanner;

public class Quiz1309 {

	public static void main(String[] args) {
		String[] str = {"", "가위", "바위", "보"};
		Random randomv = new Random();
		Scanner s = new Scanner(System.in);
		int nCom;
		int nUsr;
		
		for(;;)
		{
			System.out.println("무엇을 내겠습니가? <1: 가위 2:바위 3:보 0: 종료>");
			nCom =randomv.nextInt(3)+1;
			nUsr = s.nextInt();
			if(nUsr > 2)	continue;
			else if(nUsr==0)			break;								
				
			System.out.println("사용자: " + str[nUsr] + ", 컴퓨터: " + str[nCom]);
		
			if(nUsr==nCom)	System.out.println("...비겼습니다.");
			else if((nUsr>nCom) || (nUsr==1 && nCom==3))
							System.out.println("...이겼습니다.");
			else			System.out.println("...졌습니다.");
		}
		System.out.println("---게임종료----");
	}

}
