package zoo;

public class Cat {

	//public 선언 어디서든 호출 가능
	public void makeSound()
	{
		System.out.println("야옹");
	}
	
	//default로 선언 동일패키지내에서만 호출 가능
	void makeHappy()
	{
		System.out.println("스마일");
	}
}
