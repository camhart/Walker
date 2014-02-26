

import java.util.HashSet;


public class P2PGateHandler extends ObjectHandler {

	public static HashSet<Integer> validObjectIds = null;
	static {
		validObjectIds = new HashSet<Integer>();
		validObjectIds.add(626); //gnome stronghold gate
		validObjectIds.add(137); //falador taverly p2p gate
		validObjectIds.add(346); //wild members gate (ice giants)
		validObjectIds.add(138);
		
	}
	
	boolean wallObject = false;
	
	int[] xDest;
	int[] yDest;
	public P2PGateHandler(int id, int x, int y, Script script, int[] destX, int[] destY) {
		super(id, x, y, script);
		this.xDest = destX;
		this.yDest = destY;
		if(!validObjectIds.contains(id))
			wallObject = true;
	}
	
	//@Override
	public static boolean qualifies(int id, int x, int y) {
		return validObjectIds.contains(id);
	}

	@Override
	public boolean overcome() {
		if((oldX != script.getX() || oldY != script.getY() && script.distanceTo(script.getX(), script.getY(), xDest[0], yDest[0]) <= script.distanceTo(oldX, oldY, xDest[0], yDest[0])))
				return true;
		//determine if the objects coords are between me and destination coord
		for(int c = 0; c < xDest.length; c++) {
			if(inBetweenCoord(script.getX(), script.getY(), objX, objY, xDest[c], yDest[c]))
				return false;
		}
		return true;
	}
	
	/**
	 * Determines if objX, objY is in between meX, meY and destX, destY
	 * @param meX
	 * @param meY
	 * @param objX
	 * @param objY
	 * @param destX
	 * @param destY
	 * @return
	 */
	private boolean inBetweenCoord(int meX, int meY, int objX, int objY, int destX, int destY) {
		int minX = Math.min(meX, destX);
		int minY = Math.min(meY,  objY);
		int maxX = Math.max(meX, destX);
		int maxY = Math.max(meY,  objY);		
		return minX < objX && minY < objY && maxX > objX && maxY > objY;
	}

	int oldX, oldY;
	@Override
	public int handle() {
		assert id != -1 && objX != -1 && objY != -1;
		oldX = script.getX();
		oldY = script.getY();
		if(!wallObject) {
			script.atObject(objX,  objY);
			return script.random(1500, 1750);
		} else {
			script.atWallObject(objX,  objY);
			return script.random(objX, objY);
		}
	}

	@Override
	public int waitTime() {
		return 2000;
	}

}
