import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class FindPath {

	public Coord position, destination;
	
	public static ImageMap iMap = null;
	
	public static void clear() {
		iMap = null;
		FindPath.coordDestination = null;
		FindPath.endPosition = null;
		FindPath.finish = null;
		FindPath.finishHighway = null;
		FindPath.highway = null;
		FindPath.start = null;
		FindPath.startHighway = null;
		FindPath.startPosition = null;
		FindPath.reverseConditionalMap = null;
	}
	
	private static Script script = null;
	
	public void setScript(Script script) {
		FindPath.script = script;
	}

	
	private FindPath(Coord position, Coord destination) {
		this.position = position;
		this.destination = destination;
	}
	
	public static void setImageMap(ImageMap iMap) {
		FindPath.iMap = iMap;
	}
	
	private static Coord startPosition, startHighway, finishHighway, endPosition;
	public static void setPositions(Coord startPosition, Coord destinationPosition) {
		FindPath.startPosition = startPosition;
		FindPath.endPosition = destinationPosition;
	}
	
	private static Coord coordDestination;
	
	public static Coord getCoordDestination() {
		return coordDestination;
	}
	
	public static void setDestinationToStart() {
		coordDestination = startPosition;
	}
	
	public static void setDestinationToFinish() {
		coordDestination = endPosition;
	}	
	
	public static void setDestinationToHighwayFinish() {
		coordDestination = finishHighway;
	}	
	
	private static FindPath start, highway, finish;
	public static FindPath getStartInstance() {
		if(start == null) {
			assert FindPath.startPosition != null : "You must set your position before getting the start instance.";
			assert FindPath.endPosition != null : "You must set your destination position before getting the start instance.";
				
			start = new FindPath(FindPath.startPosition, FindPath.endPosition);
		}
		return start;
	}
	
	public static FindPath getFinishInstance() {
		if(finish == null) {
			assert FindPath.startPosition != null : "You must set your position before getting the end instance.";
			assert FindPath.endPosition != null : "You must set your destination position before getting the end instance.";
				
			finish = new FindPath(FindPath.endPosition, FindPath.startPosition);
		}
		return finish;
	}	
	
	public static FindPath getHighwayInstance() {
		if(highway == null) {
			assert FindPath.startHighway != null : "You must set your highway position before getting the highway instance.";
			assert FindPath.finishHighway != null : "You must set your highway destination position before getting the highway instance.";
				
			highway = new FindPath(FindPath.startHighway, FindPath.finishHighway);
		}
		return highway;
	}
	
	//public PriorityQueue<Coord> coordsToCheck = new PriorityQueue<Coord>();
	ArrayList<Coord> coordsToCheck = new ArrayList<Coord>();
	
	List<Coord> closedList = new ArrayList<Coord>();
	List<Coord> possiblePaths = new ArrayList<Coord>();	

	private List<Coord> findPathOrHighway(boolean highway) {
		//if(Player.getInstance().getDestination())
//		if(ImageMap.badTiles.contains(new ImageMap.Tile(destination.getX(),destination.getY()))) {
//			System.out.println("Destination on unwalkable tile...");
//			return new ArrayList<Coord>();
//		}
		
		//System.out.println("Searching starting at " + this.position + ", going to " + this.destination );
		
		if(!canReach(destination.getX(),destination.getY())) {
			//System.out.println("Destination on unwalkable tile...");
			return new ArrayList<Coord>();
		}
		boolean destinationPossible = true;
		Coord curCoord = position; //Player.getInstance().getPosition();//new Coord(Player.getInstance().getX(), Player.getInstance().getY(), null);
		boolean destinationFound = curCoord.equalCoords(destination);		
		coordsToCheck.clear();
		closedList.clear();
		possiblePaths.clear();
		coordsToCheck.add(curCoord);		
		StringBuilder sb = new StringBuilder();
		while(destinationPossible && !destinationFound) {
			//System.out.println(coordsToCheck);
//			if(coordsToCheck.size() % 100 == 0)
//				System.out.println("ctc: " + coordsToCheck.size());
			if(sb.length() > 10) {
				//System.out.println(sb.toString());
				sb = new StringBuilder();
			}

			if(curCoord.equalCoords(destination)) {
				possiblePaths.add(curCoord);
				destinationFound = true;
			} else if(highway && isHighway(curCoord.getX(), curCoord.getY())) {
				FindPath.setHighway(curCoord);
				//System.out.println("Highway found...");
				return buildPath(curCoord);
			}
			else {
				int addedCount = findAdjacentSquares(curCoord, highway);
				//System.out.print(addedCount + ", ");
				sb.append(addedCount + ", ");
				if(highway && addedCount > 0) {
					for(int c = 1; c <= addedCount; c++) {
//						if(coordsToCheck.size() - c < 0)
//							System.out.println("lame");
						Coord addedCoord = coordsToCheck.get(coordsToCheck.size() - c);
						if(iMap.isHighway(addedCoord.getX(), addedCoord.getY())) {
							FindPath.setHighway(addedCoord);
							return buildPath(curCoord);
						}
					}
				}
				Collections.sort(coordsToCheck);
				Iterator<Coord> iter = coordsToCheck.iterator();
				if(iter.hasNext()) {
					Coord other = curCoord;
					curCoord = iter.next();				
					coordsToCheck.remove(other);
					closedList.add(other);				
				} else {
					destinationPossible = false;
				}
			}
			//System.out.println(String.format("cur: %s ctc: %d\n%s", curCoord.toString(), coordsToCheck.size(), closedList.toString()));
		}
		if((!destinationPossible && !destinationFound) || possiblePaths.size() == 0) {
			System.out.println("no possible path found");
			return new ArrayList<Coord>();
		}
		List<Coord> ret = buildPath(possiblePaths.get(0));
		//System.out.println("PATH: " + ret);		
		return ret;
	}	
	
	private static void setHighway(Coord curCoord) {
		if(FindPath.startHighway == null)
			FindPath.startHighway = curCoord;
		else
			FindPath.finishHighway = curCoord;
	}

	public List<Coord> findPathToHighway() {
		return this.findPathOrHighway(true);
	}
	
	public List<Coord> findHighwayPath() {	
		return this.findPathOrHighway(false);
	}
	
	public List<Coord> buildPath(Coord coord) {
		ArrayList<Coord> ret = new ArrayList<Coord>();
		Coord cur = coord;
		while(cur != null) {
			if(ret.isEmpty())
				ret.add(cur);
			else
				ret.add(0, cur);
			//if(cur.isCondition())
			//	System.out.println(StaticValues.coordConditions.get(cur));
			cur = cur.getParent();
		}
		return ret;
	}
	
	public Collection<Coord> getCoordsToCheck() {
		return coordsToCheck;
	}
	
	public int findAdjacentSquares(Coord coord, boolean highway) {
		int prev = coordsToCheck.size();
		//4 corners		
		if(highway) {
			//    
			//  o 
			//   x
			if(canReach(coord.getX() + 1, coord.getY() + 1)) {
				addToCoordsToCheck(new Coord(coord.getX() + 1, coord.getY() + 1, coord));
			}				
	
			//   x
			//  o 
			//    
			if(canReach(coord.getX() + 1, coord.getY() - 1)) {
				addToCoordsToCheck(new Coord(coord.getX() + 1, coord.getY() - 1, coord));					
			}		
					
			// x  
			//  o 
			//    		
			if(canReach(coord.getX() - 1,coord.getY() - 1)) {
				addToCoordsToCheck(new Coord(coord.getX() - 1,coord.getY() - 1, coord));		
			}		
					
			//    
			//  o 
			// x  		
			if(canReach(coord.getX() - 1, coord.getY() + 1)) {
				addToCoordsToCheck(new Coord(coord.getX() - 1, coord.getY() + 1, coord));			
			}		
			
			
			//start straight line ones
			//    
			//  o 
			//  x 
			if(canReach(coord.getX(), coord.getY() + 1)) {
				addToCoordsToCheck(new Coord(coord.getX(), coord.getY() + 1, coord));			
			}				
	
			//    
			//  ox
			//    
			if(canReach(coord.getX() + 1, coord.getY())) {
				addToCoordsToCheck(new Coord(coord.getX() + 1, coord.getY(), coord));				
			}		
					
			//  x 
			//  o 
			//    		
			if(canReach(coord.getX(),coord.getY() - 1)) {
				addToCoordsToCheck(new Coord(coord.getX(),coord.getY() - 1, coord));			
			}		
					
			//    
			// xo 
			//    		
			if(canReach(coord.getX() - 1, coord.getY())) {
				addToCoordsToCheck(new Coord(coord.getX() - 1, coord.getY(), coord));			
			}
		} else {
//    
			//  o 
			//   x
			if(isHighway(coord.getX() + 1, coord.getY() + 1)) {
				addToCoordsToCheck(new Coord(coord.getX() + 1, coord.getY() + 1, coord));
			}				
	
			//   x
			//  o 
			//    
			if(isHighway(coord.getX() + 1, coord.getY() - 1)) {
				addToCoordsToCheck(new Coord(coord.getX() + 1, coord.getY() - 1, coord));					
			}		
					
			// x  
			//  o 
			//    		
			if(isHighway(coord.getX() - 1,coord.getY() - 1)) {
				addToCoordsToCheck(new Coord(coord.getX() - 1,coord.getY() - 1, coord));		
			}		
					
			//    
			//  o 
			// x  		
			if(isHighway(coord.getX() - 1, coord.getY() + 1)) {
				addToCoordsToCheck(new Coord(coord.getX() - 1, coord.getY() + 1, coord));			
			}		
			
			
			//start straight line ones
			//    
			//  o 
			//  x 
			if(isHighway(coord.getX(), coord.getY() + 1)) {
				addToCoordsToCheck(new Coord(coord.getX(), coord.getY() + 1, coord));			
			}				
	
			//    
			//  ox
			//    
			if(isHighway(coord.getX() + 1, coord.getY())) {
				addToCoordsToCheck(new Coord(coord.getX() + 1, coord.getY(), coord));				
			}		
					
			//  x 
			//  o 
			//    		
			if(isHighway(coord.getX(),coord.getY() - 1)) {
				addToCoordsToCheck(new Coord(coord.getX(),coord.getY() - 1, coord));			
			}		
					
			//    
			// xo 
			//    		
			if(isHighway(coord.getX() - 1, coord.getY())) {
				addToCoordsToCheck(new Coord(coord.getX() - 1, coord.getY(), coord));			
			}
		}
		
		//if(StaticValues.coordConditions.containsKey(key))
		if(coord.x == 1018 && coord.y == 1665)
			System.out.println(coordsToCheck);
		
		CoordCondition condition = StaticValues.coordConditions.get(new Tile(coord.getX(), coord.getY()));
		if(condition != null) {
			//coord condition exists
			//System.out.print("Coord condition found! ");			
			if(condition.canUse(FindPath.script)) {
				Coord conditionCoord = new Coord(condition.getDestinationCoord().getX(), condition.getDestinationCoord().getY(), coord, true);
				//conditionCoord.setAsCondition();
				//coord.setAsCondition();//set the start coord as a conditional coord
				//conditionCoord.setAsCondition();
				//System.out.println("adding " + conditionCoord + " to ctc...");
				addToCoordsToCheck(conditionCoord);//add the conditional coord destination as an adjacent coord
				if(reverseConditionalMap == null)
					reverseConditionalMap = new HashMap<Coord, Coord>();
				reverseConditionalMap.put(conditionCoord, coord);
				//conditionalCoordMap.put(conditionCoord, coord);
//				if(conditionalCoordMap.containsKey(conditionCoord)) {
//					Error with coord
//				}
				//System.out.println(coordsToCheck);
			}
		}
		
		if(this == this.getFinishInstance()) {
			if(reverseConditionalMap != null && reverseConditionalMap.containsKey(coord)) {
				Coord destination = reverseConditionalMap.get(coord);
				addToCoordsToCheck(destination);
			}
		}
		
		return coordsToCheck.size() - prev;
		
	}
	
	private static HashMap<Coord, Coord> reverseConditionalMap = null;
	//private BiMap<Coord, Coord> conditionalCoordMap = HashBiMap.create();
	
	private boolean isHighway(int x, int y) {
		if(x == 1018 && y == 1665) {
			//System.out.println();
			boolean conn = StaticValues.coordConditions.containsKey(new Tile(x, y));
			conn = true;
			//System.out.println(conn + " found...");
		}
		return iMap.isHighway(x, y) 
				|| StaticValues.coordConditions.containsKey(new Tile(x, y));
	}

	public boolean canReach(int x, int y) {
		if(x < 0 || y < 0 || y >= iMap.height || x >= iMap.width)
			return false;
		//return map[y][x] == 1;
		//return !ImageMap.badTiles.contains(new ImageMap.Tile(x, y));
		return iMap.canReach(x,  y);
	}	
	
	private void addToCoordsToCheck(Coord coord) {
		//if(coord.x == 1019 && coord.y == 1665)
		//	System.out.println(coordsToCheck);		
		if(!coord.listContainsCoords(closedList, coord)) {
		
			int index = coordsToCheck.indexOf(coord);
			if(index != -1) {
				Coord curCoord = coordsToCheck.get(index);
				int comp = coord.compareTo(curCoord);
				if(comp == -1) {
					this.coordsToCheck.add(coord);
					this.coordsToCheck.remove(index);
				}
			} else 
				this.coordsToCheck.add(coord);
		}
	}
}
