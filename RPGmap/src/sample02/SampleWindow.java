package sample02;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SampleWindow  extends JFrame {
	public SampleWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(400, 240)); // パネルのサイズを設定

		FieldMapWithJPanel panel = new FieldMapWithJPanel();

		add(panel);

		pack();

	}

}



class FieldMapWithJPanel extends JPanel {
	private int rows = 6;
	private int columns = 9;
	private int[][] fieldData; // フィールドデータ

	public FieldMapWithJPanel() {
		setPreferredSize(new Dimension(400, 240)); // パネルのサイズを設定
		initializeField();

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
				fieldData[i][j] = 0; // 初期状態: 壁
			}
		}
		// 一部のセルを移動可能なセルに設定
		fieldData[2][2] = 1;
		fieldData[3][3] = 1;
		fieldData[4][4] = 1;
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

				// フィールドの状態に応じてセルを描画
				if (fieldData[i][j] == 1) {
					g.setColor(Color.BLACK);
					g.fillRect(cellX, cellY, cellWidth, cellHeight);
				} else if (fieldData[i][j] == 0) {
					g.setColor(Color.GREEN);
					g.fillRect(cellX, cellY, cellWidth, cellHeight);
				} else {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(cellX, cellY, cellWidth, cellHeight);
				}
			}
		}
	}
}
