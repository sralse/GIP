package GIP.GIPTest;

import java.awt.Image;
import java.util.ArrayList;

public class UtilsImagesEntity extends Settings {
	protected String type;
	protected String face;
	protected int subtype;
	protected int mode;
	private String path;
	protected ArrayList<Image> facingUp = new ArrayList<Image>();
	protected ArrayList<Image> facingDown = new ArrayList<Image>();
	protected ArrayList<Image> facingLeft = new ArrayList<Image>();
	protected ArrayList<Image> facingRight = new ArrayList<Image>();
	// TODO Attack images
	protected ArrayList<Image> attackImages = new ArrayList<Image>();
	private Image img = null;

	public UtilsImagesEntity(String dir, String type, int subtype) {
		this.type = type;
		this.subtype = subtype;
		this.path = dir;
		img = uFiles.loadImage(dir + subtype + "D" + 0 + imgExt);
		if(img != null) {

			for(int i = 0; i < 4; i++) {
				switch(i) {
				case 0:
					face = "D";
					facingDown = directionCollection();
					break;
				case 1:
					face = "U";
					facingUp = directionCollection();
					break;
				case 2:
					face = "L";
					facingLeft = directionCollection();
					break;
				case 3:
					face = "R";
					facingRight = directionCollection();
					break;
				}
			}
		}
	}

	private ArrayList<Image> directionCollection() {
		ArrayList<Image> tmp = new ArrayList<Image>();
		for(int mode = 0; mode < 3; mode++) {

			img = uFiles.loadImage(path + subtype + face + mode + imgExt);
			if(img != null) {tmp.add(img);} else {System.err.println("Error resolving image.");}

		}
		return tmp;
	}
	
	public Image getImage(String face, int mode) {
		img = null;
		if(face == "U") {
			img =  facingUp.get(mode);
		}
		if(face == "D") {
			img =  facingDown.get(mode);
		}
		if(face == "L") {
			img =  facingLeft.get(mode);
		}
		if(face == "R") {
			img =  facingRight.get(mode);
		}
		return img;
	}
}
