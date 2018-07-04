interface Eatable{
	public String eat();
}

class Apple8 implements Eatable{
	public String toString() {
		return "I am an apple.";
	}
	
	@Override
	public String eat() {
		return "It tastes so good..";
	}
}

class BoxB<T extends Eatable>{
	private T ob;
	
	public void set(T o) {
		ob = o;
	}
	
	public T get() {
		System.out.println(ob.eat());
		return ob;
	}
}

public class B5_BoundeInterfaceBox {

	public static void main(String[] args) {

		BoxB<Apple8> box = new BoxB<>();
		box.set(new Apple8());
		
		Apple8 ap = box.get();
		System.out.println(ap);
	}

}
