package sample10;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

import javax.swing.JPanel;
import javax.swing.Timer;


public class ImageWhiteout {
	private static final int TRANSITION_DURATION = 1000; // 5 seconds
	private long startTime;

	public ImageWhiteout() {
		Timer timer = new Timer(16, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				long currentTime = System.currentTimeMillis();
				long elapsedTime = currentTime - startTime;

				if (elapsedTime >= TRANSITION_DURATION) {
					((Timer) e.getSource()).stop();
					// 5秒後にブラックアウトが完了するので、ここで必要に応じて追加の処理を行う
				} else {
					float progress = (float) elapsedTime / TRANSITION_DURATION;
					int alpha = (int) (progress * 255); // 透明度を逆にすることでブラックアウトにする
				}

			}
		});

		addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentShown(java.awt.event.ComponentEvent evt) {
				startTime = System.currentTimeMillis();
				timer.start();
			}
		});



	}



	private void addComponentListener(ComponentAdapter componentAdapter) {
		// TODO 自動生成されたメソッド・スタブ

	}
}

class ImagePanel extends JPanel {
	private Image image;
	private int alpha = 0; // 透明度を0で初期化

	public void setImage(Image image) {
		this.image = image;
		repaint();
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			//g.setColor(new Color(0, 0, 0, alpha));
			g.setColor(new Color(255, 255, 255, alpha));

			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
}


