import java.util.Random;
import java.util.Scanner;

public class QuizHighLow {

	public static void main(String[] args) {

		Random random = new Random();
		Scanner s = new Scanner(System.in);
		
		String yn = "y";
		
		int i=0, nUsr;
		
		System.out.println("나는 지금 0 부터 100 사이의 값 중에 하나를 생각하겠습니다.");
		System.out.println("당신은 그 숫자를 6회안에 맞추시면 됩니다.....");

		while(yn.equals("y"))
		{
			int nCom = random.nextInt(101);
			System.out.println("몇이라고 생각합니까? <0 to 100>");
			nUsr = s.nextInt();
			
			if(nUsr > nCom) 
				System.out.println(nUsr + " 는 제가 정한 숫자보다 큽니다.");
			else if(nUsr < nCom)
				System.out.println(nUsr + " 는 제가 정한 숫자보다 작습니다.");
			else
			{
				System.out.println(nUsr + " 는 정답입니다. 축하합니다.");
				System.out.println("High / Low 게임을 플레이해 주셔서 감사합니다.");
				System.out.println("다시 하겠습니까? (y/n).... ");
				yn = s.next();
				if(yn.equalsIgnoreCase("n"))	break;
				else {							i=0;
												continue;}
			}
			System.out.println("[ " + (5-i) + " ]" + "의 기회가 남았습니다");

			i++;
			if(i>5)
			{
				System.out.println("High / Low 게임을 플레이해 주셔서 감사합니다.");
				System.out.println("다시 하겠습니까? (y/n).... ");
				yn = s.next();
				if(yn.equalsIgnoreCase("n"))	break;
				else {							i=0;
												continue;}
			}
			
		}
		System.out.println(".....Good Bye!..... ");	
	}

}
