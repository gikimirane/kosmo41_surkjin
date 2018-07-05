import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class StrComp implements Comparator<String>{
	public int compare(String s1, String s2) {
		return s1.compareToIgnoreCase(s2);
	}
}

public class D2_StringComparator {

	public static void main(String[] args) {

		List<String> list = new ArrayList<>();
		list.add("BOX");
		list.add("ROBOT");
		list.add("APPLE");
		
		StrComp cmp = new StrComp(); //정렬과 탐색의 기준
		Collections.sort(list, cmp);
		int idx = Collections.binarySearch(list, "Robot", cmp);
		System.out.println(list.get(idx));
	}

}
