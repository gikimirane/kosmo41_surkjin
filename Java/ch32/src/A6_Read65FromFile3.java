import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class A6_Read65FromFile3 {

	public static void main(String[] args) {
		
		try(OutputStream out = new FileOutputStream("data.dat")){
			 out.write(66); //ASCII 66='B'
			}catch(IOException e) {
				e.printStackTrace();
			}

		try(InputStream in = new FileInputStream("data.dat")){
			//int dat = in.read();
			char cdat = (char) in.read();
			//System.out.println(dat);
			System.out.println(cdat);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
