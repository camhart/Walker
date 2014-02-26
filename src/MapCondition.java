


import java.util.Collection;


public abstract class MapCondition {
	
	private Tile lowerLeft, upperRight;
	public MapCondition(Tile lowerLeft, Tile upperRight) {
		this.lowerLeft = lowerLeft;
		this.upperRight = upperRight;
	}
	
	public boolean inApplicableArea(Tile tile) {
		if(tile == null)
			return false;
		return tile.getX() <= upperRight.getX() &&
				tile.getX() >= lowerLeft.getX() &&
				tile.getY() <= upperRight.getY() &&
				tile.getY() >= lowerLeft.getY();
	}
	
	public abstract void handleCondition(Collection<Tile> badTileList, int rgbColor, Tile coord);
	//2350, 1257 on map == 224, 452
	//2016
	//432 is the top near gnome stronghold == 1296, 864 off
	//766 is the west edge near gnome stronghold == 720

	public abstract boolean isWalkable(Tile tile, int color);
}
