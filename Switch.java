package Parts;

import java.awt.Color;
import java.awt.Graphics;

public class Switch extends BasicPart
{
	public boolean justClicked = false;
	
	private static int count = 0;
	private static String keys = "1234567890";
	
	public Switch(int x, int y, char key)
	{
		super(0, 1, x, y);
		
		if (key == ' ')
		{
			key = keys.charAt(count);
			count++;
			// if more than 10 switches are placed, an IndexOutOfBoundsException is thrown
			// but for now that doesn't matter
		}
		
		this.key = key;
	}
	
	public void updateNow()
	{
		if (!needsUpdate)
			return;
		
		updateClose();
	}
	
	public void checkKey(char k)
	{
		if (k == key && !justClicked && !isMenu)
		{
			justClicked = true;
			outputs[0] = !outputs[0];
			update();
		}
	}
	
	public void releaseKey(char k)
	{
		if (k == key)
		{
			justClicked = false;
		}
	}
	
	public void drawConnectors(Graphics g)
	{
		super.drawConnectors(g);
		
		g.setColor(Color.GRAY);
		g.fillRect(getX(), getY(), 30, 30);
		
		g.setColor(Color.BLACK);
		g.drawString(key + "", getX()+10, getY()+20);
		
		if (outputs[0])
			g.setColor(Color.GREEN);
		else
			g.setColor(Color.RED);
		g.drawRect(getX()+5, getY()+5, 20, 20);
	}
	
	public String toString()
	{
		return "SWITCH";
	}
}
