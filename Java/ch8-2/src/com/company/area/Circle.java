package com.company.area;

public class Circle {
	double rad;
	final double PI = 3.14;
	
	public Circle(double r)
	{
		setRad(r);
	}

	public void setRad(double r)
	{
		if(r < 0 )
		{
			rad = 0;
			return;
		}
		rad = r;
	}
	
	public double getArea()
	{
		return (rad * rad) * PI;
	}
}
