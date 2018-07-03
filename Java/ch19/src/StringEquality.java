
public class StringEquality {

	public static void main(String[] args) {

		String str1 = new String("So Simple");
//		String str2 = new String("So Simple");
		String str2 = str1;
		
		if(str1 == str2)
				System.out.println("str1, str2 참조대상이 동일하다");
		else	System.out.println("str1, str2 참조대상이 다르다");
		
		if(str1.equals(str2))
				System.out.println("str1, str2 내용이 동일하다");
		else	System.out.println("str1, str2 내용이 다르다");
	}

}
