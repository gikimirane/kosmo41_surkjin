
class ImmutableString {

	public static void main(String[] args) {

		String str1 = "Simple string";
		String str2 = "Simple string";
		
		String str3 = new String("Simple string");
		String str4 = new String("Simple string");
		
		if(str1==str2) 	System.out.println("str1과 str2는 동일 인스턴트 참조");
		else			System.out.println("str1과 str2는 다른 인스턴트 참조");
		
		if(str3==str4) 	System.out.println("str1과 str2는 동일 인스턴트 참조");
		else			System.out.println("str1과 str2는 다른 인스턴트 참조");

	}

}
