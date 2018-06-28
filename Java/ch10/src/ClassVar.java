class InstCnt
{
//	static int instNum = 0; //전역변수
	int instNum = 0;
	
	InstCnt(){
		System.out.println("인스턴트 생성: " + ++instNum);
	}
}

public class ClassVar {

	public static void main(String[] args) {

		InstCnt cnt1 = new InstCnt();
		InstCnt cnt2 = new InstCnt();
		InstCnt cnt3 = new InstCnt();

		

	}

}
