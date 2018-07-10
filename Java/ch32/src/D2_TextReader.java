import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class D2_TextReader {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("읽을 파일: ");
		String src = sc.nextLine();
		
		try(Reader in = new FileReader(src)){
			int ch;
			Instant s = Instant.now();
			while(true){
				ch = in.read();
				if(ch == -1)	break;
				System.out.print((char)ch);
			}
			Instant e = Instant.now();
			System.out.println("Seq. processing time: " + Duration.between(s, e).toMillis());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		sc.close();
	}

}
