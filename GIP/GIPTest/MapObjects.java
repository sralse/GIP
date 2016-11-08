package GIP.GIPTest;

import java.awt.Image;
import java.util.ArrayList;

public class MapObjects extends Settings {

	public static ArrayList<Integer> objectX = new ArrayList<Integer>();
	public static ArrayList<Integer> objectY = new ArrayList<Integer>();
	public static ArrayList<Integer> objectNameWidth = new ArrayList<Integer>();
	public static ArrayList<Integer> objectNameLength = new ArrayList<Integer>();
	public static ArrayList<String> objectName = new ArrayList<String>();
	public static ArrayList<Image> objectImage  = new ArrayList<Image>();
	private static String[] objectsList = {};
	public static String splitSymbol = ",";

	/** Will make an instance of all our drawing methods */
	
	public static int sum() {
		if(objectsList == null) {return 0;}
		int val = objectName.size();
		return val;
	}
	
	/**
	 * init() Initializes everything that has to do with MapObjects
	 * */
	public static void init() {
		// Clear old data
		objectX.clear();
		objectY.clear();
		objectNameWidth.clear();
		objectNameLength.clear();
		objectName.clear();
		objectImage.clear();
		// Read the file and its contents where the objects are
		// Transform our map into objectsList
		String mapCurrent = mapDir + mapName + mapID  + mapDirObjects  + lvlExt ;
		objectsList = dynamics.readFileString(mapCurrent).split(splitSymbol);
		//  TODO Get the array of non walkables, extend the current mapBounds
		extendBounds();
		// TODO Get the lists of objects that need to be drawn on this map.
		getObjects();
	}

	public static void getObjects() {
		// TODO Parsing of our objects
		int j = 0;
		for(int i = 0; i <= objectsList.length - 3; i+=3) {
			// Seperate names
			objectName.add(objectDir + objectsList[i]);
			// Seperate X values
			objectX.add(Integer.parseInt(objectsList[i + 1]));
			// Seperate Y values
			objectY.add(Integer.parseInt(objectsList[i + 2]));
			
			// Debug output
			if(DEBUG){System.out.println("Object detected : " + objectName.get(j));}
			
			j++;
		}
		
		for(int i = 0; i < objectName.size(); i++){
			// Load our image into the array
			Image image = dynamics.loadImage(objectName.get(i));
			
			if(image != null) {
				objectImage.add(image);
			}
		}	
		
		
		// Debug output
		if(DEBUG){System.out.println(objectName.size() + " objects detected");}
	}

	public static void extendBounds() {
		// TODO Extend our current MapUtils.mapBounds
		
	}

}
