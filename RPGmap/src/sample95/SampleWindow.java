package sample95;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;



public class SampleWindow extends JFrame  {
	public SampleWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		Container contPane = getContentPane();
		
		Panel01 imagePanel = new Panel01();		
		
		imagePanel.setPreferredSize(new Dimension(400, 400));
		contPane.add(imagePanel, BorderLayout.CENTER);
		pack();
	}
}


