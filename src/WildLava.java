

import java.awt.Color;
import java.util.Collection;

public class WildLava extends MapCondition {

	public WildLava() {
		super(new Tile(2116,1276), new Tile(2878, 288));
		lava = new Color(112, 48, 2).getRGB();
	}
	
	int lava = -1;

	@Override
	public void handleCondition(Collection<Tile> badTileList, int rgbColor,
			Tile coord) {
		if(inApplicableArea(coord)) {
			if(rgbColor == lava) {
				badTileList.add(coord);
			}
		}
	}

	@Override
	public boolean isWalkable(Tile tile, int color) {
		if(this.inApplicableArea(tile) && color == lava) {
			return false;
		}
		return true;
	}

}
