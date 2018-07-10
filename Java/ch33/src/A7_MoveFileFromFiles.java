import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class A7_MoveFileFromFiles {

	public static void main(String[] args) throws IOException {

		Path src = Paths.get("D:\\jsj\\JavaStudy\\empty");
		Path dst = Paths.get("D:\\jsj\\JavaStudy\\empty2");

		//디렉토리 이동
		Files.move(src, dst, StandardCopyOption.REPLACE_EXISTING);
	}

}
