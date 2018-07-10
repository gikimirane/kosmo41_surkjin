import java.nio.file.Path;
import java.nio.file.Paths;

public class A2_CurrentDir {

	public static void main(String[] args) {

		Path cur = Paths.get("");
		String cdir;
		
		if(cur.isAbsolute())
			cdir = cur.toString();
		else
			cdir = cur.toAbsolutePath().toString();
		System.out.println(cdir);
//		System.out.println(cur.toAbsolutePath().toString());

	}

}
