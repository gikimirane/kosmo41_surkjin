package zoo;

//default 선언
class Tiger
{
	
}

//MyTiger는 public 선언이므로 인스턴트 생성 가능
public class MyTiger {
	public void makeTiger1()
	{
		//Tiger와 같은 패키지에 있으니 인스턴트 생성 가능
		Tiger hodol = new Tiger();
	}
}
