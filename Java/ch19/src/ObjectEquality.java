class Inum{
	private int num;
	public Inum(int num) {
		this.num = num;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this.num == ((Inum)obj).num)	return true;
		else 							return false;
	}
}

public class ObjectEquality {

	public static void main(String[] args) {

		Inum num1 = new Inum(10);
		Inum num2 = new Inum(12);
		Inum num3 = new Inum(10);
		
		if(num1.equals(num2))
			System.out.println("num1, num2 내용 동일하다");
		else
			System.out.println("num1, num2 내용 다르다");

		if(num1.equals(num3))
			System.out.println("num1, num2 내용 동일하다");
		else
			System.out.println("num1, num2 내용 다르다");

	}

}
