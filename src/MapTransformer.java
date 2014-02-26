




import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Formatter;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.File;
import java.util.Iterator;
import javax.imageio.*;
import javax.imageio.stream.*;

import javax.imageio.ImageIO;

public class MapTransformer {

	// look for fences...
	BufferedImage mapImage = null;
	int width, height;
	String imagePath = null;

	public MapTransformer(String imagePath) {
		this.imagePath = imagePath;
	}
	
	private static Logger logger = null;
	static {
		logger = Logger.getLogger(MapTransformer.class.getName());		
		logger.setUseParentHandlers(false);
		FileHandler fh = null;
		try {
			fh = new FileHandler(logger.getName() + ".txt", 26214400 * 20, 1); //400mb
			fh.setLevel(Level.FINEST);			
			//SimpleFormatter sf = new SimpleFormatter();
			fh.setFormatter(new SimpleFormatter() {
				
			});
			logger.log(Level.INFO, (new Date()).toString());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(fh != null) {
			System.out.println("Handler added");
			logger.addHandler(fh);
		}
	}
	

	public void generate() throws IOException {
		mapImage = ImageIO.read(new File(imagePath + ".png"));

		int width = mapImage.getWidth();
		int height = mapImage.getHeight();
		System.out.println(String.format("MapDimensions: %d x %d", width, height));
		int curColor = -1;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				curColor = mapImage.getRGB(x, y);
				if(StaticValues.goodColors.contains(curColor)) {
//					//return;
					continue;
				}
				if(x == 381 && y == 1989) {
					Color color = new Color(curColor);
					System.out.println("asdf "+color);
				}
				
				for(int c = 0; c < ImageMap.conditions.size(); c++) {
					if(!ImageMap.conditions.get(c).isWalkable(new Tile(x,  y), curColor)) {
						mapImage.setRGB(x,  y,  StaticValues.NOT_WALKABLE);
					}
				}
				
				if (StaticValues.badColors.contains(curColor)) {
					// badTiles.add(new Tile(x, y));
					
					mapImage.setRGB(x, y, StaticValues.NOT_WALKABLE);
					int tl = -1, bl = -1, tr = -1, br = -1;
					//if (x > 0 && y > 0)
						tl = getMapImageRGB(x - 1, y - 1);
					//if (x > 0 && y < height - 1)
						bl = getMapImageRGB(x - 1, y + 1);
					//if (x < width - 1 && y > 0)
						tr = getMapImageRGB(x + 1, y - 1);
					//if (x < width - 1 && y < height - 1)
						br = getMapImageRGB(x + 1, y + 1);

					if (StaticValues.badColors.contains(tl)) {
						// badTiles.add(new Tile(x - 1, y));
						// badTiles.add(new Tile(x, y - 1));
						if(x == 1000 && y == 1000)
							logger.log(Level.INFO, String.format("%d, %d set to not walkable due to tl.\n", x - 1, y));
						//if(!goodColors.contains(getMapImageRGB(x - 1,  y)))
						if(!StaticValues.badColors.contains(getMapImageRGB(x - 1,  y)) && StaticValues.NOT_WALKABLE != getMapImageRGB(x - 1,  y))
							mapImage.setRGB(x - 1, y, StaticValues.GENERATED_NOT_WALKABLE);
						if(x == 1000 && y == 1000)						
							logger.log(Level.INFO, String.format("%d, %d set to not walkable due to tl.\n", x, y - 1));
						//if(!goodColors.contains(getMapImageRGB(x,  y - 1)))
						if(!StaticValues.badColors.contains(getMapImageRGB(x,  y - 1)) && StaticValues.NOT_WALKABLE != getMapImageRGB(x,  y - 1))						
							mapImage.setRGB(x, y - 1, StaticValues.GENERATED_NOT_WALKABLE);
					}
					if (StaticValues.badColors.contains(br)) {
						// badTiles.add(new Tile(x + 1, y));
						// badTiles.add(new Tile(x, y + 1));
						if(x == 1000 && y == 1000)						
							logger.log(Level.INFO, String.format("%d, %d set to not walkable due to br.\n", x + 1, y));
						//if(!goodColors.contains(getMapImageRGB(x - 1,  y)))
						if(!StaticValues.badColors.contains(getMapImageRGB(x + 1,  y)) && StaticValues.NOT_WALKABLE != getMapImageRGB(x + 1,  y))						
							mapImage.setRGB(x + 1, y, StaticValues.GENERATED_NOT_WALKABLE);
						if(x == 1000 && y == 1000)						
							logger.log(Level.INFO, String.format("%d, %d set to not walkable due to br.\n", x, y + 1));
						//if(!goodColors.contains(getMapImageRGB(x - 1,  y)))
						if(!StaticValues.badColors.contains(getMapImageRGB(x,  y + 1)) && StaticValues.NOT_WALKABLE != getMapImageRGB(x,  y + 1))						
							mapImage.setRGB(x, y + 1, StaticValues.GENERATED_NOT_WALKABLE);
					}
					if (StaticValues.badColors.contains(tr)) {
						// badTiles.add(new Tile(x, y - 1));
						// badTiles.add(new Tile(x + 1, y));
						//if(!goodColors.contains(getMapImageRGB(x - 1,  y)))
						if(!StaticValues.badColors.contains(getMapImageRGB(x + 1,  y)) && StaticValues.NOT_WALKABLE != getMapImageRGB(x + 1,  y))						
							mapImage.setRGB(x + 1, y, StaticValues.GENERATED_NOT_WALKABLE);
						if(x == 1000 && y == 1000)						
							logger.log(Level.INFO, String.format("%d, %d set to not walkable due to tr.\n", x + 1, y));
						//if(!goodColors.contains(getMapImageRGB(x - 1,  y)))	
						if(!StaticValues.badColors.contains(getMapImageRGB(x,  y - 1)) && StaticValues.NOT_WALKABLE != getMapImageRGB(x,  y - 1))						
							mapImage.setRGB(x, y - 1, StaticValues.GENERATED_NOT_WALKABLE);
						if(x == 1000 && y == 1000)						
							logger.log(Level.INFO, String.format("%d, %d set to not walkable due to tr.\n", x, y - 1));						
					}
					if (StaticValues.badColors.contains(bl)) {
						// badTiles.add(new Tile(x - 1, y));
						// badTiles.add(new Tile(x, y + 1));
						if(x == 1000 && y == 1000)						
							logger.log(Level.INFO, String.format("%d, %d set to not walkable due to bl.\n", x - 1, y));
						//if(!goodColors.contains(getMapImageRGB(x - 1,  y)))	
						if(!StaticValues.badColors.contains(getMapImageRGB(x - 1,  y)) && StaticValues.NOT_WALKABLE != getMapImageRGB(x - 1,  y))						
							mapImage.setRGB(x - 1, y, StaticValues.GENERATED_NOT_WALKABLE);
						if(x == 1000 && y == 1000)						
							logger.log(Level.INFO, String.format("%d, %d set to not walkable due to bl.\n", x, y + 1));
						//if(!goodColors.contains(getMapImageRGB(x - 1,  y)))		
						if(!StaticValues.badColors.contains(getMapImageRGB(x,  y + 1)) && StaticValues.NOT_WALKABLE != getMapImageRGB(x,  y + 1))						
							mapImage.setRGB(x, y + 1, StaticValues.GENERATED_NOT_WALKABLE);
					}
				} else if(curColor == StaticValues.GENERATED_NOT_WALKABLE) { 
					mapImage.setRGB(x,  y,  StaticValues.GENERATED_NOT_WALKABLE);
				} else {
					//check for highway
					//if(y > 1000) 
					//	System.out.println("hi");
					if(curColor == StaticValues.HIGHWAY || curColor == StaticValues.ROAD)
						mapImage.setRGB(x,  y,  StaticValues.HIGHWAY);
					else 
						mapImage.setRGB(x, y, StaticValues.WALKABLE);
				}
				if(x == 1740 && y == 1846) {
					System.out.println(curColor);
					System.out.println(new Color(curColor));
					System.out.println(new Color(curColor).getAlpha());
					
//					System.out.println(new Color(mapImage.getRGB(x + 1,  y + 1)));
//					System.out.println(new Color(mapImage.getRGB(x + 1,  y + 1)).getAlpha());
//					
//					System.out.println(new Color(mapImage.getRGB(x + 1,  y - 1)));
//					System.out.println(new Color(mapImage.getRGB(x + 1,  y - 1)).getAlpha());
//					
//					System.out.println(new Color(mapImage.getRGB(x - 1,  y + 1)));
//					System.out.println(new Color(mapImage.getRGB(x - 1 ,  y + 1)).getAlpha());
//					
//					System.out.println(new Color(mapImage.getRGB(x,  y - 1)));
//					System.out.println(new Color(mapImage.getRGB(x,  y - 1)).getAlpha());
//					
//					System.out.println(new Color(mapImage.getRGB(x,  y + 1)));
//					System.out.println(new Color(mapImage.getRGB(x,  y + 1)).getAlpha());
//					
//					System.out.println(new Color(mapImage.getRGB(x + 1,  y)));
//					System.out.println(new Color(mapImage.getRGB(x + 1,  y)).getAlpha());
//					
//					System.out.println(new Color(mapImage.getRGB(x - 1,  y)));
//					System.out.println(new Color(mapImage.getRGB(x - 1 ,  y)).getAlpha());
//										
				}
//				if(x == 1561 && y == 1711) {
//					System.out.println(curColor);
//					System.out.println(new Color(curColor));
//					System.out.println(new Color(curColor).getAlpha());
//					
//					System.out.println(new Color(mapImage.getRGB(x,  y + 1)));
//					System.out.println(new Color(mapImage.getRGB(x,  y + 1)).getAlpha());					
//				}
			}
			//System.out.println(y);			
		}
	}
	
	public int getMapImageRGB(int x, int y) {
		try {
			return this.mapImage.getRGB(x,  y);
		} catch(ArrayIndexOutOfBoundsException e) {
			return -1;
		}
	}
	
	public void save(String outFilePath) throws IOException {
		File file = new File(outFilePath);		
		
//		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("bmp");
//		ImageWriter writer = iter.next();
//				
//		ImageOutputStream ios = ImageIO.createImageOutputStream(file);
//		writer.setOutput(ios);
//			
//		ImageWriteParam iwp = writer.getDefaultWriteParam();
//		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//		iwp.setCompressionQuality(1.0f);
//		
//		IIOImage image = new IIOImage(mapImage, null, null);
//		
//		writer.write(null, image, iwp);
//		writer.dispose();
//		
		ImageIO.write(mapImage, "png", file);
	}
	
	public static void main(String[] args) throws IOException {
		MapTransformer mt = new MapTransformer("map");
		mt.generate();
		mt.save("transformed_map.png");
		System.out.println("done.");
	}
}
