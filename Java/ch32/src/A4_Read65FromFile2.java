import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class A4_Read65FromFile2 {

	public static void main(String[] args){
		InputStream in = null;
		try {
			in = new FileInputStream("data.dat");
			int dat = in.read();
			System.out.println(dat);
			System.out.printf("%c \n", dat);

		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(in != null)
				try {
				in.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
		}
	}

}
