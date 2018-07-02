interface Cry
{
	void cry();
}

class Cat1 implements Cry
{
	public void cry()
	{
		System.out.println("야옹~");
	}
}

class Dog
{
	public void cry()
	{
		System.out.println("멍멍~");
	}
}
public class CheckCry {

	public static void main(String[] args) {

		Cat1 cat = new Cat1();
		Dog dog = new Dog();
		
		if(cat instanceof Cat1)
		{
			cat.cry();
		}
		if(cat instanceof Cry)
		{
			cat.cry();
		}
		if(dog instanceof Cry)
		{
			dog.cry();
		}
	}

}
