


import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import javax.imageio.ImageIO;

public class ImageMap {
	
	static ArrayList<MapCondition> conditions;
	
	static {
		conditions = new ArrayList<MapCondition>();
		conditions.add(new WildLava());
	}	

	static HashSet<Integer> badColors = null;
	static HashSet<Tile> badTiles = null;
	static {
		badColors = new HashSet<Integer>();
		badColors.addAll(StaticValues.badColors);
		badColors.add(StaticValues.GENERATED_NOT_WALKABLE);
//		badColors.add(new Color(32, 64, 126).getRGB()); // water
//		badColors.add(new Color(36, 72, 0).getRGB()); // fence
//		badColors.add(new Color(96, 96, 96).getRGB()); // wall
//		badColors.add(new Color(100, 100, 100).getRGB()); // rock
//		//badColors.add(new Color(112, 48, 2).getRGB()); // lava
//		badColors.add(new Color(0, 0, 0).getRGB()); // blackness
		//badColors.add(new Color(1, 1, 1).getRGB());

	}

	public int width, height;
	BufferedImage mapImage = null;
	
	public ImageMap(String imagePath) throws IOException, ClassNotFoundException {
		File dataFile = new File(imagePath + ".data");
		mapImage = ImageIO.read(new File(imagePath + ".png"));
		this.width = mapImage.getWidth();
		this.height = mapImage.getHeight();
//		if (!dataFile.exists()) {
//			badTiles = new HashSet<Tile>();
//			int width = mapImage.getWidth();
//			int height = mapImage.getHeight();
//			int curColor = -1;
//			for (int y = 0; y < height; y++) {
//				for (int x = 0; x < width; x++) {
//					curColor = mapImage.getRGB(x, y);
//					if (badColors.contains(curColor)) {
//						badTiles.add(new Tile(x, y));
//						int tl = -1, bl = -1, tr = -1, br = -1;						
//						if(x > 0 && y > 0)
//							tl = mapImage.getRGB(x - 1, y - 1);
//						if(x > 0 && y < height - 1)
//							bl = mapImage.getRGB(x - 1, y + 1);
//						if(x < width - 1 && y > 0)
//							tr = mapImage.getRGB(x + 1, y - 1);
//						if(x < width - 1 && y < height - 1) {
//							br = mapImage.getRGB(x + 1, y + 1);
//						}
//						
//						if(badColors.contains(tl)) {
//							badTiles.add(new Tile(x - 1, y));
//							badTiles.add(new Tile(x, y - 1));
//						}
//						if(badColors.contains(br)) {
//							badTiles.add(new Tile(x + 1, y));
//							badTiles.add(new Tile(x, y + 1));
//						}
//						if(badColors.contains(tr)) {
//							badTiles.add(new Tile(x, y - 1));
//							badTiles.add(new Tile(x + 1, y));
//						}		
//						if(badColors.contains(bl)) {
//							badTiles.add(new Tile(x - 1, y));
//							badTiles.add(new Tile(x, y + 1));
//						}													
//					}
//				}
//			}
//			
//			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile));
//			oos.writeObject(badTiles);
//			oos.close();
//		} else {
//			System.out.print("Loading map...");
//			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile));
//			badTiles = (HashSet<Tile>)ois.readObject();
//			ois.close();
//			System.out.println(" done!");
//		}
	}

//	public static class Tile implements Serializable {
//		Tile data = new Tile();
//
//		public Tile(int x, int y) {
//			this.data.setX(x);
//			this.data.setY(y);
//		}
//
//		@Override
//		public int hashCode() {
//			return new String(this.data.getX() + ", " + this.data.getY()).hashCode();
//		}
//
//		@Override
//		public boolean equals(Object obj) {
//			if (obj == null || obj.getClass() != Tile.class) {
//				return false;
//			}
//			return hashCode() == obj.hashCode();
//		}
//
//		public int getX() {
//			return data.getX();
//		}
//
//		public int getY() {
//			return data.getY();
//		}
//	}

	public int getColorAtTile(Tile tile) {
		return mapImage.getRGB(tile.getX(),  tile.getY());
	}
	
	public boolean canReach(int x, int y) {
		if(x < 0 || y < 0 || y >= height || x >= width)
			return false;
		Tile tile = new Tile(x, y);	
		int rgb = getColorAtTile(tile);
//		for(int c = 0; c < conditions.size(); c++) {
//			//if(conditions.get(c).inApplicableArea(tile)) {
//				if(!conditions.get(c).isWalkable(tile, rgb)) {
//					return false;
//				}
//			//}
//		}
		
//		int topLeftRGB = getColorAtTile(new Tile(tile.x - 1, tile.y - 1));
//		int bottomRightRGB = getColorAtTile(new Tile(tile.x + 1, tile.y + 1));
//		int topRightRGB = getColorAtTile(new Tile(tile.x + 1, tile.y - 1));
//		int bottomLeftRGB = getColorAtTile(new Tile(tile.x - 1, tile.y + 1));
		//xox
		//o+o
		//xox
		
		//oox
		//oxo
		//xoo
		
		if(ImageMap.badColors.contains(rgb))
			return false;
		return true;
		//if(ImageMap.badColors)
//		return true;
		
//		return ImageMap.badColors.contains(topLeftRGB) && ImageMap.badColors.contains(rgb) 
		
		//return !mimicMap.badTiles.contains(new ImageMap.Tile(x, y));
		//return mimicMap[y][x] == 1;
	}

	public boolean isHighway(int x, int y) {
		int tileColor = this.getColorAtTile(new Tile(x, y));
		return StaticValues.HIGHWAY == tileColor || tileColor == StaticValues.ROAD;
	}
}
