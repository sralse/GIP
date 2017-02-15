package GIP.GIPTest;

public class UtilsObjects extends Settings {

	private static String[] objectsList = {};

	/**
	 * init() Initializes everything that has to do with MapObjects
	 */
	public void init() {
		// Clear old data
		mapObjects.clear();
		// Read the file and its contents where the objects are
		// Transform our map into objectsList
		String mapCurrent = mapDir + mapName + mapID + mapDirObjects + lvlExt;
		objectsList = uFiles.readFileString(mapCurrent).split(splitSymbol);
		// Get the lists of objects that need to be drawn on this map.
		getObjects();
	}

	public void getObjects() {
		// Parsing of our objects
		MapObject object = null;
		for (int i = 0; i < objectsList.length / 3; i ++) {
			object = new MapObject(objectsList[i * 3], Integer.parseInt(objectsList[(i * 3) + 1]), Integer.parseInt(objectsList[(i * 3 ) + 2]));
			mapObjects.add(object);
			// Debug output
			if (DEBUG) {
				System.out.println("Object added : " + object.NAME + " images(x): " + object.images.size());
				System.out.println("RAW object info: " + object);
			}

		}

		// Debug output
		if (DEBUG) {
			System.out.println(mapObjects.size() + " objects detected");
		}
	}

}
