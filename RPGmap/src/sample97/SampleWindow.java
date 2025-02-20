package sample97;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;


public class SampleWindow extends JFrame {


	SampleWindow(String title){
		setTitle(title);
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contPane = getContentPane();
		
		
		//=======背景画像パネル生成========
		JPanel panel = new JPanel();
		panel.setLayout( new BorderLayout() ) ;
		
		
		//=======ラベル(上乗せ画像)生成========
		Dimension dim = new Dimension( 200, 150) ;
		JLabel label1 = new JLabel(new ImageIcon("img/chap5-1-key.png"));
		label1.setPreferredSize(dim) ;
		
		//=======ラベル(上乗せテキスト)生成========
		JLabel label2 = new JLabel(" Hello World ! ");
		label2.setHorizontalAlignment(JLabel.CENTER);
		
		//=======パネル生成========
		JPanel pnl1 = new JPanel() ;
		JPanel pnl2 = new JPanel() ;
		pnl1.setLayout( new BorderLayout() ) ;
		pnl2.setLayout( new BorderLayout() ) ;
		//pnl1にラベル(上乗せ画像)を透明にし、追加
		pnl1.add(label1);
		pnl1.setOpaque(false);
		//pnl1にラベル(上乗せテキスト)を透明にし、追加
		pnl2.add(label2);
		pnl2.setOpaque(false);
		
		
		//背景画像パネルに
		panel.setLayout( new OverlayLayout( panel));
		panel.add(pnl2) ;
		panel.add(pnl1) ;

		panel.setPreferredSize( dim  ) ;
		contPane.add(panel, BorderLayout.CENTER);
		
		
		pack();
	}
}

