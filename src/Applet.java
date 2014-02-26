

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public class Applet extends JApplet {
	public Applet() {
		this.setName("A* Search Algorithm - CamHart");
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(400, 400));
		MapPanel mapPanel = new MapPanel("img");
		this.add(mapPanel, BorderLayout.CENTER);
		//this.pack();
		this.setVisible(true);
	}
	
	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Applet applet = new Applet();
			}
		});
	}
}
