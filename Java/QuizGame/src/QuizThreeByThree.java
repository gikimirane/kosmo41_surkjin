import java.util.Random;
import java.util.Scanner;

public class QuizThreeByThree {

	static Boolean ins = true;
	static int x=0, y=0;
	
	public static void main(String[] args) {

		String[][] brd;
		Scanner s = new Scanner(System.in);	
		Random r = new Random();
				
		brd = suffleBrd(r);
		prnBoard(brd);
		
		while(ins) {
			prnBoard(mvBoard(prnInput(s), brd));
			//System.out.println(ins);
		}
		
	}
	
	static void clearScreen()
	{
		for(int i=0; i<50; i++) System.out.println();
	}
	
	static String[][] suffleBrd(Random r) 
	{
		int[] rv = new int[9];
		Boolean chk = false;
		String[][] brd = new String[3][3];
		
		while(!chk)
		{
			for(int i=0; i<9; i++) 
				rv[i] = r.nextInt(9)+1;
			
			chk = comArray(rv);
		}
		
		for(int i=0; i<3; i++ ) {
			for(int j=0; j<3; j++) 
			{
				brd[i][j] =Integer.toString(rv[i+j+(2*i)]);
				if(brd[i][j].equals("9")) {
					brd[i][j]="X";
					x=i; y=j;
				}
			}
		}		
		return brd;
	}
	
	static Boolean comArray(int[] rv)
	{
		for(int i=0; i<rv.length-1; i++)
        {
			for(int j=i+1; j<rv.length; j++)
			{
				if (rv[i] == rv[j]) return false;
			}
        }
		return true;
	}
	
	static String[][] mvBoard(String mv, String[][] brd)
	{
		String[][] bd = brd;
		
		switch(mv) {
		case "a": 	
		case "A": 	bd = moveLeft(brd);	break;
		case "s": 	
		case "S": 	bd = moveRight(brd);break;
		case "w": 	
		case "W": 	bd = moveUp(brd);	break;
		case "z": 	
		case "Z": 	bd = moveDown(brd);	break;
		case "k": 	
		case "K":	ins = false; 
					System.out.println("Bye~ 게임을 종료합니다.");
		}
		if(mv != "k" &&  mv != "K" )	okBoard(bd);
		return bd;
	}
	
	static String[][] moveRight(String[][] brd)
	{
		/*
		for(int i=0; i<brd.length; i++) {
			for(int j=brd.length-1; j>=1; j--) {
				if(brd[i][j-1].equals("X")) {
					brd[i][j-1] = brd[i][j];
					brd[i][j] = "X";
				}
			}
		}
		*/
		if(y+1 > 2)	return brd;
		brd[x][y] = brd[x][y+1];
		brd[x][++y] = "X";
		
		return brd;
	}
	
	static String[][] moveLeft(String[][] brd)
	{
		/*
		for(int i=0; i<brd.length; i++) {
			for(int j=0; j<brd.length-1; j++) {
				if(brd[i][j+1].equals("X")) {
					brd[i][j+1] = brd[i][j];
					brd[i][j] = "X";
				}
			}
		}
		*/
		if(y-1 < 0)	return brd;
		brd[x][y] = brd[x][y-1];
		brd[x][--y] = "X";
		return brd;
	}
	
	static String[][] moveUp(String[][] brd)
	{
		/*
		for(int i=0; i<brd.length-1; i++) {
			for(int j=0; j<brd.length; j++) {
				if(brd[i+1][j].equals("X")) {
					 brd[i+1][j] = brd[i][j];
					 brd[i][j] = "X";
				}
			}
		}
		*/
		if(x-1 < 0)	return brd;
		brd[x][y] = brd[x-1][y];
		brd[--x][y] = "X";
		return brd;
	}
	
	static String[][] moveDown(String[][] brd)
	{
		/*
		for(int i=brd.length-1; i>=1; i--) {
			for(int j=0; j<brd.length; j++) {
				if(brd[i-1][j].equals("X")) {
					brd[i-1][j] = brd[i][j];
					brd[i][j] = "X";
				}
			}
		}
		*/
		if(x+1 > 2)	return brd;
		brd[x][y] = brd[x+1][y];
		brd[++x][y] = "X"; 
		return brd;
	}
	
	static void prnBoard(String[][] brd) {
	
		if(ins == false)	return;
		clearScreen();
		for(int i=0; i<brd.length; i++) {
			for(int j=0; j<brd.length; j++) 
				System.out.printf("%2s", brd[i][j]);
			System.out.println();
		}
	}
	
	static String prnInput(Scanner s) {
		System.out.println();
		System.out.println("[ Move ] a:Left s:Right w:Up z:Down");
		System.out.println("[ Exit ] k:Exit");
		System.out.print("이동키를 입력하세요 : ");	
		String nUsr = s.next();
		return nUsr;
	}
	
	static void okBoard(String[][] brd) {
	String[][] okBrd = {{"1","2","3"},{"4","5","6"},{"7","8","X"}};
	
		for(int i=0; i<brd.length; i++) 
			for(int j=0; j<brd.length; j++)
				if(!(brd[i][j].equals(okBrd[i][j]))) return; 
		prnBoard(brd);
		System.out.println();
		System.out.println("정답입니다. 게임을 종료합니다.");
		System.out.println("Good Bye~");		
		ins = false; 
	}

}
