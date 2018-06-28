
public class Circle {

	private double rad;
	final double PI = 3.14;
	
	public Circle(double r)
	{
		setRad(r);
	}

	public void setRad(double r)  //Setter
	{
		if(r < 0 )
		{
			rad = 0;
			return;
		}
		rad = r;
	}
	
	public double getrad()	//Getter
	{
		return rad;
	}
	public double getArea()		
	{
		return (rad * rad) * PI;
	}
}
