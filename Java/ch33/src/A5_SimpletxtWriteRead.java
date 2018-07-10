import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class A5_SimpletxtWriteRead {

	public static void main(String[] args) throws IOException {
		Path fp = Paths.get("D:\\jsj\\JavaStudy\\simple.txt"); 
//		fp = Files.createFile(fp);
		String st1 = "One Simple String";
		String st2 = "Two Simple String";

		List<String> lst1 = Arrays.asList(st1, st2);
		Files.write(fp, lst1, StandardOpenOption.APPEND);
		List<String> lst2 = Files.readAllLines(fp);
		System.out.println(lst2);
		
		for(String s : lst2)
			System.out.println(s);
	}

}
