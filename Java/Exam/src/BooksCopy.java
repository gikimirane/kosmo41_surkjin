class Mybook1
{
	int price;
	String title;
	
	Mybook1(String t, int p)
	{
		title= t;
		price = p;
	}
	
	Mybook1(Mybook1 copy)
	{
		title= copy.title;
		price = copy.price;
	}
	
	void print()
	{
		System.out.println("제    목: " + title);
		System.out.println("가    격: " + price);
	}
}

public class BooksCopy {

	public static void main(String[] args) {

		Mybook1 book1 = new Mybook1("게임스쿨", 10000);
		book1.print();
		
		Mybook1 book2 = new Mybook1(book1);
		book2.title = "모바일게임";
		book2.print();

	}

}
