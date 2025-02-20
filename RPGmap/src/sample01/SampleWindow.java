package sample01;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SampleWindow extends JFrame implements KeyListener, ActionListener {
	final int MAXWIDTH = 10;
	final int MAXHEIGHT = 7;

	private int characterX = 100; // キャラクターの初期X座標
	private int characterY = 100; // キャラクターの初期Y座標

	private int rows = 6;
	private int columns = 9;
	private int[][] fieldData; // フィールドデータ


	int g_mapData[][] = {

			{1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, //0
			{1, 0, 0, 1, 2, 0, 0, 1, 3, 1}, //1
			{1, 1, 0, 1, 1, 1, 0, 1, 0, 1}, //2
			{1, 0, 0, 0, 0, 0, 0, 1, 0, 1}, //3
			{1, 0, 1, 1, 1, 1, 1, 1, 0, 1}, //4
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //5
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //6
	};

	SampleWindow( String title ){
		super( title ) ;
		setSize( 500, 500 ) ;
		Container contPane = this.getContentPane();
		
		initializeField();
		
		JPanel panel = new JPanel();

		pack();
	}


	
	private void initializeField() {
		fieldData = new int[rows][columns];
		// フィールドデータの初期化
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				fieldData[i][j] = 0; // 初期状態
			}
		}
	}

	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);

		int cellWidth = getWidth() / columns;
		int cellHeight = getHeight() / rows;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				int cellX = j * cellWidth;
				int cellY = i * cellHeight;
				// フィールドの状態に応じてセルを描画
				if (fieldData[i][j] == 1) {
					g.setColor(Color.BLUE);
					g.fillRect(cellX, cellY, cellWidth, cellHeight);
				} else {
					g.setColor(Color.GREEN);
					g.fillRect(cellX, cellY, cellWidth, cellHeight);
				}
			}
		}
	}	                



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}



	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_LEFT) {
			characterX -= 10;
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			characterX += 10;
		}
		repaint();
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
