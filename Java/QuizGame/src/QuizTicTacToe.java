import java.util.Scanner;

public class QuizTicTacToe {	
	public static void main(String[] args) {
		String[][] brd= 
			{{" ","1"," ","|"," ","2"," ","|"," ","3"," "},
			 {"-","-","-","|","-","-","-","|","-","-","-"},
			 {" ","4"," ","|"," ","5"," ","|"," ","6"," "},
			 {"-","-","-","|","-","-","-","|","-","-","-"},
			 {" ","7"," ","|"," ","8"," ","|"," ","9"," "}};
		int[] xPos = {0,0,0,0,2,2,2,4,4,4};
		int[] yPos = {0,1,5,9,1,5,9,1,5,9};
		boolean ins = false;
		Scanner s = new Scanner(System.in);	
		int iPlay;
		
		for(int i=0; ins!=true; ) {
			if(i==9) {System.out.println("No more game!!"); break;}
			prnBoard(brd);
			iPlay = prnInput(i, s);
			if(brd[xPos[iPlay]][yPos[iPlay]] == "X" ||
					brd[xPos[iPlay]][yPos[iPlay]] == "O") continue;			
			brd[xPos[iPlay]][yPos[iPlay]] = (i%2==0 ? "X" : "O");
			ins = okBoard(brd, (i%2==0 ? "X" : "O"));
			
			if(ins==true) {
				prnBoard(brd);
				System.out.println("Player " + (i%2+1) + " has won.");
			}
			i++;
		}
}
	static void clearScreen()
	{
		for(int i=0; i<50; i++) System.out.println();
	}
	
	static void prnBoard(String[][] brd) {		
		clearScreen();
		for(int i=0; i<brd.length; i++) {
			for(int j=0; j<brd[i].length; j++) 
				System.out.printf("%s", brd[i][j]);
			System.out.println();
		}
	}
	
	static int prnInput(int player, Scanner s) {
		System.out.println();
			
		System.out.println("Play " + (player%2+1) + " please enter the number of square");
		System.out.print("where you want to place your " +  (player%2==0? "X":"O")+ ": ");

		return s.nextInt();
	}
	
	static boolean okBoard(String[][] brd, String s) {	
		if(brd[0][1]==s && brd[0][5]==s && brd[0][9]==s) return true;
		if(brd[2][1]==s && brd[2][5]==s && brd[2][9]==s) return true;
		if(brd[4][1]==s && brd[4][5]==s && brd[4][9]==s) return true;
		if(brd[0][1]==s && brd[2][1]==s && brd[4][1]==s) return true;
		if(brd[0][5]==s && brd[2][5]==s && brd[4][5]==s) return true;
		if(brd[0][9]==s && brd[2][9]==s && brd[4][9]==s) return true;
		if(brd[0][1]==s && brd[2][5]==s && brd[4][9]==s) return true;
		if(brd[0][9]==s && brd[2][5]==s && brd[4][1]==s) return true;
		
		return false;
	}
}
