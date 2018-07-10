import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class A3_Write65ToFile2 {

	public static void main(String[] args) {
		OutputStream out = null;
		try{
		out = new FileOutputStream("data.dat");
		out.write(66); //ASCII 65='A'
		}catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(out != null)
				try {
				out.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
		}
	}

}
