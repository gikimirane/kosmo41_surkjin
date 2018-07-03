import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class IOExceptionase {

	public static void main(String[] args) {

		Path file = Paths.get("D:\\jsj\\study\\Simple.txt");
		BufferedWriter writer = null;
		
		try {
			writer = Files.newBufferedWriter(file);
			writer.write('A');
			writer.write('Z');
			
			if(writer != null)
				writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
