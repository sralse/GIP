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
		updateID();
		System.err.println("Deleted entity ID: " + iD + " ENTITIES left: " + ENTITIES.size());
	}

	public void addEntity(Entity ent) {
		ENTITIES.add(ent);
		ent.ID = ENTITIES.size() - 1;
		System.out.println("TYPE: " + ent.TYPE 
				+ " SUBTYPE: " + ent.SUBTYPE 
				+ " Display name: " + ent.NAME
				+ " uID: " + ent.ID);
	}

	public void addPlayer(Entity player) {
		ENTITIES.add(player);
		player.ID = 0;
		System.out.println("TYPE: " + player.TYPE 
				+ " SUBTYPE: " + player.SUBTYPE 
				+ " Display name: " + player.NAME
				+ " uID: " + player.ID);
	}
	
	private void updateID() {
		System.err.println("Updating ENTITIES");
		for(int i = 0; i < ENTITIES.size(); i++) {			
			ENTITIES.get(i).ID = i;
			System.out.println("Name: " + ENTITIES.get(i).NAME 
					+ " ID: " + ENTITIES.get(i).ID 
					+ " TYPE: " + ENTITIES.get(i).TYPE);
		}
	}
}
