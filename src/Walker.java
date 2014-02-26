

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Walker {

	private static ImageMap iMap;
	public static List<Coord> generatePath(Coord start, Coord finish) {
		try {
			iMap = new ImageMap("transformed_map");
		} catch (ClassNotFoundException e) {
			System.out.println("Missing map data file 'transformed_map.png'.");
			return null;
		} catch (IOException e) {
			System.out.println("Missing map data file 'transformed_map.png'.");
			return null;
		}
		List<Coord> completePath = new ArrayList<Coord>();
		long startTime = System.currentTimeMillis();
		
		FindPath.clear();
		FindPath.setImageMap(iMap);
		FindPath.setPositions(start , finish);		
	
		System.out.print("Searching for highway... ");
		FindPath startFP = FindPath.getStartInstance();
		FindPath.setDestinationToFinish();		
		List<Coord> pathToHighway = startFP.findPathToHighway();
//		Scanner s = new Scanner(System.in);
//		System.out.println("Click enter: ");
//		System.out.println(s.nextLine());
		if(!pathToHighway.isEmpty())		
			System.out.println("found! " + pathToHighway.get(0) + " - " + pathToHighway.get(pathToHighway.size() - 1));
		else System.out.println("not found.");
		
		System.out.print("Searching for destination from highway...");
		FindPath finishFP = FindPath.getFinishInstance();
		FindPath.setDestinationToStart();
		List<Coord> pathFromHighway = finishFP.findPathToHighway();
		
//		System.out.println("Click enter: ");
//		System.out.println(s.nextLine());		
		//Collections.reverse(pathFromHighway);		
		if(!pathFromHighway.isEmpty())
			System.out.println("found! " + pathFromHighway.get(0) + " - " + pathFromHighway.get(pathFromHighway.size() - 1));
		else System.out.println("not found.");
		
		
		System.out.print("Searching for highway path... ");
		FindPath highwayFP = FindPath.getHighwayInstance();
		FindPath.setDestinationToHighwayFinish();
		List<Coord> highwayPath = highwayFP.findHighwayPath();
		if(!highwayPath.isEmpty())
			System.out.println("found! " + highwayPath.get(0) + " - " + highwayPath.get(highwayPath.size() - 1));
		else System.out.println("not found.");
//		Collections.reverse(pathToHighway);
//		Collections.reverse(highwayPath);
//		Collections.reverse(pathFromHighway);
		completePath.addAll(pathToHighway);
		completePath.addAll(highwayPath);
		completePath.addAll(pathFromHighway);
		
		return completePath;
	}
	
//	public static boolean isConditionCoord(int x, int y) {
//		return iMap.getColorAtTile(new Tile(x * 3, y * 3)) == StaticValues.COORD_CONDITION;
//	}	

	public static int[] findConditionCoord(int x, int y) {
		for(int c = 0; c < 2; c++) {
			for(int d = 0; d < 2; d++) {
				//if(iMap.getColorAtTile(new Tile(x * 3 - c, y * 3 - d)) == StaticValues.COORD_CONDITION)
				if(x * 3 - c >= 0 && x * 3 - c < iMap.width && y * 3 - d >= 0 && y * 3 - d < iMap.height)
					if(iMap.getColorAtTile(new Tile(x * 3 - c, y * 3 - d)) == StaticValues.COORD_CONDITION)					
						return new int[]{x * 3 - c, y * 3 - d};
			}
		}
		return new int[0];
//		return iMap.getColorAtTile(new Tile(x * 3, y * 3)) == StaticValues.COORD_CONDITION
//				|| iMap.getColorAtTile(new Tile(x * 3, y * 3)) == StaticValues.COORD_CONDITION
//				|| iMap.getColorAtTile(new Tile(x * 3, y * 3)) == StaticValues.COORD_CONDITION;
	}
}
