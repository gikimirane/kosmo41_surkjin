import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class StrComp implements Comparator<String>{
	@Override
	public int compare(String s1, String s2) {
		return s1.length()-s2.length();
	}
}

public class C1_SortComprator {

	public static void main(String[] args) {

		List<String> list = new ArrayList<>();
		list.add("BOX");
		list.add("ROBOT");
		list.add("APPLE");
		
		StrComp cmp = new StrComp(); //정렬과 탐색의 기준
		Collections.sort(list, cmp);
		System.out.println(list);
	}
}
