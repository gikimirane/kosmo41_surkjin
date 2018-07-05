import java.util.TreeSet;

class Person implements Comparable<Person>{
	private String name;
	private int age;
	
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	@Override
	public String toString() {
		return name + " : " + age;
	}
	
	@Override
	public int compareTo(Person p) {
//		return this.age - p.age;
		return -(this.name.compareTo(p.name)) + (this.age - p.age);
	}
}

public class C2_ComparablePerson {

	public static void main(String[] args) {

		TreeSet<Person> tree = new TreeSet<>();
		tree.add(new Person("Yoon", 37));
		tree.add(new Person("Hong", 53));
		tree.add(new Person("Park", 22));
		tree.add(new Person("Yoon", 61));
		tree.add(new Person("Yoon", 61)); //compareTo 동일조건이면 중복불가
		
		for(Person p : tree)
			System.out.println(p);

	}

}
