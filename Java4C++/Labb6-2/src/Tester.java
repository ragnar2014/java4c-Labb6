import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Tester {

	public static void main(String[] args) {
		
		
		
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				MapDrawer draw= new MapDrawer();
				JFrame frame= new JFrame();
				frame.add(draw);
				frame.setSize(650,500);
				frame.setLocation(300, 300);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			
			}

		});

	}
	
}
