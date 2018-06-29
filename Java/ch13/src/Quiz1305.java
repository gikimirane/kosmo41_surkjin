
import java.util.Scanner;

public class Quiz1305 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("단어를 입력해주세요");
		String word = s.next();

		String[] ar_word = new String[word.length()]; 

		String r_word = new String();
		for(int i=ar_word.length-1, j=0; i>=0; i--, j++){ 
			ar_word[j] = Character.toString(word.charAt(i));
			r_word = r_word + ar_word[j];
		}
			
		if (word.equals(r_word))
				    System.out.println("..회문입니다.");
		else		System.out.println("..회문이 아닙니다.");		
	}

}

