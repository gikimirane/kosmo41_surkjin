class ParentAdder
{
	public int add(int a, int b)
	{
		System.out.println("부모 add");
		return a + b;
	}
}

class ChildAdder extends ParentAdder
{
	public double add(double a, double b)
	{
		System.out.println("덧셈을 진행합니다.");
		return a + b;
	}
}

public class OerrideMistake {

	public static void main(String[] args) {

		ParentAdder adder = new ChildAdder();
		System.out.println(adder.add(3,  4));
	}

}
