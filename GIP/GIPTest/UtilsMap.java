package GIP.GIPTest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilsMap extends Settings {
	// ArrayLists containing our tile data
	private static List<String> tileData = new ArrayList<String>();
	private static List<Image> tileImage = new ArrayList<Image>();

	/**
	 * init() Makes sure we get the following items: A virtual image of the map
	 * An array representing the map tiles An array representing the boundries
	 * An map object list Extending the existing boundries
	 */
	public static void init() {
		// Clear old data
		if (tileData != null) {
			tileData.clear();
			;
			tileImage.clear();
		}
		// Initialize our map ID
		mapID = 1;
		// Initialize our map image
		mapArray = getMapArray(mapID);
		mapImage = getMapImage(mapID);
		// Get the array of map boundries.
		mapBounds = getMapBounds();
		// Initialize all our objects
		uObjects.init();

	}

	private static List<String> getMapArray(int mapID) {
		// Transform our mapID to a complete mapname
		mapCurrent = mapDir + mapName + mapID + lvlExt;
		// Init reader
		InputStream in = UtilsMap.class.getClass().getResourceAsStream(mapCurrent);
		// Init Strings used by the read loop
		String str1, strOut = "";
		mapLines = 0;
		mapColumns = 0;
		// If no url exist then
		if (in == null) {
			System.err.println("Can't find ref: " + mapCurrent);
		}
		// Try reading from our file
		try {
			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			while ((str1 = read.readLine()) != null) {
				strOut += str1;
				mapLines++;
			}
			// Close reader and notify us of our completed action.
			read.close();
			if (DEBUG) {
				System.out.println("Done loading map: " + mapCurrent);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		// Transform our Char Array
		List<String> strOutSplit = Arrays.asList(strOut.split(splitSymbol));
		mapColumns = strOutSplit.size() / mapLines;
		return strOutSplit;
	}

	/**
	 * getMapImage(String mapID) Makes sure we do the following Reads our map
	 * array Makes a virtual image of the array
	 * 
	 * @param mapID
	 *            The map ID we're loading.
	 * @return Image A virtual representation of the file
	 */
	public static Image getMapImage(int mapID) {

		// DEBUG
		if (true) {
			System.out.println("Map rows/lines: " + mapLines);
			System.out.println("Map colums: " + mapColumns);
			System.out.println("Array length: " + mapArray.size());
		}

		// Get our image form our Array and return it
		Image image = mapArrayToImage(mapID);
		return image;
	}

	/**
	 * Transform any matrix file into a corresponding tilemap image.
	 * 
	 * @param charToImage
	 *            The character map as a set of characters.
	 * @return Image Returns the transformed matrix.
	 */
	public static Image mapArrayToImage(int mapID) {
		// Create local image
		BufferedImage image = new BufferedImage(mapColumns * 16, mapLines * 16, BufferedImage.TYPE_INT_ARGB);
		// Create local buffer
		Graphics2D gBuffer = (Graphics2D) image.getGraphics();
		// This part converts all the tiles we have to 1 list of tiles tileData
		String[] strTileData = uFiles.readFileString(mapDir + mapName + mapID + mapDirTiles + lvlExt)
				.split(splitSymbol);

		for (int i = 0; i < strTileData.length; i++) {
			String s = strTileData[i];
			tileData.add(s);
			tileImage.add(uFiles.loadImage(imgDir + s + imgExt));

			if (DEBUG && tileImage.get(i) != null) {
				System.out.println("Tile[" + tileData.get(i) + "] path : " + imgDir + tileData.get(i) + imgExt);
			}
		}

		// Check for safe Tile Data
		if ((tileImage == null) || (tileData == null)) {
			System.out.println("Tile Data null!");
			return null;
		} else if (DEBUG) {
			System.out.println("Tile image(s) : " + tileImage.size());
			System.out.println("Tile data : " + tileData);
		}

		gBuffer.create();
		gBuffer.setColor(Color.black);
		gBuffer.fillRect(0, 0, mapLines * 16, mapColumns * 16);

		for (int i = 0; i < mapLines; i++) {
			for (int j = 0; j < mapColumns; j++) {
				int k = (mapColumns * i) + j;
				// Load our Image
				Image img = tileImage.get(tileData.indexOf(mapArray.get(k)));
				gBuffer.drawImage(img, j * 16, i * 16, null);
			}
		}

		return image;
	}

	/**
	 * getMapBounds() Will give you an array containing the non walkables
	 * 
	 * @param mapArray2
	 * @return char[] A character array containing the non walkables
	 */
	private static List<String> getMapBounds() {
		List<String> newMapArray = new ArrayList<>(mapArray);
		for (int i = 0; i < mapArray.size(); i++) {
			String s = mapArray.get(i);
			newMapArray.set(i, "0");
			for (int j = 0; j < mapForbidden.size(); j++) {
				String m = mapForbidden.get(j);
				if (s.equals(m)) {
					newMapArray.set(i, "1");
				}
			}
		}
		return newMapArray;
	}
}