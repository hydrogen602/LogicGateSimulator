package Parts;

import java.awt.Color;
import java.awt.Graphics;

public class Display extends BasicPart 
{
	public Display(int xCoord, int yCoord) 
	{
		super(1, 0, xCoord, yCoord);
	}

	//public void compute() 
	//{
	//	state = inputs[0];
	//}
	
	public void updateNow()
	{
		if (!needsUpdate)
			return;
		
		//state = inputs[0];
		//System.out.println("Update");
		
		updateClose();
	}
	
	public void drawConnectors(Graphics g)
	{
		super.drawConnectors(g);
		
		g.setColor(Color.GRAY);
		g.fillRect(getX(), getY(), 30, 30);
		
		if (inputs[0])
		{
			g.setColor(Color.GREEN);
			g.drawLine(getX()+15, getY()+5, getX()+15, getY()+25);
		}
		else
		{
			g.setColor(Color.RED);
			g.drawOval(getX()+10, getY()+5, 10, 20);
		}
	}
	
	public String toString()
	{
		return "DISPLAY";
	}
}
