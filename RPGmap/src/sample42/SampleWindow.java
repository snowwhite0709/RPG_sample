package sample42;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SampleWindow   extends JFrame {
	Panel01 panel;
	
	
	static boolean next = false;
	public SampleWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(450, 300)); // パネルのサイズを設定
		Container contPane = getContentPane();

		next = false;

		panel = new Panel01( this );
		panel.setPreferredSize(new Dimension(400, 240));
		JLabel label = new JLabel("コマンド");
		
		contPane.add(panel, BorderLayout.CENTER);
		contPane.add(label, BorderLayout.SOUTH);
		pack();
		
		change();
		
		System.out.println(next);
		//			contPane.removeAll();
		//			contPane.add(panel, BorderLayout.CENTER);
		//			revalidate();
		//			repaint();


	}

	//画面切り替え用メソッド
	public void change() {
		//ContentPaneにはめ込まれたパネルを削除
		System.out.println("---------------------------------------------");
		getContentPane().removeAll();
		
		Panel01 panel = new Panel01( this );

		
		super.add(panel);//パネルの追加
		validate();//更新
		repaint();//再描画
	}
	
	public void resetPanel() {
		panel = new Panel01( this );
		panel.setPreferredSize(new Dimension(400, 240));
		getContentPane().removeAll();
		getContentPane().add(panel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

}





