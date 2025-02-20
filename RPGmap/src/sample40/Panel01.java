package sample40;

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
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;

public class Panel01 extends JPanel{

	private int rows = 16;
	private int columns = 16;
//	private int[][] fieldData = {  // フィールドデータ
//			{1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, //0
//			{1, 0, 0, 1, 0, 0, 0, 1, 0, 1}, //1
//			{1, 1, 0, 1, 1, 1, 0, 1, 0, 1}, //2
//			{1, 0, 0, 0, 0, 0, 0, 1, 0, 1}, //3
//			{1, 0, 1, 1, 1, 1, 1, 1, 0, 1}, //4
//			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //5
//			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //6
//	};  
	
	private int[][] fieldData = new int[16][16];  // フィールドデータ
			
	
	
	final String g_images[] = {
			"img/chap5-1-field.png",
			"img/chap5-1-wall.png",
			"img/chap5-1-goal.png",
			"img/chap5-1-key.png",
			"img/chap5-1-man.png"
	};

	//主人公の位置
	int gX, gY;
	int keyX, keyY;
	int goalX, goalY;
	public void startLocation() {
		Random rand = new Random();
		while(true) {
			int y = rand.nextInt(16);
			int x = rand.nextInt(16);
			if( fieldData[y][x] == 0 ) {
				gX = x; gY = y;
				break;
			}
		}
		System.out.println("gX:"+gX + " gY:"+gY);
		
		while(true) {
			int y = rand.nextInt(16);
			int x = rand.nextInt(16);
			if( fieldData[y][x] == 0 &&  y!=gY && x!=gX ) {
				keyX = x; keyY = y;
				break;
			}
		}
		System.out.println("keyX:"+keyX + " keyY:"+keyY);
		while(true) {
			int y = rand.nextInt(16);
			int x = rand.nextInt(16);
			if( fieldData[y][x] == 0 &&  y!=gY && x!=gX &&  y!=keyY && x!=keyX    ) {
				goalX = x; goalY = y;
				break;
			}
		}
		System.out.println("goalX:"+goalX + " goalY:"+goalY);
		fieldData[keyY][keyX] = 3;
		fieldData[goalY][goalX] = 2;
	}
	
	
	public void makeField(int[][] fieldData_1) {
		Random rand = new Random();
		//int[][] fieldData_1 = new int[6][9];  // フィールドデータ
		for(int i=0; i<fieldData_1.length; i++) {
			for(int j=0; j<fieldData_1[i].length; j++) {
				if(rand.nextInt(3)<=1) {
					fieldData_1[i][j] = 0;
				} else {
					fieldData_1[i][j] = 1;
				}
			}
		}
		for(int i=0; i<fieldData_1.length; i++) {
			for(int j=0; j<fieldData_1[i].length; j++) {
				System.out.print( fieldData_1[i][j] +"," ); 
			}
			System.out.println();
		}
		
		
	}
	
	
	//鍵のフラグ
	boolean keyFlag = false;
	//ゴールフラグ
	static boolean goalFlag = false;

	private Image wallImage;
	private Image fieldImage;
	private Image goalImage;
	private Image keyImage;
	private Image manImage;

	//	private static final int DURATION = 3000; // 5秒（ミリ秒単位）
	//	private static final int TIMER_DELAY = 100; // タイマーの更新間隔（ミリ秒単位）
	//	private Timer timer;
	//	private int currentAlpha = 0;
	//	private int alphaIncrement = 5; // アルファ値を増やす量

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
		makeField(fieldData);
		
		setPreferredSize(new Dimension(400, 240)); // パネルのサイズを設定
		// フォーカスを設定
		setFocusable(true);
		requestFocus();
		
		// 勇者、鍵、ゴールの位置を決定
		startLocation();
		
		
		
		// keyListener
		ArrowKeyListener keyListner = new ArrowKeyListener(); 
		addKeyListener(keyListner);


		blackoutPanel = new BlackoutPanel();
		blackoutPanel.setOpaque(false);
		blackoutPanel.startBlackout();
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
						fieldData[clickedRow][clickedColumn] = 0; // フィールド
					} else if ( fieldData[clickedRow][clickedColumn] == 1 ){
						fieldData[clickedRow][clickedColumn] = 1; // 壁
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
				if( i==keyX && j==keyY ) {
					g.drawImage(keyImage, gX * cellWidth, gY * cellHeight, cellWidth, cellHeight, this);
				}
				if( i==goalX && j==goalY ) {
					g.drawImage(goalImage, gX * cellWidth, gY * cellHeight, cellWidth, cellHeight, this);
				}
				
				
			}
		}
		// 主人公表示
		g.drawImage(manImage, gX * cellWidth, gY * cellHeight, cellWidth, cellHeight, this);
//		g.drawImage(keyImage, keyX * cellWidth, keyY * cellHeight, cellWidth, cellHeight, this);
//		g.drawImage(goalImage, goalX * cellWidth, goalY * cellHeight, cellWidth, cellHeight, this);

	}

}


class BlackoutPanel extends JPanel {
	private static final int DURATION = 3000; // 5秒（ミリ秒単位）
	private static final int TIMER_DELAY = 10; // タイマーの更新間隔（ミリ秒単位）
	private Timer timer;
	private int currentAlpha = 255;
	private int alphaIncrement = 5; // アルファ値を増やす量

	public BlackoutPanel() {
		timer = new Timer(TIMER_DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("current:"+currentAlpha);
				if( Panel01.goalFlag) {
					if(currentAlpha >= 255) {
						timer.stop();
					} else {
						currentAlpha += alphaIncrement;
						repaint();	
					}
				} else {
					if(currentAlpha <= 0) {
						timer.stop();
					} else {
						currentAlpha -= alphaIncrement;
						repaint();	
					}
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

