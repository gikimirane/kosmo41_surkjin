
public class BuildString { 

	public static void main(String[] args) {

		//대량의 데이터 처리시 유리 I/O 횟수 줄임
		StringBuilder stbuf = new StringBuilder("123");
		
		stbuf.append(45678);
		System.out.println(stbuf.toString());
		
		stbuf.delete(0, 2);
		System.out.println(stbuf.toString());
		
		stbuf.replace(0, 3, "AB");
		System.out.println(stbuf.toString());

		stbuf.reverse();
		System.out.println(stbuf.toString());

		String sub = stbuf.substring(2, 4);
		System.out.println(sub);
	}

}
