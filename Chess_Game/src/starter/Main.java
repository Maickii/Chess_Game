package starter;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JFrame;

import entities.Board;

public class Main {

	static JFrame frame;
	enum something {no, yes}
	enum somethingElse {why, whyno}
	static Date time = new Date();
	static long start = System.currentTimeMillis();
	static long finish;
	public static void main(String[] args) {
		
		frame = new JFrame();
		frame.setTitle("Chess");
		frame.setSize(816,839);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Board board = new Board();
		
		LoadImages images = new LoadImages();
		Container pane = frame.getContentPane();
		pane.setLayout(new GridLayout(1,1));
		
		pane.add(images);
		Component mouseClick = new MouseInput(); 
		frame.addMouseListener((MouseListener) mouseClick);
		frame.setVisible(true);
		
		while(true)
		{
			update(false);
		}
		
	}
	public static void update(boolean restart)
	{
//		System.out.println(System.currentTimeMillis());
		finish = System.currentTimeMillis();
//		System.out.println(start);
//		System.out.println(finish);
		long timer = (finish -start)/1000;
		if(restart)
		{
			start =	System.currentTimeMillis();
			finish = System.currentTimeMillis();
			timer = (finish -start)/1000;
		}
		if (timer >= 60)
		{
			System.exit(0);
		}
		frame.repaint();
	}

	
}
