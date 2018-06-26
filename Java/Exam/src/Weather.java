class Wdata
{
	int mon;
	int day;
	String sky;
}

public class Weather {

	public static void main(String[] args) {
		Wdata today = new Wdata();
		today.mon = 10;
		today.day = 5;
		today.sky = "맑음";
		
		System.out.println(	today.mon + " 월 " +
							today.day + " 일 " +
							today.sky);

	}

}
