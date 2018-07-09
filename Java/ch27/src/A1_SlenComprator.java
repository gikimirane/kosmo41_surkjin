import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
class SlenComp implements Comparator<String>{
	@Override
	public int compare(String s1, String s2) {
		return s1.length() - s2.length();
	}
}
*/
public class A1_SlenComprator {

	public static void main(String[] args) {

		List<String> l = new ArrayList<>();
		l.add("Robot");
		l.add("Lamda");
		l.add("Box");

//		Collections.sort(l, new SlenComp());
		Collections.sort(l, (s1,s2) -> s1.length()- s2.length());

		
		for(String s: l)
			System.out.println(s);
	}

}
