
import java.util.List;
import java.util.Set;
import java.util.TreeSet;



public class Coord implements Comparable {

	int x, y;
	
//	static final int COST = 10;
//	static final int DIAGONAL_COST = 14;
	
	double totalDist = 1;

	Coord parent;
	
	public Coord(int x, int y, Coord parent, boolean condition) {
		this.x = x;
		this.y = y;
		this.parent = parent;
		if(this.parent != null) {
			if(condition)
				totalDist = parent.totalDist + 1; //distanceTo(this, parent);				
			else 
				totalDist = parent.totalDist + distanceTo(this, parent);
		}
	}	
	
	public Coord(int x, int y, Coord parent) {
		this.x = x;
		this.y = y;
		this.parent = parent;
		if(this.parent != null) {
			totalDist = parent.totalDist + distanceTo(this, parent);
		}
	}
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Coord getParent() {
		return parent;
	}

	public String toString() {
		return "[" +  x + ", " + y + " (" + totalDist + ")]";
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int calculateCompareTo(Coord other) {
		if(this.pathCost() < other.pathCost()) {
			return -1;
		} else if(this.pathCost() > other.pathCost()) {
			return 1;
		} else {
			//System.out.print("0");
			return 0;
		}
	}

	@Override
	public int compareTo(Object obj) {
		if(obj == null || obj.getClass() != this.getClass())
			return -1;
		Coord other = (Coord)obj;
		return calculateCompareTo(other);
	}
	
	@Override
	public boolean equals(Object obj) {
		//System.out.print("e");
		if(obj == null || obj.getClass() != this.getClass())
			return false;
		Coord other = (Coord)obj;
		return other.x == x && other.y == y ;// && other.totalDist == totalDist;		
	}
	
	public boolean equalCoords(Coord other) {
		if(other == null)
			return false;
		return other.x == x && other.y == y;	
	}

	public static boolean listContainsCoords(List<Coord> list, Coord coord) {
		for(int c = 0; c < list.size(); c++) {
			if(list.get(c).equalCoords(coord)) {
				return true;				
			}
				
		}
		return false;
	}
	
	public static double manhattanDistance(Coord from, Coord to) {
		return manhattanDistance(from.x, from.y, to.x, to.y);
	}	
	
	public static double manhattanDistance(int x, int y, int x2, int y2) {
		return Math.abs(x - x2) + Math.abs(y - y2);
	}	
	
	public static double distanceFormula(Coord from, Coord to) {
		return distanceFormula(from.x, from.y, to.x, to.y);
	}	
	
	public static double distanceFormula(int x, int y, int x2, int y2) {
		return Math.sqrt(Math.pow(x - x2,  2) + Math.pow(y - y2,  2));
	}
	
	public static double euclideanDistance(Coord from, Coord to) {
		return euclideanDistance(from.x, from.y, to.x, to.y);
	}
	
	public static double euclideanDistance(int x, int y, int x2, int y2) {
		int xDist = Math.abs(x - x2);
		int yDist = Math.abs(y - y2);
		int min = Math.min(xDist, yDist);
		
		int max = Math.max(xDist,  yDist);
		
		return max - min + Math.sqrt(2 * Math.pow(min,  2));
		
	}
	//
	
	public static double distanceTo(Coord from, Coord to) {
		//return distanceFormula(from, to);
		return euclideanDistance(from, to);
		//e - 59 sec
		//d - 1 min 6 seconds
	}
//
//	public static boolean isDiagonalWith(Coord a, Coord b) {
//		if(Math.abs(b.x - a.x) + Math.abs(b.y - a.y) == 2)
//			return true;
//		return false;
//	}
	
	public double pathCostToSpot(Coord coordPos) {
		return coordPos.totalDist;	
	}
	
	public static double pathCostFromSpot(Coord coordPos) {
		return distanceTo(FindPath.getCoordDestination(), coordPos);
	}
	
	public double pathCost() {	
		return pathCostToSpot(this) + pathCostFromSpot(this);				
	}

	private boolean isCondition = false;
	public void setAsCondition() {
		isCondition = true;
	}
	
	public boolean isCondition() {
		return isCondition;
	}
	
}
