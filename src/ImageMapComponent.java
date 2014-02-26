
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class ImageMapComponent extends JComponent {

	public final int CUR_POSITION = 3;
	public final int PATH = 2;
	public final int BAD_TILE = 0;
	public final int GOOD_TILE = 1;
	public final int DESTINATION = 9;
	BufferedImage img = null;
	private Collection<Tile> path = null;
	
	public ImageMapComponent(String imagePath) throws IOException {
		File imgFile = new File(imagePath);
		System.out.println(imgFile.getAbsolutePath());
		img = ImageIO.read(imgFile);
		this.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
		//this.setPreferredSize(new Dimension(600, 400));
	}
	
	

	
	public void setPath(Collection<Tile> path) {
		this.path  = path;
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		Graphics2D g = (Graphics2D)graphic;	
		assert img != null : "image is null";
		
		//System.out.println(img."");
		
		g.drawImage(img, 0, 0, this);
		Tile cur = null;
		if(path != null) {
			g.setColor(new Color(255, 0, 0, 255));
			Iterator<Tile> iter = path.iterator();
			while(iter.hasNext()) {
				cur = iter.next();
				g.drawRect(cur.getX(), cur.getY(), 1, 1);
			}
		}
	}




	public void saveImage() {
		BufferedImage bi = new BufferedImage(3024, 3024, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = bi.createGraphics();
		
		paintComponent(graphic);
		File file = new File("path.png");
		
		try {
			ImageIO.write(bi,  "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}	
}
