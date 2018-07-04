class BoxA<T extends Number>{
	private T ob;
	
	public void set(T o) {
		ob = o;
	}
	public T get() {
		return ob;
	}
}

public class B4_BoundeBox {

	public static void main(String[] args) {

		BoxA<Integer> iBox = new BoxA<>();
		iBox.set(24);
		
		BoxA<Double> dBox = new BoxA<>();
		dBox.set(5.97);
		
//		Box<String> sBox = new Box<>();
//		sBox.set("String");
		
		System.out.println(iBox.get());
		System.out.println(dBox.get());

	}

}
