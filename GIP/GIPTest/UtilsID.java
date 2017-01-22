package GIP.GIPTest;

public class UtilsID extends Settings {

	// TODO Desc
	public int newID() {
		if (uIDList == null) {
			uIDList.add(0);
			return 0;
		}

		int i = uIDList.size();
		uIDList.add(i);
		System.out.println("New entity: " + " ID: " + i);
		return i;
	}
	
	public int getEntity(Entity ent) {
		return ENTITIES.indexOf(ent);
	}

	public static void init() {
		if (uIDList != null) {
			uIDList.clear();
			ENTITIES.clear();
		}
	}

}
