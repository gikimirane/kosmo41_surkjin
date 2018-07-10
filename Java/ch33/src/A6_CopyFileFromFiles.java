import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class A6_CopyFileFromFiles {

	public static void main(String[] args) throws IOException {

		Path src = Paths.get("D:\\jsj\\JavaStudy\\simple.txt");
		Path dst = Paths.get("D:\\jsj\\JavaStudy\\simple1.txt");

		Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
	}

}
