package GIP.GIPTest;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class UtilsFiles extends Settings {
	
	
	public String readFileString(String filePath) {
		// Init reader
		BufferedReader read;
		// Init Strings used by the read loop
		String str1, strOut = "";
		// Init url, and read mapCurrent path to URL
		InputStream in = UtilsFiles.class.getClass().getResourceAsStream(filePath);

		if (in == null) {
			System.out.println("Can't find ref: " + filePath);
		}
		// Try reading our file
		try {
			read = new BufferedReader(new InputStreamReader(in));
			while ((str1 = read.readLine()) != null) {
				strOut += str1;
			}
			// Close reader and notify us of our completed action.
			read.close();
			if (DEBUG) {
				System.out.println("Done loading file: " + filePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return strOut;
	}
	
	// TODO Make it happen
	public List<String> readFileArray(String filePath) {
		List<String> tmp = new ArrayList<String>();
		// Init reader
		BufferedReader read;
		// Init Strings used by the read loop
		String str1;
		// Init url, and read mapCurrent path to URL
		InputStream in = UtilsFiles.class.getClass().getResourceAsStream(filePath);

		if (in == null) {
			System.out.println("Can't find ref: " + filePath);
		}
		// Try reading our file
		try {
			read = new BufferedReader(new InputStreamReader(in));
			while ((str1 = read.readLine()) != null) {
				tmp.add(str1);
			}
			// Close reader and notify us of our completed action.
			read.close();
			if (DEBUG) {
				System.out.println("Done loading file: " + filePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return tmp;
	}

	/**
	 * This function will load images as a type Image if the image does not
	 * exist it will return null
	 * 
	 * @return <b>Image</b> The cached image from the string.
	 * @return null If the String is invalid.
	 * @param FilePath
	 *            String: This is the path where the image is located.
	 */
	@SuppressWarnings("static-access")
	public Image loadImage(String FilePath) {
		BufferedImage img = null;
		// Look up file
		URL url = UtilsFiles.class.getClass().getClassLoader().getSystemResource(FilePath);
		if (url == null) {
			System.err.println("Can't find ref: " + FilePath);
			return null;
		}
		// Try fetching image
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("FAILED! Whilst loading Image: " + url);
		}
		// // create an accelerated image of the right size to store our sprite
		// in
		// GraphicsConfiguration gc =
		// GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		// Image image =
		// gc.createCompatibleImage(img.getWidth(),img.getHeight(),Transparency.BITMASK);
		// // draw our source image into the accelerated image
		// img.getGraphics().clearRect(0, 0, img.getWidth(), img.getHeight());
		// img.getGraphics().drawImage(image,0,0,null);
		return img;
	}

	/**
	 * This function can scale any image using a cubic interpolation given as
	 * the parameter.
	 * 
	 * @return <b>Image</b> The new and scaled version of the input Image.
	 * @param img
	 *            Image: This is the Image that needs to be converted.
	 * @param i
	 *            Integer: The scaling amount.
	 */
}
