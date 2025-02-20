package sample21;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;

public class Panel01 extends JPanel{

	private int rows = 6;
	private int columns = 9;
	private int[][] fieldData = {  // フィールドデータ
			{1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, //0
			{1, 0, 0, 1, 2, 0, 0, 1, 3, 1}, //1
			{1, 1, 0, 1, 1, 1, 0, 1, 0, 1}, //2
			{1, 0, 0, 0, 0, 0, 0, 1, 0, 1}, //3
			{1, 0, 1, 1, 1, 1, 1, 1, 0, 1}, //4
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //5
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //6
	};  

	final String g_images[] = {
			"img/chap5-1-field.png",
			"img/chap5-1-wall.png",
			"img/chap5-1-goal.png",
			"img/chap5-1-key.png",
			"img/chap5-1-man.png"
	};

	//主人公の位置
	int gX = 1, gY = 0;
	//鍵のフラグ
	boolean keyFlag = false;
	//ゴールフラグ
	boolean goalFlag = false;

	private Image wallImage;
	private Image fieldImage;
	private Image goalImage;
	private Image keyImage;
	private Image manImage;

	private static final int DURATION = 5000; // 5秒（ミリ秒単位）
	private static final int TIMER_DELAY = 100; // タイマーの更新間隔（ミリ秒単位）
	private Timer timer;
	private int currentAlpha = 0;
	private int alphaIncrement = 5; // アルファ値を増やす量
	
	//ブラックアウトインスタンス
	BlackoutPanel blackoutPanel ;
	

	class ArrowKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) { }
		@Override
		public void keyPressed(KeyEvent e) {
			// 進もうとしている座標
			int newX = gX;
			int newY = gY;

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

			switch( keyCode ) {
			case KeyEvent.VK_LEFT: newX--; newY=gY; break;
			case KeyEvent.VK_RIGHT: newX++; newY=gY; break;
			case KeyEvent.VK_UP: newX=gX;  newY--; break;
			case KeyEvent.VK_DOWN: newX=gX; newY++; break;
			}
			System.out.printf("newX:%d  newY:%d\n", newX, newY);

			// 移動チェック
			if( newX >=0 && newX < columns && newY >= 0 && newY < rows ) {
				// 壁かどうかチェック
				if( fieldData[newY][newX] != 1 ) {
					switch(fieldData[newY][newX]) {
					case 3: 
						keyFlag = true;
						fieldData[newY][newX] = 0;
						break;
					case 2:
						if(keyFlag) {
							System.out.println("succes!");
							goalFlag = true;
							blackoutPanel.startBlackout();
						}
					}
					gX = newX;
					gY = newY;
				}
			}
			//g.drawImage(manImage, gX * cellWidth, gY * cellHeight, cellWidth, cellHeight, this);
			repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {	}
	}


	public Panel01() {
		setPreferredSize(new Dimension(400, 240)); // パネルのサイズを設定
		// フォーカスを設定
		setFocusable(true);
		requestFocus();

		// keyListener
		ArrowKeyListener keyListner = new ArrowKeyListener(); 
		addKeyListener(keyListner);
		
		
		blackoutPanel = new BlackoutPanel();
		blackoutPanel.setOpaque(false);
		this.add(blackoutPanel);
		this.setLayout(new OverlayLayout( this ));
		

		// 画像の読み込み
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		wallImage = toolkit.getImage(g_images[1]); // 壁の画像
		fieldImage = toolkit.getImage(g_images[0]); // 移動可能なセルの画像
		goalImage = toolkit.getImage(g_images[2]); // ゴールの画像
		keyImage = toolkit.getImage(g_images[3]); // ゴールの画像
		manImage = toolkit.getImage(g_images[4]); // ゴールの画像

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int cellWidth = getWidth() / columns;
				int cellHeight = getHeight() / rows;
				int clickedColumn = e.getX() / cellWidth;
				int clickedRow = e.getY() / cellHeight;

				// フィールドデータのクリックされたセルを切り替え
				if (clickedRow >= 0 && clickedRow < rows && clickedColumn >= 0 && clickedColumn < columns) {
					if (fieldData[clickedRow][clickedColumn] == 0) {
						fieldData[clickedRow][clickedColumn] = 0; // 壁
					} else if ( fieldData[clickedRow][clickedColumn] == 1 ){
						fieldData[clickedRow][clickedColumn] = 1; // 移動可能なセル
					} else if ( fieldData[clickedRow][clickedColumn] == 2 ){
						fieldData[clickedRow][clickedColumn] = 2; // 移動可能なセル
					} else if ( fieldData[clickedRow][clickedColumn] == 3 ) {
						fieldData[clickedRow][clickedColumn] = 3; // 移動可能なセル

					}
					repaint();
				}
			}
		});
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int cellWidth = getWidth() / columns;
		int cellHeight = getHeight() / rows;

		System.out.println("width:"+cellWidth);
		System.out.println("height:"+cellHeight);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				int cellX = j * cellWidth;
				int cellY = i * cellHeight;

				// フィールドの状態に応じて画像を描画
				if (fieldData[i][j] == 0) {
					g.drawImage(fieldImage, cellX, cellY, cellWidth, cellHeight, this);
				} else if( fieldData[i][j] == 1) {
					g.drawImage(wallImage, cellX, cellY, cellWidth, cellHeight, this);
				} else if( fieldData[i][j] == 2 ) {
					g.drawImage(goalImage, cellX, cellY, cellWidth, cellHeight, this);
				} else if( fieldData[i][j] == 3 ) {
					g.drawImage(keyImage, cellX, cellY, cellWidth, cellHeight, this);
				}				
				//主人公表示
				if( i==gX && j==gY ) {
					g.drawImage(manImage, gX * cellWidth, gY * cellHeight, cellWidth, cellHeight, this);
				}
			}
		}
		// 主人公表示
		g.drawImage(manImage, gX * cellWidth, gY * cellHeight, cellWidth, cellHeight, this);
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

