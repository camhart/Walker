

public abstract class ObjectHandler {
	
	int id, objX, objY;
	Script script;
	public ObjectHandler(int id, int objX, int objY, Script script) {
		this.id = id;
		this.objX = objX;
		this.objY = objY;
		this.script = script;
	}
	
//	/**
//	 * Determines if the given info qualifies for this objecthandler
//	 * @param id
//	 * @param x
//	 * @param y
//	 * @return
//	 */
	//public abstract boolean qualifies(int id, int x, int y);
	
	/**
	 * returns true if this no longer applies
	 * @return
	 */
	public abstract boolean overcome();
	
	/**
	 * Handles the given object
	 * 	(aka opens the door, climbs the wall, etc)
	 * @return TODO
	 */
	public abstract int handle();
	
	public abstract int waitTime();
}
