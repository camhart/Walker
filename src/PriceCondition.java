import java.util.List;



public abstract class PriceCondition extends CoordCondition {
	
	private static int gpUsed = 0;
	int priceCost = 0;
	
	public PriceCondition(Coord coord, Coord destination, int priceCost) {
		super(coord, destination);
		this.priceCost = priceCost;
	}
	
	public void applyCost() {
		gpUsed -= priceCost;
	}
	
	public static int getGPUsed() {
		return gpUsed;
	}	
}
