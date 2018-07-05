import java.util.Comparator;
import java.util.TreeSet;

class StringComparator implements Comparator<String>{
	public int compare(String s1, String s2) {
		return s1.length() - s2.length();
	}
}

public class C4_ComparatorString {

	public static void main(String[] args) {

		TreeSet<String> tree = new TreeSet<>(new StringComparator());
//		TreeSet<String> tree = new TreeSet<>();

		tree.add("Box");
		tree.add("Rabbit");
		tree.add("Robot");
		tree.add("aaa"); //글자수가 같아 저장안됨

		
		for(String s : tree)
			System.out.print(s.toString() + '\t');
		System.out.println();
	}

}
