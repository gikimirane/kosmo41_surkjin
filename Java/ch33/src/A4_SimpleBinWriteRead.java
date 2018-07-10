import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class A4_SimpleBinWriteRead {

	public static void main(String[] args) throws IOException {
		Path fp = Paths.get("D:\\jsj\\JavaStudy\\simple.bin"); 
		fp = Files.createFile(fp);
		
		byte[] buf1 = {0x13, 0x14, 0x15, 0x16};
		for(byte b : buf1)
			System.out.print(b + "\t");
		System.out.println();
		
		Files.write(fp, buf1, StandardOpenOption.APPEND);
		
		byte[] buf2 = Files.readAllBytes(fp);
		for(byte b : buf2)
			System.out.print(b + "\t");
		System.out.println();
	}

}
