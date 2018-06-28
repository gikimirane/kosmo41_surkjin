
public class UnsafeCircle {

	public static void main(String[] args) {

		Circle c = new Circle(1.5);
		System.out.println(c.getArea());
		
		c.setRad(2.5);
		System.out.println(c.getArea());
		c.setRad(-3.3);
		System.out.println(c.getArea());
		// c.rad = 4.5;  //변수를 private로 하지 않으면 직접 데이타에 접근 가능 (위험)
		System.out.println(c.getArea());
	}

}
