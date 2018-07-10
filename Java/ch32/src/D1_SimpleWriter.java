import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class D1_SimpleWriter {

	public static void main(String[] args) {
		try(Writer out = new FileWriter("data.dat")){
			out.write('A');
			out.write('한');
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	}

}
