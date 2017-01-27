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

			facingDown = directionCollection( "D");

			facingUp = directionCollection("U");

			facingLeft = directionCollection("L");

			facingRight = directionCollection("R");

			attackImages = directionCollection("a");
		}
	}

	private ArrayList<Image> directionCollection(String face) {
		ArrayList<Image> tmp = new ArrayList<Image>();

		if(face.equals("a")) {
			for(int i = 0; i < 4; i++) {
				if(i == 0) face = "L";
				if(i == 1) face = "R";
				if(i == 2) face = "U";
				if(i == 3) face = "D";
				for(int mode = 0; mode < 3; mode++) {

					img = uFiles.loadImage(path + "a" + subtype + face + mode + imgExt);
					tmp.add(img);

				}
			}
			
		} else {

			for(int mode = 0; mode < 3; mode++) {

				img = uFiles.loadImage(path + subtype + face + mode + imgExt);
				if(img != null) {tmp.add(img);} else {System.err.println("Error resolving image.");}

			}
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

	public Image getAttackImage(String face, int mode) {
		img = null;
		int maxModi = 3;
		int i = 0;
		if(face == "L") i = 0;
		if(face == "R") i = 1;
		if(face == "U") i = 2;
		if(face == "D") i = 3;
		
		img =  attackImages.get(maxModi * i + mode);
		if(img == null) attackImages.get(maxModi * i);
		if(img == null) img = getImage(face, mode);
		
		return img;
	}
}
