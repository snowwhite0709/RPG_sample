package sample41;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SampleWindow   extends JFrame {
	public SampleWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(450, 300)); // パネルのサイズを設定
		Container contPane = getContentPane();

		Panel01 panel = new Panel01();
		panel.setPreferredSize(new Dimension(400, 240));

		JLabel label = new JLabel("コマンド");

		contPane.add(panel, BorderLayout.CENTER);
		contPane.add(label, BorderLayout.SOUTH);
		pack();
	}
}





