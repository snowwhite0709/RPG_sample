package sample06;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class SampleWindow   extends JFrame {
	public SampleWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(400, 240)); // パネルのサイズを設定

		FieldMapWithImages panel = new FieldMapWithImages();

		add(panel);

		pack();

	}

}


class FieldMapWithImages extends JPanel {
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


	private Image wallImage;
	private Image fieldImage;
	private Image goalImage;
	private Image keyImage;
	private Image manImage;


	public FieldMapWithImages() {
		setPreferredSize(new Dimension(400, 240)); // パネルのサイズを設定
		// initializeField();

		
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

	private void initializeField() {
		fieldData = new int[rows][columns];
		// フィールドデータの初期化
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				fieldData[i][j] = 0; // 初期状態: 移動可能なセル
			}
		}
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
		//		g.drawImage(manImage, gX * cellWidth, gY * cellHeight, cellWidth, cellHeight, this);


	}

}  