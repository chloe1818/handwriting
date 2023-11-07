package handwriting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener{

	private boolean p;
	private boolean painting;
	private int px, py;
	private boolean[][] data;

	/**
	 * Creates a panel where the user can draw.
	 */
	public DrawPanel() {
		setPreferredSize(new Dimension(280, 280));
		setBounds(10, 10, 280, 280);
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p = false;
		painting = false;
		px = 0; py = 0;
		data = new boolean[28][28];
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void clear() {
		data = new boolean[28][28];
		getGraphics().clearRect(0, 0, 280, 280);
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	/**
	 * Use this method to get a boolean array of the bits
	 * that are drawn in the panel.
	 * @return Boolean array of bits.
	 */
	public boolean[][] getData() {
		return data;
	}

	public void mousePressed(MouseEvent e) {
		p = true;
		painting = true;
	}
	
	private void drawBox(int x, int y) {
		Graphics graphics = getGraphics();
		graphics.setColor(Color.PINK);
		graphics.drawRect((x / 10) * 10, (y / 10) * 10, 10, 10);
	}

	public void mouseDragged(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		if (x >= 280 || y >= 280) 
			return;
		Graphics graphics = getGraphics();
		graphics.setColor(Color.BLACK);
		if (painting && p) {
			graphics.drawLine(x, y, x, y);
			drawBox(x, y);
			p = false;
		} else if (painting) {
			graphics.drawLine(px,py,x,y);
			drawBox(px, py);
		}
		px = x;
		py = y;
		if (painting) data[x / 10][y / 10] = true;
	}
	
	public void mouseExited(MouseEvent e) {
		painting = false;
	}
	
	public void mouseEntered(MouseEvent e) {
		painting = true;
	}
	
	public void mouseMoved(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}

}
