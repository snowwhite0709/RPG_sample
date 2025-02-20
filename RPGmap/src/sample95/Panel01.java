package sample95;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;


public class Panel01 extends JPanel {
	int count = 0;
	class ArrowKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) { }
		@Override
		public void keyPressed(KeyEvent e) {
			count++;
			System.out.println("count:"+count);
			System.out.println("keyListener");
			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_LEFT) {
				System.out.println("Left arrow key pressed");
			} else if (keyCode == KeyEvent.VK_RIGHT) {
				System.out.println("Right arrow key pressed");
			} else if (keyCode == KeyEvent.VK_UP) {
				System.out.println("Up arrow key pressed");
			} else if (keyCode == KeyEvent.VK_DOWN) {
				System.out.println("Down arrow key pressed");
			}
			if(count==5) {
				//Panel01の再描画
				 // Panel01の再生成
			    Panel01 newPanel = new Panel01();
			    
			    Container parent = getParent();
			    if (parent instanceof JComponent) {
			        JComponent parentComponent = (JComponent) parent;
			        parentComponent.remove(Panel01.this);  // 既存のパネルを削除
			        parentComponent.add(newPanel, BorderLayout.CENTER);  // 新しいパネルを追加
			        parentComponent.revalidate();  // コンポーネントを再構築
			        parentComponent.repaint();  // リペイント
			    }
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {	}
	}

	
	public Panel01() {
		ArrowKeyListener keyListner = new ArrowKeyListener();
		addKeyListener(keyListner);
		setFocusable(true);  // パネルがフォーカス可能であることを設定
		requestFocusInWindow();  // フォーカスを設定
	
		BlackoutPanel panel = new BlackoutPanel();
		panel.setPreferredSize(new Dimension(400, 400));
		panel.setOpaque(false);
		
		panel.startBlackout();
		this.add(panel);
		this.setLayout(new OverlayLayout( this ));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 1枚目の画像を描画する
		g.setColor(Color.RED); // 1枚目の画像の背景色
		g.fillRect(0, 0, getWidth(), getHeight());
		// ここに1枚目の画像の描画コードを追加
		
	}
}


class BlackoutPanel extends JPanel {
	private static final int DURATION = 5000; // 5秒（ミリ秒単位）
	private static final int TIMER_DELAY = 100; // タイマーの更新間隔（ミリ秒単位）
	private Timer timer;
	private int currentAlpha = 255;
	private int alphaIncrement = 5; // アルファ値を増やす量

	public BlackoutPanel() {
		timer = new Timer(TIMER_DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentAlpha > 0) {
					currentAlpha -= alphaIncrement;
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
		Color background = new Color(255, 255, 255, currentAlpha);
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public void startBlackout() {
		timer.start();
	}
}

