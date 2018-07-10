import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class B1_BytesFileCopier {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.print("대상 파일: ");
		String src = sc.nextLine();
		System.out.print("사본 파일: ");
		String dst = sc.nextLine();

		try(InputStream in = new FileInputStream(src) ;
				OutputStream out = new FileOutputStream(dst)){
			int data;
			while(true){
				data = in.read();
				if(data == -1)	break;
				out.write(data);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		try(InputStream in = new FileInputStream(src) ;
				InputStream out = new FileInputStream(dst)){
			
			while(true){
				int data1 = in.read();
				int data2 = out.read();
				if(data1 == -1 || data2 == -1)	break;
				System.out.println("data1: " + data1);
				System.out.println("data2: " + data2);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		sc.close();
	}

}
