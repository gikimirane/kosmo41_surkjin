
public class CompString {

	public static void main(String[] args) {

		String s1 = "Apple";
		String s2 = "apple";
		int cmp;
		
		if(s1.equals(s2))	System.out.println("두 문자열은 동일");
		else				System.out.println("두 문자열은 상이");
		
		cmp = s1.compareTo(s2);
		
		if(cmp ==0)			System.out.println("두 문자열은 동일");
		else if(cmp < 0)	System.out.println("사전 앞에 위치하는 문자: " + s1);
		else 				System.out.println("사전 앞에 위치하는 문자: " + s2);

		if(s1.compareToIgnoreCase(s2) ==0)	
							System.out.println("두 문자열은 동일");
		else								
							System.out.println("두 문자열은 상이");
	}

}
