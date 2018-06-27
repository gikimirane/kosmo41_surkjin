//인스턴스 대상 1차원 배열의 예
class Box
{
	private String conts;
	private int nums;
	
	Box(int num, String cont){
		this.conts = cont;
		this.nums = num;
	}
	
	public int getBoxNum()
	{
		return this.nums;
	}
	public String toString()
	{
		return this.conts;
	}
}

public class AraayIsInstance2 {

	public static void main(String[] args) {

		Box ar[] = new Box[5];
		System.out.println("length: " + ar.length);

	}

}
