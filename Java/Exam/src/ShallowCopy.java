class Point2 implements Cloneable
{
	private int xPos, yPos;
	
	public Point2(int x, int y)
	{
		xPos = x; yPos =y;
	}
	
	public void showPos()
	{
		System.out.printf("[%d, %d]", xPos, yPos);
		System.out.println();
	}
	
	public void changePos(int x, int y)
	{
		xPos = x; yPos =y;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}

class Rectangle2 implements Cloneable
{
	public Point2 upperLeft;
	public Point2 lowerRight;
	
	public Rectangle2(int x1, int y1, int x2, int y2)
	{
		upperLeft = new Point2(x1, y1);
		lowerRight = new Point2(x2, y2);
	}
	
	public void chagePos(int x1, int y1, int x2, int y2)
	{
		upperLeft.changePos(x1, y1);
		lowerRight.changePos(x2, y2);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	public void showPos()
	{
		System.out.print("좌측상단: ");
		upperLeft.showPos();
		System.out.print("우측상단: ");
		lowerRight.showPos();
		System.out.println();
	}
}
public class ShallowCopy {

	public static void main(String[] args) {

		Rectangle2 org = new Rectangle2(1, 1, 9, 9);
		Rectangle2 cpy;
		
		try {
			cpy = (Rectangle2)org.clone();
			org.chagePos(2, 2, 7, 7);
			
			org.showPos();
			cpy.showPos();
			
			if(org.equals(cpy))
				System.out.println("aaaaa");
			else
				System.out.println("bbbbb");
			
			if(org.lowerRight.equals(cpy.lowerRight))
				System.out.println("ccccc");
			else
				System.out.println("ddddd");		
		}
		catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

}
