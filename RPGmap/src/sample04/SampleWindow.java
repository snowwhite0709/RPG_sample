package sample04;

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
	private int[][] fieldData = {

			{1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, //0
			{1, 0, 0, 1, 2, 0, 0, 1, 3, 1}, //1
			{1, 1, 0, 1, 1, 1, 0, 1, 0, 1}, //2
			{1, 0, 0, 0, 0, 0, 0, 1, 0, 1}, //3
			{1, 0, 1, 1, 1, 1, 1, 1, 0, 1}, //4
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //5
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //6
	};  // フィールドデータ



	private Image wallImage;
	private Image cellImage;

	public FieldMapWithImages() {
		setPreferredSize(new Dimension(400, 240)); // パネルのサイズを設定
		// initializeField();

		// 画像の読み込み
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		wallImage = toolkit.getImage("img/chap5-1-wall.png"); // 壁の画像
		cellImage = toolkit.getImage("img/chap5-1-field.png"); // 移動可能なセルの画像

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
						fieldData[clickedRow][clickedColumn] = 1; // 壁
					} else {
						fieldData[clickedRow][clickedColumn] = 0; // 移動可能なセル
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

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				int cellX = j * cellWidth;
				int cellY = i * cellHeight;

				// フィールドの状態に応じて画像を描画
				if (fieldData[i][j] == 1) {
					g.drawImage(wallImage, cellX, cellY, cellWidth, cellHeight, this);
				} else {
					g.drawImage(cellImage, cellX, cellY, cellWidth, cellHeight, this);
				}
			}
		}
	}

}  