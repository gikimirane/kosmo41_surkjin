import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class A1_Write65ToFile {

	public static void main(String[] args) throws IOException {

		OutputStream out = new FileOutputStream("data.dat");
		out.write(65); //ASCII 65='A'
		out.close();
	}

}
