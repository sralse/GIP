package GIP.GIPTest;

public class UtilsID extends Settings {

	// TODO Desc
	public int newID(Entity ent) {
		if (uIDList == null) {
			uIDList.add(0);
			ENTITIES.add(ent);
			return 0;
		}

		int i = uIDList.size();
		uIDList.add(i);
		System.out.println("New entity: " + ent + " ID: " + i);
		return i;
	}
	
	public Entity getEntity(int id) {
		return ENTITIES.get(id);
	}

	public static void init() {
		if (uIDList != null) {
			uIDList.clear();
			ENTITIES.clear();
		}
	}

}
