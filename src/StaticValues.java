

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;

public class StaticValues {
	static int NOT_WALKABLE = -1;
	static int WALKABLE = -1;
	static int HIGHWAY = -1;
	static int ROAD = -1;
	static int GENERATED_NOT_WALKABLE = -1;
	static int WAYPOINT = -1;
	static int COORD_CONDITION = -1;
	static {
		NOT_WALKABLE = new Color(0, 0, 0).getRGB();
		GENERATED_NOT_WALKABLE = new Color(139, 0, 204, 255).getRGB();
		WALKABLE = new Color(0, 128, 64, 255).getRGB();
		HIGHWAY = new Color(255, 255, 0).getRGB();
		ROAD = new Color(255, 0, 255).getRGB();
		COORD_CONDITION = new Color(255, 36, 0).getRGB();
		WAYPOINT = new Color(16,78,139).getRGB();
	}
	
	static HashSet<Integer> badColors = null;
	static HashSet<Integer> goodColors = null;
	static HashSet<Tile> badTiles = null;
	static {
		badColors = new HashSet<Integer>();
		badColors.add(new Color(32, 64, 126).getRGB()); // water
		//badColors.add(new Color(36, 72, 0).getRGB()); // fence?  where did i get this color from?
		badColors.add(new Color(96, 96, 96).getRGB()); // wall
		badColors.add(new Color(100, 100, 100).getRGB()); // rock
		badColors.add(new Color(0, 0, 0).getRGB()); // blackness

		goodColors = new HashSet<Integer>();
		goodColors.add(HIGHWAY);
		goodColors.add(ROAD);
		goodColors.add(COORD_CONDITION);
		goodColors.add(WAYPOINT);
	}
	
	static HashMap<Tile, CoordCondition> coordConditions = null;
	static {
		coordConditions = new HashMap<Tile, CoordCondition>();
		//Fally Handholds
		//coordConditions.put(new Coord(339, 555), new HandholdCondition(new Coord(339, 555), new Coord(338, 555)));
		coordConditions.put(new Tile(1018, 1665), new HandholdCondition(new Coord(1018, 1665), new Coord(1016, 1665)));		
	}
}
