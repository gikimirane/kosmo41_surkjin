enum Animal2{
	 DOG, CAT
}

enum Person2{
	 MAN, WOMAN
}

public class A4_SafeEnum {

	public static void main(String[] args) {

		System.out.println(Animal2.DOG);
		who(Person2.MAN);  //정상적 호출
//		who(Animal2.DOG);  //비정상적 호출로 컴파일 오류
//		who(Person2.MAN==0);  //C처럼 쓰면 에러

	}

	public static void who(Person2 man) {
		switch(man) {
		case MAN:
			System.out.println("남성 손님."); break;
		case WOMAN:
			System.out.println("여성 손님."); break;
		}
	}
}
