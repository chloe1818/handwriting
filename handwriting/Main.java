package handwriting;

import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
	static Network n;
	static DrawPanel drawPanel;
	public static void readFile(double[] a, String fn) {
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(fn));
			for (int i = 0; i < a.length; i++) {
				long l = dis.readLong();
				l = Long.reverseBytes(l);
				a[i] = Double.longBitsToDouble(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("Neural Network App");
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel p = new JPanel();
		
		drawPanel = new DrawPanel();
		p.add(drawPanel);
		frame.add(p, BorderLayout.CENTER);
		drawPanel.clear();
		
		JPanel p2 = new JPanel();
		JButton recognize = new JButton("Recognize");
		recognize.addActionListener(new Handler());
		p2.add(recognize, BorderLayout.EAST);
		JButton clear = new JButton("Clear");
		clear.addActionListener(new Handler2());
		p2.add(clear, BorderLayout.WEST);
		frame.add(p2, BorderLayout.SOUTH);
		
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		mb.add(file);
		
		
		frame.setJMenuBar(mb);
	
		n = new Network(new int[] { 28 * 28, 36, 10 });
		
		// Load weights
		readFile(n.biases[0].matrix, "biases0.bin");
		//n.biases[0].dump();
		readFile(n.biases[1].matrix, "biases1.bin");
		readFile(n.weights[0].matrix, "weights0.bin");
		readFile(n.weights[1].matrix, "weights1.bin");
	}
	
	private static class Handler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Matrix2D m = new Matrix2D(1, 28*28);
			boolean[][] b = drawPanel.getData();
			for (int i = 0; i < 28; i++)
				for (int j = 0; j < 28; j++)
					m.matrix[j * 28 + i] = b[i][j] ? 1 : 0;
			
			Matrix2D q = n.feedforward(m);
			double max = 0;
			int index = 0;
			for (int i = 0; i < 10; i++)
				if (q.matrix[i] > max) {
					max = q.matrix[i];
					index = i;
				}
			System.out.printf("Guessed number: %d (%.2f%% confidence)\n", index, max*100);
		}
	}
	private static class Handler2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			drawPanel.clear();
		}
	}
}
