interface Animal2
{
	void cry();
}

class Cat2 implements Animal2
{
	public void cry()
	{
		System.out.println("야옹~");
	}
}

class Dog2 implements Animal2
{
	public void cry()
	{
		System.out.println("멍멍~");
	}
}

public class CheckCry2 {

	public static void main(String[] args) {

		Animal2 cat = new Cat2();
		Animal2 dog = new Dog2();
		
		checkAnimal(cat);
		checkAnimal(dog);
	}
	
	static void checkAnimal(Animal2 animal)
	{
		if(animal instanceof Cat2)
		{
			System.out.println("고양이");
			animal.cry();
		} else if(animal instanceof Dog2)
		{
			System.out.println("강아지");
			animal.cry();
		}
	}

}
