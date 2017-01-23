package GIP.GIPTest;

public class UtilsID extends Settings {
	
	public int getEntity(Entity ent) {
		return ENTITIES.indexOf(ent);
	}

	public static void init() {
		if (ENTITIES != null) {
			ENTITIES.clear();
		}
	}

	public void removeID(int iD) {
		ENTITIES.remove(iD);
		System.err.println("Deleted entity ID: " + iD + " ENTITIES left: " + ENTITIES.size());
	}

	public void addEntity(Entity ent) {
		ENTITIES.add(ent);
		ent.ID = ENTITIES.size() - 1;
		System.out.println("New entity: " + ent + " ID: " + ent.ID);
	}

	public void addPlayer(Entity player) {
		ENTITIES.clear();
		ENTITIES.add(player);
		player.ID = 0;
		System.out.println("New entity: " + player + " ID: " + player.ID);
	}

}
