import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class NetworkEx4 {

	public static void main(String[] args) {
		URL url = null;
		BufferedReader input = null;
		String add = "http://www.google.com";
		String line = "";
		
		try {
			url = new URL(add);
			input = new BufferedReader(new InputStreamReader(url.openStream()));
			while((line=input.readLine()) !=null) {
				System.out.println(line);
			}
			input.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
