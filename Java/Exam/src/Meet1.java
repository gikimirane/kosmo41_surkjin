interface Greet1
{
	void greet();
}

interface  Bye extends Greet1
{
	void bye();
}

class Greeting implements Bye
{
	public void greet()
	{
		System.out.println("안녕하세요!");
	}
	
	public void bye()
	{
		System.out.println("안녕히 계세요.");
	}
}

public class Meet1 {

	public static void main(String[] args) {

		Greeting greeting = new Greeting();
		greeting.greet();
		greeting.bye();

	}

}
