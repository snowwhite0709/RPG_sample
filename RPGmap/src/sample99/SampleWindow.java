package sample99;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;

public class SampleWindow extends JFrame  {
	

	public SampleWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		Container contPane = getContentPane();
		
		JPanel imagePanel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// 1枚目の画像を描画する
				g.setColor(Color.RED); // 1枚目の画像の背景色
				g.fillRect(0, 0, getWidth(), getHeight());
				// ここに1枚目の画像の描画コードを追加
			}
		};
		
		
		
		
		imagePanel.setPreferredSize(new Dimension(400, 400));
		
		BlackoutPanel panel = new BlackoutPanel();
		panel.setPreferredSize(new Dimension(400, 400));
		panel.setOpaque(false);
		imagePanel.add(panel);
		
		
		JButton startButton = new JButton("Start Blackout");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.startBlackout();
			}
		}); 
		
		imagePanel.setLayout( new OverlayLayout( imagePanel) );		
		contPane.add(imagePanel, BorderLayout.CENTER);
		contPane.add(startButton, BorderLayout.SOUTH);
		pack();
		
	}



}


class BlackoutPanel extends JPanel {
	private static final int DURATION = 5000; // 5秒（ミリ秒単位）
	private static final int TIMER_DELAY = 100; // タイマーの更新間隔（ミリ秒単位）
	private Timer timer;
	private int currentAlpha = 0;
	private int alphaIncrement = 5; // アルファ値を増やす量

	public BlackoutPanel() {
		timer = new Timer(TIMER_DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentAlpha < 255) {
					currentAlpha += alphaIncrement;
					repaint();
				} else {
					timer.stop();
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color background = new Color(0, 0, 0, currentAlpha);
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public void startBlackout() {
		timer.start();
	}
}
