package Core;

/* 
 * This class is not actually used by the program,
 * but is instead a place to test blocks of code
 * to see if they behave as intended.
*/

//import javax.swing.*;
import java.awt.*;

import javax.swing.JPanel;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
//import javax.swing.

@SuppressWarnings("serial")
public class testing extends JPanel 
{	
	public void paintComponent(Graphics g) 
	{
		setOpaque(false);
		super.paintComponent(g);
	}
	
	public void stuff2()
	{
		try {
			throw new IllegalArgumentException();
		}
		catch (IllegalArgumentException e)
		{
			//JOptionPane.showMessageDialog(getParent(), e.getStackTrace(), "Error", JOptionPane.WARNING_MESSAGE);
		}
		JOptionPane.showMessageDialog(getParent(), "test", "Error", JOptionPane.WARNING_MESSAGE);
	}
	
	public void stuff()
	{
		//JFileChooser chooser = new JFileChooser();
	    //chooser.setApproveButtonText("Run Application");
	    //chooser.showDialog(null, null);
		
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "JPG & GIF Images", "jpg", "gif");
	    chooser.setFileFilter(filter);

	    int returnVal = chooser.showSaveDialog(getParent());//chooser.showOpenDialog(getParent());
	    
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getAbsolutePath());
	       
	    }
	}
	
	public static void main(String[] args)
	{
		testing t = new testing();
		t.stuff2();
		
	}
}
