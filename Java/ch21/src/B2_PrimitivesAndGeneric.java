class Box<T>{
	private T ob;
	
	public void set(T o) {
		ob = o;
	}
	public T get() {
		return ob;
	}
}

//래퍼 클래스가 필요한 이유
public class B2_PrimitivesAndGeneric {

	public static void main(String[] args) {

		Box<Integer> iBox = new Box<>();
		iBox.set(125); //오토 박싱
		int num = iBox.get(); //오토 언박싱
		System.out.println(num);
	} 

}
