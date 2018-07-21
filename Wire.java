package Wires;

import java.awt.Color;
import java.awt.Graphics;

import Parts.BasePart;

public class Wire 
{
	BasePart inputPart;
	BasePart outputPart;
	private int inputNum;
	private int outputNum;

	private boolean state;

	public boolean needsUpdate;

	/*
	 * constructionState
	 * 
	 * 0 -> The wire is fully connected and configured
	 * 1 -> The wire's input node is not yet determined
	 * 2 -> The wire's output node is not yet determined 
	 */
	public int constructionState;

	int x1, y1;
	int x2, y2;
	
	protected static int BaseWireID = 0;
	protected int myID;

	public Wire(BasePart input, int inNum, BasePart output, int outNum)
	{
		myID = BaseWireID;
		BaseWireID++;
		
		constructionState = 0;

		needsUpdate = false;

		inputPart = input;
		inputNum = inNum;

		outputPart = output;
		outputNum = outNum;

		x1 = output.getX() + 30 + 5 + 2 + 1;
		y1 = output.getY() + 15;

		input.giveRef(this, inputNum);

		//System.out.println(input.getInputNum());
		updateXYInput();
	}

	public Wire(BasePart output, int outNum)
	{
		myID = BaseWireID;
		BaseWireID++;
		
		constructionState = 1;

		needsUpdate = false;

		//inputPart = input;
		//inputNum = inNum;

		outputPart = output;
		outputNum = outNum;
		
		switch (output.getOutputNum())
		{
		case 1:
			x1 = output.getX() + 30 + 5 + 2 + 1;
			y1 = output.getY() + 15;
			break;
		case 2:
			x1 = output.getX() + 30 + 5 + 2 + 1;
			y1 = output.getY() + 5 + 20 * outNum;
			break;
		}
	}
	
	/**
	 * For reconstructing wires only. Skips the construction phase of wire creation
	 * 
	 * @param outputPart The part from which the wire originates
	 * @param outputNum The node where the wire connects
	 * @param b A value that must be true, else an exception will be thrown
	 */
	public Wire(BasePart outputPart, int outputNum, boolean b) {
		if (!b)
			throw new IllegalArgumentException();
		
		constructionState = 0;
		needsUpdate = false;
		
		this.outputPart = outputPart;
		this.outputNum = outputNum;
	}

	/**
	 * Method for reconstructing a wire when loading a circuit from file
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param state
	 * @param myID
	 * @param inputPart
	 * @param inputNum
	 */
	public void reconstruct(int x1, int y1, int x2, int y2, boolean state, int myID, BasePart inputPart, int inputNum)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.state = state;
		
		this.myID = myID;
		if (this.myID <= BaseWireID)
			BaseWireID = this.myID + 1;
		
		this.inputPart = inputPart;
		this.inputNum = inputNum;
	}

	/**
	 * Method for altering where a wire is connected to its output part.
	 * <p>
	 * Should only be used during construction phase 1
	 * 
	 * @param n Node number, starting at 0
	 */
	public void setOutputNode(int n)
	{
		outputPart.switchOutputNode(n, outputNum);
		outputNum = n;
		updateXYOutput(outputPart.getX(), outputPart.getY());
	}

	/**
	 * Method for altering where a wire is connected to its input part.
	 * <p>
	 * Should only be used during construction phase 2
	 * 
	 * @param n Node number, starting at 0
	 */
	public void setInputNode(int n)
	{
		inputPart.switchInputNode(n, inputNum); // remeber when copying similar code
		inputNum = n;						   // to change THE VARIABLES AND METHODS PROPERLY
		updateXYInput();
	}
	
	/**
	 * Passes a reference to itself to the input part
	 */
	public void passRef()
	{
		inputPart.giveRef(this, inputNum);
	}

	public void setInputPart(BasePart input, int inNum)
	{
		inputPart = input;
		inputNum = inNum;

		input.giveRef(this, inputNum);

		updateXYInput();
	}

	public void updateXYInput()
	{
		switch (inputPart.getInputNum())
		{
		case 1:
			x2 = inputPart.getX()-5-2-1;
			y2 = inputPart.getY()+15;
			break;

		case 2:
			x2 = inputPart.getX()-5-2-1;
			y2 = inputPart.getY()+5 + 20 * inputNum;
			break;
		}
	}

	public void updateXYOutput(int x, int y)
	{
		switch (outputPart.getOutputNum())
		{
		case 1:
			x1 = x + 30 + 5 + 2 + 1;
			y1 = y + 15;
			break;
		case 2:
			x1 = x + 30 + 5 + 2 + 1;
			y1 = y + 5 + 20 * outputNum;
			break;
		}
		//x1 = x + 30 + 5 + 2 + 1;
		//y1 = y + 15;
	}

	public void setXY1(int x1, int y1)
	{
		this.x1 = x1;
		this.y1 = y1;
	}

	public void setXY2(int x2, int y2)
	{
		this.x2 = x2;
		this.y2 = y2;
	}

	public void draw(Graphics g)
	{
		if (constructionState > 0)
			g.setColor(Color.ORANGE);
		else if (state)
			g.setColor(Color.GREEN);
		else
			g.setColor(Color.RED);
		
		int half = (x2 - x1) / 2;
		g.drawLine(x1, y1, x1 + half, y1);
		g.drawLine(x1 + half, y1, x1 + half, y2);
		g.drawLine(x1 + half, y2, x2, y2);
		
		
		//g.drawLine(x1, y1, x2, y2);
	}

	public void update(boolean value)
	{
		//System.out.println("1");
		state = value;

		if (inputPart.getInput(inputNum) == value)
		{
			return; // no need to update
		}

		inputPart.setInput(inputNum, state);
		inputPart.update();
		inputPart.updateNow();
	}

	public void destroy()
	{
		if (inputPart != null)
			inputPart.removeRefIn(inputNum);
		if (outputPart != null)
			outputPart.removeRefOut(outputNum);
		inputPart = null;
		outputPart = null;
		//System.out.println("cleanup!");

	}
	
	public String persistent()
	{
		String s = "class=WIRE; ";
		s += "myID=" + myID + "; ";
		//s += "xy=[" + x1 + "," + y1 + "],[" + x2 + "," + y2 + "]; ";
		s += "x1=" + x1 + "; ";
		s += "y1=" + y1 + "; ";
		s += "x2=" + x2 + "; ";
		s += "y2=" + y2 + "; ";
		s += "state=" + state + "; ";
		s += "inputID=" + inputPart.getID() + "; ";
		s += "inputNum=" + inputNum + ";";
		
		s = "{" + s + "}";
		
		return s;
	}
}
