
public class C2_VarargsBefore {

	public static void showAll(String[] vargs) {
		System.out.println("Len: " + vargs.length);
		
		for(String s : vargs)
			System.out.print(s + '\t');
		System.out.println();
	}
	public static void main(String[] args) {
		showAll(new String[]{"Box"});
		showAll(new String[]{"Box", "Toy"});
		showAll(new String[]{"Box", "Toy", "Apple"});
	}
}
//매개변수 가변인자 선언은 자바5에서 추가된 문법