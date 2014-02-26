import java.util.List;

public class HandholdCondition extends CoordCondition {

	public HandholdCondition(Coord coord, Coord destination) {
		super(coord, destination);
	}

	@Override
	public boolean canUse(Script script) {
		if(script == null)
			return true;
		return script.getCurrentLevel(16) >= 5;
	}

	@Override
	public void handleCondition(Script script) {
		System.out.println("Using handholds...");
		script.atObject(coord.getX(), coord.getY());
	}

	@Override
	public boolean overcome(Script script) {
		return script.isReachable(destination.getX(), destination.getY());
	}

	@Override
	public long waitTime() {
		return 1750;
	}

	
}
