
public class NullPointer {

	public static void main(String[] args) {

		String str = null;
		System.out.println(str);
		if(str != null) {
			int len = str.length();	//Exception
		}
	}

}
