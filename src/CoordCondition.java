import java.util.List;


public abstract class CoordCondition {
	
	protected Coord coord, destination;
	public CoordCondition(Coord coord, Coord destination) {
		this.coord = coord;
		this.destination = destination;
	}
	
	public abstract boolean canUse(Script script);
	
	public abstract void handleCondition(Script script);
	
	public abstract boolean overcome(Script script);

	public Coord getDestinationCoord() {
		return destination;
	}
	
	@Override
	public String toString() {
		return "CoordCondition(" + getClass().getName() + "): " + this.coord.toString() + " to " + this.destination.toString();
	}

	public abstract long waitTime();

}
