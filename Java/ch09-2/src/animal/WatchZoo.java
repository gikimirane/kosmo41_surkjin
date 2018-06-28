package animal;

public class WatchZoo {

		public void makeTiger2()
		{
			//Cat는 public이므로 어디서든 인스턴트 생성 가능
			zoo.MyTiger hosun = new zoo.MyTiger();
		}
		
		public void makeTiger3()
		{
			//Tiger는 default 선언으로 인스턴트 생성 불가
//			zoo.Tiger hosun = new zoo.Tiger();
		}
}
