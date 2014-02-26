
public class HandlerFactory {
	
	
	public static Class<Script> SCRIPT_CLASS = Script.class;

	public static ObjectHandler getHandler(int id, int x, int y, Script script, int[] destX, int[] destY) {
		if(P2PGateHandler.qualifies(id,  x,  y)) {
			return new P2PGateHandler(id, x, y, script, destX, destY);
		}
		return null;
	}
}
