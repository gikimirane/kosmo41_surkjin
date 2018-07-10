import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class A5_Read65FromFile3 {

	public static void main(String[] args) {

		try(OutputStream out = new FileOutputStream("data.dat")){
			 out.write(65); //ASCII 65='A'
			}catch(IOException e) {
				e.printStackTrace();
			}
		
		try(InputStream in = new FileInputStream("data.dat")){
			int dat = in.read();
			System.out.println(dat);
			System.out.printf("%c \n", dat);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
