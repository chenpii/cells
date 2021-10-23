package cellmachine;

import javax.swing.JFrame;

import cell.Cell;
import field.Field;
import field.View;

public class CellMachine {

	public static void main(String[] args) {
		Field field = new Field(30, 30);
		for (int row = 0; row < field.getHeight(); row++) {
			for (int col = 0; col < field.getWidth(); col++) {
				field.place(row, col, new Cell());
			}
		}
		for (int row = 0; row < field.getHeight(); row++) {
			for (int col = 0; col < field.getWidth(); col++) {
				Cell cell = field.get(row, col);
				if (Math.random() < 0.2) {// random返回[0,1)随机数，小于0.2意味着1/5的概率，细胞是活的
					cell.reborn();
				}
			}
		}
		View view = new View(field);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 保证点击窗口X的时候，程序结束
		frame.setResizable(false);// 大小不可变
		frame.setTitle("Cells");// 标题
		frame.add(view);// 把view放到框架里
		frame.pack();// 自适应大小
		frame.setVisible(true);

		for (int i = 0; i < 1000; i++) {
			for (int row = 0; row < field.getHeight(); row++) {
				for (int col = 0; col < field.getWidth(); col++) {
					Cell cell = field.get(row, col);
					Cell[] neighbour = field.getNeighbour(row, col);
					int numOfLive = 0;
					for (Cell c : neighbour) {
						if (c.isAlive()) {
							numOfLive++;
						}
					}
					System.out.print("[" + row + "][" + col + "]:");
					System.out.print(cell.isAlive() ? "live" : "dead");
					System.out.print(":" + numOfLive + "-->");
					if (cell.isAlive()) {
						if (numOfLive < 2 || numOfLive > 3) {
							cell.die();
							System.out.print("die");
						}
					} else if (numOfLive == 3) {
						cell.reborn();
						System.out.print("reborn");
					}
					System.out.println();
				}
			}
			System.out.println("UPDATE");
			frame.repaint();// 把field里所有的数据都更新好了以后，重新画
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
