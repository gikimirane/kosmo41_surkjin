class Box1<T>{
	protected T ob;
	
	public void set(T o) { ob = o;}
	public T get() { return ob;}
}

class SteelBox1<T> extends Box1<T>{
	public SteelBox1(T o) {
		ob = o;
	}
}

public class A1_GenericInheritance {

	public static void main(String[] args) {

		Box1<Integer> iBox = new SteelBox1<>(7959);
		Box1<String> sBox = new SteelBox1<>("Simple");

		System.out.println(iBox.get());
		System.out.println(sBox.get());

	}

}
