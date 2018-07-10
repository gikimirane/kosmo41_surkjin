import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class D4_StringWriter {

	public static void main(String[] args) {
		
		String ks ="공부에는 돈이 꼭 필요한 것은 아니다....";
		String es ="Life is long if you know how to use it.";

		try(BufferedWriter bw = new BufferedWriter(new FileWriter("string.txt"))){
//			bw.write(ks);
			bw.write(ks, 0, ks.length());
			bw.newLine();
//			bw.write(es);
			bw.write(es, 0, es.length());		
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	}

}
