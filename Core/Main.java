package Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Main extends JFrame
{
	private JPanel jContentPane;
	private GUI panel;

	public Main() // Constructor
	{	
		super();

		initializeScreen();

		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				myKeyPressed(evt);
			}
			public void keyReleased(KeyEvent evt) {
				myKeyReleased(evt);
			}
		});
		
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				myMousePressed(evt);
			}
			public void mouseReleased(MouseEvent evt) {
				myMouseReleased(evt);
			}
		});
		/*
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent evt) {
				myMouseMoved(evt);
			}
			public void mouseDragged(MouseEvent evt) {
				myMouseDragged(evt);
			}
		});*/
	}

	private GUI getPanel() {
		if (panel == null) {
			panel = new GUI();  // Create a new game
		}
		return panel;
	}

	private void myKeyPressed(KeyEvent e)
	{
		panel.keyPressed(e);
	}

	private void myKeyReleased(KeyEvent e)
	{
		panel.keyReleased(e);
	}

	private void myMousePressed(MouseEvent e)
	{
		panel.mousePressed(e);
	}

	private void myMouseReleased(MouseEvent e)
	{
		panel.mouseReleased(e);
	}

	private void initializeScreen() {
		//this.setResizable(false);
		this.setBounds(new Rectangle(312, 184, 250, 250)); // Position on the desktop
		this.setMinimumSize(new Dimension(550, 300));
		//this.setMaximumSize(new Dimension(250, 250));
		this.setContentPane(getJContentPane());
		
		this.setTitle("Circuits 1.1.0");
	}

	private JPanel getJContentPane() 
	{
		if (jContentPane == null) 
		{
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main thisClass = new Main();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}
}
