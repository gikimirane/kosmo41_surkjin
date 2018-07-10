import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class C3_BufferedStreamFileCopier {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("대상 파일: ");
		String src = sc.nextLine();
		System.out.print("사본 파일: ");
		String dst = sc.nextLine();

		try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(src)) ;
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dst))){
			int data;
			Instant s = Instant.now();
			while(true){
				data = in.read();
				if(data == -1)	break;
				out.write(data);
			}
			out.flush();
			Instant e = Instant.now();
			System.out.println("Seq. processing time: " + Duration.between(s, e).toMillis());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		sc.close();
	}

}
