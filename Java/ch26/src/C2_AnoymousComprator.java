import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class C2_AnoymousComprator {

	public static void main(String[] args) {

		List<String> list = new ArrayList<>();
		list.add("BOX");
		list.add("ROBOT");
		list.add("APPLE");
		
		Comparator<String> cmp = new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.length()-s2.length();
			}
		};
			
		Collections.sort(list, cmp);
		System.out.println(list);
	}

}
