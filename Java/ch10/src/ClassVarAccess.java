class AccessWay{
	static int num =0 ;
	
	AccessWay() { 
		incrCnt();
	}
	
	void incrCnt()
	{
		num++;
	}
}

public class ClassVarAccess {

	public static void main(String[] args) {

		System.out.println("num = " + AccessWay.num);
		AccessWay way = new AccessWay();
		System.out.println("num = " + AccessWay.num);
		//인스턴스 이름을 통한 접근 - 비추
		System.out.println("num = " + ++way.num);
		
		//클래스 이름을 통한 접근
		System.out.println("num = " + ++AccessWay.num);
	}

}
