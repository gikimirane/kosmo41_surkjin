import java.util.Random;
import java.util.Scanner;

public class QuizBaseball {

	public static void main(String[] args) {
		
		int c1, c2, c3;
		int u1, u2, u3;
		int b=0, s=0;
		
		Random rndv = new Random();
		Scanner ins = new Scanner(System.in);
	
		while(true)
		{	
			int rnd =rndv.nextInt(900)+100;
			c1 = rnd/100;
			c2 = (rnd%100)/10;
			c3 = rnd%10;
	
			if(c1*c2*c3 ==0)	continue;
			if((c1 != c2) && (c2 != c3) && (c3 != c1)) 		break;
		}
		
		System.out.println("숫자로 하는 야구게임 시작");

		for(int i=1; s<3 && b<4; i++) 
		{
			System.out.println("세자리 숫자를 입력하세요." + "(" + i +"회)");
			int nUsr = ins.nextInt();
			u1 = nUsr/100;
			u2 = (nUsr%100)/10;
			u3 = nUsr%10;
			System.out.println(u1 + ":" + u2 + ":" + u3);
			
			if(u1==c1)					s++;
			if(u2==c2)					s++;
			if(u3==c3)					s++;
			
			if(u2==c1 || u2 ==c3)		b++;
			if(u1==c2 || u1 ==c3)		b++;
			if(u3==c2 || u3 ==c1)		b++;
			
			System.out.println(s + " Strike " + b + " Ball");
		}
		if(s==3) System.out.println("You Winner!");
		else	 System.out.println("You Loser!");
	}

}
