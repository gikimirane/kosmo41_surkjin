
public class SubstringEx {

	public static void main(String[] args) {

		String str1 = "ABCDEFG";
		String result1 = str1.substring(str1.length()-3, str1.length());
		System.out.println(result1);
		
		String str2 = "AB/CD/EFG";
		int num2 = str2.indexOf("/");
		String result2 = str1.substring(num2+1);
		System.out.println(result2);
		
		String str3 = "AB/CD/EFG";
		int num3 = str3.lastIndexOf("/");
		String result3 = str1.substring(num3+1);
		System.out.println(result3);
	}

}
