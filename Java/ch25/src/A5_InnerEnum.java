class Customer{
	enum Gender{
		MALE, FEMALE
	} //클래스내에서만 사용가능
	
	private String name;
	private Gender gen;
	
	Customer(String n, String g){
		name = n;
		
		if(g.equals("man"))		gen = Gender.MALE;
		else					gen = Gender.FEMALE;
	}
	
	@Override
	public String toString() {
		if(gen == Gender.MALE)
			return "Thank you, Mr " + name +".";
		else
			return "Thank you, Mrs " + name + ".";
	}
}

public class A5_InnerEnum {

	public static void main(String[] args) {

		Customer cus1 = new Customer("Brown", "man");
		Customer cus2 = new Customer("Suzan Hill", "woman");
		
		System.out.println(cus1);
		System.out.println(cus2);

	}

}
