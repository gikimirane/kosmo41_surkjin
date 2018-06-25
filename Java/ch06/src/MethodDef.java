
public class MethodDef {

	public static void main(String[] args) {

		System.out.println("프로그램 시작");
		hiEveryone(12);
		hiEveryone(13);
		System.out.println("프로그램 종료");
	}

	public static void hiEveryone(int age) {
		System.out.println("Good Moring");
		System.out.println("제 나이는 " + age + " 세 입니다.");
	}
}