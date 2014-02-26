


import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class MapPanel extends JPanel {
	
	ImageMapComponent imc = null;
	
	public MapPanel(String imagePath) {
		super();
		this.setLayout(new BorderLayout());
		try {
			imc = new ImageMapComponent(imagePath + ".png");

			this.add(imc);
//			JScrollPane scroll = new JScrollPane();
//			scroll.add(imc);
//			
//			this.add(scroll);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void setPath(Collection<Coord> findPath) {
		List<Tile> tiles = new ArrayList<Tile>();
		//for(int c = 0; c < findPath; c++)
		Iterator<Coord> iter = findPath.iterator();
		while(iter.hasNext()) {
			Coord cur = iter.next();
			tiles.add(new Tile(cur.getX(),  cur.getY()));
		}
		imc.setPath(tiles);
	}
	
	public void saveImage() {
		imc.saveImage();
	}
	
	
}
