import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

class AgeComparator implements Comparator<Integer>{
	public int compare(Integer n1, Integer n2) {
		return n1.intValue() - n2.intValue();
	}
}

public class E4_ComparatorTeeMap {

	public static void main(String[] args) {

		TreeMap<Integer, String> map = new TreeMap<>(new AgeComparator());
		
		map.put(45, "Brown");
		map.put(37, "James");
		map.put(23, "Martin");
		
		Set<Integer> ks = map.keySet();
		
		for(Integer n : ks)
			System.out.print(n.toString() + '\t');
		System.out.println();
		
		for(Integer n : ks)
			System.out.print(map.get(n).toString() + '\t');
		System.out.println();
		
		for(Iterator<Integer> itr = ks.iterator();itr.hasNext();)
			System.out.print(map.get(itr.next()).toString() + '\t');
		System.out.println();

	}

}
