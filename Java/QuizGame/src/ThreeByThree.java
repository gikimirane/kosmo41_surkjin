import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ThreeByThree {

	static Boolean ins = true;
	static int x=0, y=0;
	
	public void gameStart(PrintWriter out, String s) {

		String[][] brd;
				
		brd = suffleBrd();
		prnBoards(out, brd);
		
		while(ins) {
			prnBoards(out, mvBoard(out, prnInput(out, s), brd));
			out.println(ins);
		}
		
	}
	
	static void clearScreen(PrintWriter out)
	{
		for(int i=0; i<50; i++) out.println();
	}
	
	static String[][] suffleBrd() 
	{
		List<String> list = Arrays.asList("1","2","3","4","5","6","7","8","X");
		list = new ArrayList<>(list);
		
		String[][] brd = new String[3][3];

		Collections.shuffle(list);
		Iterator<String> itr = list.iterator();
		for(int i=0 ; itr.hasNext(); i++) {
			brd[i/3][i%3] = itr.next();
			if(brd[i/3][i%3]=="X") { x=i/3; y=i%3;}
		}
		return brd;
	}
	
	static Boolean comArray(int[] rv)
	{
		for(int i=0; i<rv.length-1; i++)
			for(int j=i+1; j<rv.length; j++)
				if (rv[i] == rv[j]) return false;
		return true;
	}
	
	static String[][] mvBoard(PrintWriter out, String mv, String[][] brd)
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
					try {
						out.println(URLEncoder.encode("Bye~ 게임을 종료합니다. ", "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
		}
		if(mv != "k" &&  mv != "K" )	okBoard(out, bd);
		return bd;
	}
	
	static String[][] moveRight(String[][] brd)
	{
		if(y+1 > 2)	return brd;
		brd[x][y] = brd[x][y+1];
		brd[x][++y] = "X";		
		return brd;
	}
	
	static String[][] moveLeft(String[][] brd)
	{
		if(y-1 < 0)	return brd;
		brd[x][y] = brd[x][y-1];
		brd[x][--y] = "X";
		return brd;
	}
	
	static String[][] moveUp(String[][] brd)
	{
		if(x-1 < 0)	return brd;
		brd[x][y] = brd[x-1][y];
		brd[--x][y] = "X";
		return brd;
	}
	
	static String[][] moveDown(String[][] brd)
	{
		if(x+1 > 2)	return brd;
		brd[x][y] = brd[x+1][y];
		brd[++x][y] = "X"; 
		return brd;
	}
	
	public static void prnBoards(PrintWriter out, String[][] brd) {
	
		if(ins == false)	return;
		clearScreen(out);
		for(int i=0; i<brd.length; i++) {
			for(int j=0; j<brd.length; j++) 
				out.printf("%2s", brd[i][j]);
			out.println();
		}
	}
	
	static String prnInput(PrintWriter out, String s) {
		out.println();
		out.println("[ Move ] a:Left s:Right w:Up z:Down");
		out.println("[ Exit ] k:Exit");
		try {
			out.println(URLEncoder.encode("이동키를 입력하세요... ", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//String nUsr = s.next();
		return s;
	}
	
	static void okBoard(PrintWriter out, String[][] brd) {
	String[][] okBrd = {{"1","2","3"},{"4","5","6"},{"7","8","X"}};
	
		for(int i=0; i<brd.length; i++) 
			for(int j=0; j<brd.length; j++)
				if(!(brd[i][j].equals(okBrd[i][j]))) return; 
		prnBoards(out, brd);
		out.println();
		
		try {
			out.println(URLEncoder.encode("정답입니다. 게임을 종료합니다.", "UTF-8"));
			out.println(URLEncoder.encode("Good Bye~", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ins = false; 
	}
}
