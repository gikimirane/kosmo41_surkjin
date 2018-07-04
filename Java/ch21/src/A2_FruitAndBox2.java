class Apple2
{
	public String toString()
	{
		return "I am an apple.";
	}
}

class Orange2
{
	public String toString()
	{
		return "I am an orange.";
	}
}

class Box2
{
private Object ob;
	
	public void set(Object o) {
		ob = o;
	}
	
	public Object get() {
		return ob;
	}
}

public class A2_FruitAndBox2 {

	public static void main(String[] args) {

		Box2 aBox = new Box2();
		Box2 oBox = new Box2();
		
		aBox.set(new Apple2());
		oBox.set(new Orange2());
		
		Apple2 ap = (Apple2)aBox.get();
		Orange2 or = (Orange2)oBox.get();

		System.out.println(ap);
		System.out.println(or);

	}

}
