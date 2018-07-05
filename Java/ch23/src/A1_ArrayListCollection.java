import java.util.ArrayList;
import java.util.List;

public class A1_ArrayListCollection {

	public static void main(String[] args) {

		List<String> list = new ArrayList<>();
		
		list.add("Toy");
		list.add("Box");
		list.add("Box");
		list.add("Robot");
		
		for(int i=0; i<list.size(); i++)
			System.out.print(list.get(i) + '\t');
		System.out.println();
		
		list.remove(0);
		list.add("홍길동");
		
		for(int i=0; i<list.size(); i++)
			System.out.print(list.get(i) + '\t');
		System.out.println();

	}

}
