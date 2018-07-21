package Core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import FileIO.IOReader;
import FileIO.IOWriter;
import Parts.*;
import Wires.Wire;

/**
 * Version history
 * <ul>
 * 	<li>1.0.0 First version that can be fully used outside the IDE</li>
 *  <li>1.0.1 Modifications to reduce redundant code
 *  <br>   + {@code Core.HelperMethods.generateObject}
 *  </li>
 *  <li>1.1.0 Added the Xor part and class
 *  <br>   + {@code Parts.Xor}
 *  </li>
 *  <li>1.1.1 Patched a problem with the Xor part missing in the menu 
 *  when pre-1.1.0 circuits are loaded
 *  </li>
 * </ul>
 * 
 * @version 1.1.1
 * @author Jonathan
 */
@SuppressWarnings("serial")
public class GUI extends JPanel implements Runnable 
{	
	// Main:81
	
	Thread game;

	ArrayList<BasicPart> partsList;
	ArrayList<Switch> switchList;

	private boolean mouseDown;
	private BasePart grabbed;
	private int offsetX;
	private int offsetY;

	private Wire currentWire;
	private ArrayList<Integer> availableNodes;
	private int nodeIndex;
	
	public GUI()
	{
		
		availableNodes = new ArrayList<Integer>(4);

		mouseDown = false;
		offsetX = 0;
		offsetY = 0;

		switchList = new ArrayList<Switch>();
		switchList.add(new Switch(10, 10, ' '));

		switchList.add(new Switch(30, 180, 'd'));
		switchList.get(1).isMenu = false;

		partsList = new ArrayList<BasicPart>();
		partsList.add(new Display(70, 10));
		partsList.add(new And(130, 10));
		partsList.add(new Or(190, 10));
		partsList.add(new Not(250, 10));
		partsList.add(new Split(310, 10));
		partsList.add(new GatedLatch(370, 10));
		partsList.add(new Xor(430, 10));		

		//partsList.add(new And(180, 150));
		//partsList.get(5).isMenu = false;
		
		//partsList.add(new GatedLatch(200,200));

		//switchList.get(1).setOutputWire(0, partsList.get(5), 0);

		//partsList.get(0).setXY2(0, 255, 255);
		//switchList.get(0).setOutputWire(0, partsList.get(1), 0);
		//switchList.get(1).setOutputWire(0, partsList.get(1), 1);

		//partsList.get(1).setOutputWire(0, partsList.get(2), 0);

		game = new Thread(this);
		game.start();
		
	}

	public void paintComponent(Graphics g)
	{
		setOpaque(false);
		super.paintComponent(g);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 550, 50);

		for (Switch s: switchList)
		{
			s.drawConnectors(g);
		}

		for (BasicPart part: partsList)
		{
			part.drawConnectors(g);
		}
	}

	/**
	 * Controls:
	 * <ul>
	 * 	<li>Command-S Save</li>
	 * 	<li>Command-O Load</li>
	 * 	<li>Esc Stop constructing wire</li>
	 *  <li>Return Finish constructing wire</li>
	 *  <li>Up/Down Change where a wire is connected</li>
	 * </ul>
	 * 
	 * @param event
	 */
	public void keyPressed(KeyEvent event)
	{
		// TODO show what keys to press?
		if (event.getModifiers() == 4)
		{
			if (event.getKeyChar() == 's')
			{
				// file chooser
				
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Circuit Data Packs", "circuit");
			    chooser.setFileFilter(filter);

			    int returnVal = chooser.showSaveDialog(getParent());//chooser.showOpenDialog(getParent());
			    
			    if(returnVal != JFileChooser.APPROVE_OPTION)
			    		return;
			       
			    String path = chooser.getSelectedFile().getAbsolutePath();
			    if (!path.endsWith(".circuit"))
			    		path += ".circuit";
			    
			    System.out.println("path=" + path);
				
				// pack up
				String data = HelperMethods.packUp(this);
				
				// writing
				try 
				{
					IOWriter writer = new IOWriter(path);
					writer.println(data);
					writer.close();
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(getParent(), "FileNotFoundException", "Error", JOptionPane.WARNING_MESSAGE);
				}
				
			}
			
			if (event.getKeyChar() == 'o')
			{
				// file choosing
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Circuit Data Packs", "circuit");
			    chooser.setFileFilter(filter);

			    int returnVal = chooser.showOpenDialog(getParent());
			    
			    if(returnVal != JFileChooser.APPROVE_OPTION)
			    		return;
			       
			    String path = chooser.getSelectedFile().getAbsolutePath();
				
			    System.out.println("path=" + path);
			    
				// Loading
				IOReader reader;
				String data = null;
				try 
				{
					reader = new IOReader(path); //"data/file1.txt"
					data = reader.readAll();
					reader.close();
				}
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				}
				
				if (data == null)
					return; // fail
				
				//System.out.println(data);
				
				HelperMethods.reconstruct(this, data);
				//HelperMethods.unpack(data);
				
			}
			
			return;
		}
		
		
		//System.out.println(event.getKeyCode());
		for (Switch s: switchList)
		{
			s.checkKey(event.getKeyChar());
		}

		switch (event.getKeyCode())
		{
		case 27: // esc key
			if (currentWire == null)
				return;
			Wire w = currentWire;
			currentWire = null;
			w.destroy();

		case 38: // up arrow
			if (currentWire != null && nodeIndex > 0)
			{
				nodeIndex--;
				if (currentWire.constructionState == 1)
					currentWire.setOutputNode(availableNodes.get(nodeIndex));
				else
					currentWire.setInputNode(availableNodes.get(nodeIndex));
			}
			break;
		case 40: // down arrow
			//System.out.println(nodeIndex);
			if (currentWire != null && nodeIndex < availableNodes.size() - 1)
			{
				nodeIndex++;
				if (currentWire.constructionState == 1)
					currentWire.setOutputNode(availableNodes.get(nodeIndex));
				else
					currentWire.setInputNode(availableNodes.get(nodeIndex));
			}
			break;
		case 10: // return
			if (currentWire != null)
			{
				currentWire.constructionState = 0;
				currentWire = null;
			}
			break;
		default:
			return;
		}
	}

	public void keyReleased(KeyEvent event)
	{
		for (Switch s: switchList)
		{
			s.releaseKey(event.getKeyChar());
		}
	}

	public void mousePressed(MouseEvent event)
	{
		mouseDown = true;

		// figure out which one was clicked
		Point p = this.getMousePosition();

		if (p == null)
		{
			mouseDown = false;
			return;
		}

		if (event.isShiftDown())
		{
			mouseDown = false;
			// delete mode
			removePart(p);

			// TODO wire removal?
			// not really required
		}
		else if (event.isControlDown())
		{
			// wire adding mode

			// currentWire == null means no wire being made
			// currentWire == some wire and construction == 1 means wire phase 1
			// currentWire == some wire and construction == 2 means wire phase 2

			mouseDown = false;
			grabbed = getPart(p);

			if (grabbed == null)
			{
				mouseDown = false;
				return;
			}

			if (currentWire == null)
			{
				// Integers in availableNodes should be unique
				availableNodes.clear(); // if this is done before, remove this line
				for (int i = 0; i < grabbed.getOutputNum(); i++)
				{
					if (grabbed.isOutputFree(i))
					{
						availableNodes.add(new Integer(i));
					}
				}
				if (availableNodes.isEmpty())
				{
					System.err.println("All Nodes full");
					return;
				}
				//System.out.println("Node number "+nodeNumber+" available");

				nodeIndex = 0;
				currentWire = grabbed.setOutputWireUnknown(availableNodes.get(nodeIndex).intValue());

				currentWire.constructionState = 1;
				// now wait for user to click on another block or press arrows to switch nodes
			}
			else
			{
				//System.out.println("debug");
				//System.out.println(currentWire.constructionState);
				// construction phase 1 -> 2
				if (currentWire.constructionState != 1)
					return;
				
				// Integers in availableNodes should be unique
				availableNodes.clear(); // if this is done before, remove this line
				for (int i = 0; i < grabbed.getInputNum(); i++)
				{
					if (grabbed.isInputFree(i))
					{
						availableNodes.add(new Integer(i));
					}
				}
				if (availableNodes.isEmpty())
				{
					System.err.println("All Nodes full");
					return;
				}
				
				nodeIndex = 0;
				currentWire.setInputPart(grabbed, availableNodes.get(nodeIndex).intValue());
				
				currentWire.constructionState = 2;
			}
		}
		else
		{
			// other mode
			grabbed = getPart(p);

			if (grabbed == null)
			{
				mouseDown = false;
				return;
			}
			// move parts

			/**
			 * Remember to add new parts to this list
			 */
			if (grabbed.isMenu)
			{
				grabbed.isMenu = false;

				String type = grabbed.toString();
				
				if (type.equals("SWITCH"))
					switchList.add(new Switch(10, 10, ' '));
				else
				{
					BasicPart b = HelperMethods.generateObject(type);
					if (b != null)
						partsList.add(b);
					
				}
			}

			offsetX = p.x - grabbed.getX();
			offsetY = p.y - grabbed.getY();
		}
	}

	public void mouseReleased(MouseEvent event)
	{
		mouseDown = false;
		grabbed = null;
	}

	private void removePart(Point p)
	{
		for (Switch s: switchList)
		{
			if (p.x > s.getX() && p.x < s.getX() + 30 && p.y > s.getY() && p.y < s.getY() + 30)
			{
				if (s.isMenu)
					return;

				switchList.remove(s);
				s.destroy();
				return;
			}
		}
		for (BasicPart part: partsList)
		{
			if (p.x > part.getX() && p.x < part.getX() + 30 && p.y > part.getY() && p.y < part.getY() + 30)
			{
				if (part.isMenu)
					return;

				partsList.remove(part);
				part.destroy();
				return;
			}
		}
	}

	private BasicPart getPart(Point p)
	{
		for (Switch s: switchList)
		{
			if (p.x > s.getX() && p.x < s.getX() + 30 && p.y > s.getY() && p.y < s.getY() + 30)
				return s;
		}
		for (BasicPart part: partsList)
		{
			if (p.x > part.getX() && p.x < part.getX() + 30 && p.y > part.getY() && p.y < part.getY() + 30)
				return part;
		}

		return null;
	}

	public void run()
	{
		// stuff happens here

		while(true)
		{
			try
			{
				Thread.sleep(50);
			}
			catch(InterruptedException ex)
			{
			}

			if (mouseDown)
			{
				//System.out.println("stuff");
				Point p = this.getMousePosition();
				if (p != null && grabbed != null)
				{
					grabbed.setXY(p.x - offsetX, p.y - offsetY);
				}
			}

			for (Switch s: switchList)
			{
				s.updateNow();
			}

			for (BasicPart part: partsList)
			{
				part.updateNow();
			}

			if (currentWire != null && currentWire.constructionState == 1)
			{
				Point p = this.getMousePosition();
				if (p != null)
				{
					try 
					{
						currentWire.setXY2(p.x, p.y);
					}
					catch (java.lang.NullPointerException e)
					{
						e.printStackTrace();
					}
							
				}
			}

			repaint();
		}
	}
	
	// extra stuff for reconstructing
	
	/**
	 * Currently only resets the variable grabbed
	 */
	public void resetSomeVars()
	{
		grabbed = null;
	}	
}
