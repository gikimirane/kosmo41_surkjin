class Point implements Cloneable
{
	private int xPos, yPos;
	
	public Point(int x, int y) {
		xPos = x; yPos = y;
	}
	
	public void showPos() {
		System.out.printf("[%d, %d]", xPos, yPos);
		System.out.println();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
public class InstanceCloning {

	public static void main(String[] args) {

		Point org = new Point(3, 4);
		Point cpy;
		
		try {
			cpy = (Point)org.clone();
			org.showPos();
			cpy.showPos();
			
			if(org.equals(cpy))
				System.out.println("aaaaaa");
			else
				System.out.println("bbbbbb");
		}
		catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

}
