import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class C5_ConvertCollection {

	public static void main(String[] args) {

		List<String> lst = Arrays.asList("Toy","Box","Toy","Box","Toy");
		ArrayList<String> list = new ArrayList<>(lst);
		
		for(String s : list)
			System.out.print(s.toString() + '\t');
		System.out.println();
		
		HashSet<String> set = new HashSet<>(list);
		
		list = new ArrayList<>(set);
		
		for(String s : list)
			System.out.print(s + '\t');
		System.out.println();
	}

}
