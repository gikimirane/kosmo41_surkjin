import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class A5_AsListCollection {

	public static void main(String[] args) {

		List<String> list = Arrays.asList("Toy","Box","Robot","Box");
//		list.add("홍길동")
		list = new ArrayList<>(list);
		list.add("전우치");
		for(Iterator<String> itr = list.iterator(); itr.hasNext();)
			System.out.print(itr.next() + '\t');
		System.out.println();
		
		for(Iterator<String> itr = list.iterator(); itr.hasNext();) {
			if(itr.next().equals("Box"))	itr.remove();
		}
		
		for(Iterator<String> itr = list.iterator(); itr.hasNext();)
			System.out.print(itr.next() + '\t');
		System.out.println();
	}

}
