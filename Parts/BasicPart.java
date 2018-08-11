package Parts;

import java.awt.Color;
import java.awt.Graphics;
import Wires.*;

public abstract class BasicPart extends BasePart
{	
	// stuff for the switch part
	//public boolean state; -- state is the value of outputs[0]
	//public boolean justClicked;
	//public void flip() {}
	
	protected char key;
	
	/**
	 * 
	 * @param in number of inputs [0 to 2]
	 * @param out number of outputs [0 to 1]
	 * @param xCoord X coordinate
	 * @param yCoord Y coordinate
	 */
	public BasicPart(int in, int out, int xCoord, int yCoord)
	{
		super(in, out, xCoord, yCoord);
	}
	
	public void updateXY()
	{
		for (Wire w: inputWires)
		{
			if (w != null)
				w.updateXYInput();
		}
		for (Wire w: outputWires)
		{
			if (w != null)
				w.updateXYOutput(getX(), getY());
		}
	}
	
	public void drawConnectors(Graphics g)
	{	
		for (Wire outWire: outputWires)
		{
			if (outWire == null)
				continue;
			
			outWire.draw(g);
		}
				
		switch (getInputNum())
		{
		case 0:
			break;
		
		case 1:
			if (inputs[0])
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.RED);
			
			g.drawLine(getX(), getY()+15, getX()-5, getY()+15);
			g.drawOval(getX()-5-1-4, getY()+15-2, 4, 4);
			break;
		
		case 2:
			if (inputs[0])
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.RED);
			
			g.drawLine(getX()+6, getY()+5, getX()-5, getY()+5);
			g.drawOval(getX()-5-1-4, getY()+5-2, 4, 4);
			
			if (inputs[1])
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.RED);
			
			g.drawLine(getX()+6, getY()+25, getX()-5, getY()+25);
			g.drawOval(getX()-5-1-4, getY()+25-2, 4, 4);
			break;
		
		default:
			throw new IllegalStateException(
					"part has " + getInputNum() + " number of inputs, 0-2 expected"
					);
		}

		switch (getOutputNum())
		{
		case 0:
			break;
		
		case 1:
			if (outputs[0])
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.RED);
			
			g.drawLine(getX()+30, getY()+15, getX()+30+5, getY()+15);
			g.drawOval(getX()+30+5+1, getY()+15-2, 4, 4);
			break;
		
		case 2:
			if (outputs[0])
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.RED);
			
			g.drawLine(getX()+30, getY()+5, getX()+30+5, getY()+5);
			g.drawOval(getX()+30+5+1, getY()+5-2, 4, 4);
			
			if (outputs[1])
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.RED);
			
			g.drawLine(getX()+30, getY()+25, getX()+30+5, getY()+25);
			g.drawOval(getX()+30+5+1, getY()+25-2, 4, 4);
			break;
		
		default:
			throw new IllegalStateException(
					"part has " + getOutputNum() + " number of outputs, 0-2 expected"
					);
		}
	}
	
	public String persistent() 
	{
		String s = "class=" + this.toString() + ";\n";
		s += "myID=" + myID + ";\n";
		s += "x=" + getX() + ";\n";
		s += "y=" + getY() + ";\n";
		s += "inputs=" + boolArrayToString(inputs) + ";\n";
		s += "outputs=" + boolArrayToString(outputs) + ";\n";
		if (this.toString().equals("SWITCH"))
			s += "key=" + key + ";\n";
		s += "isMenu=" + isMenu + ";\n";
		
		s += "io=" + wiresToString() + ";";
		
		s = "{\n" + s + "}\n";
		
		//System.out.println(s);
		return s;
	}
}

