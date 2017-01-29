package GIP.GIPTest;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UtilsImages extends Settings {

	public ArrayList<UtilsImagesEntity> playerImages = new ArrayList<UtilsImagesEntity>();
	public ArrayList<UtilsImagesEntity> monsterImages = new ArrayList<UtilsImagesEntity>();
	public ArrayList<UtilsImagesEntity> animalImages = new ArrayList<UtilsImagesEntity>();
	public ArrayList<UtilsImagesEntity> villagerImages = new ArrayList<UtilsImagesEntity>();

	public void init() {

		String dir = "";
		String type = "";
		// player
		dir = tx_player;
		type = "player";
		System.out.println("Loading player sprites from: " + tx_player);
		playerImages = initSubImages(dir, type);
		// monsters
		dir = tx_monster;
		type = "monster";
		System.out.println("Loading monster sprites from: " + tx_monster);
		monsterImages = initSubImages(dir, type);
		// animals
		dir = tx_animal;
		type = "animal";
		System.out.println("Loading animal sprites from: " + tx_animal);
		animalImages = initSubImages(dir, type);
		// villagers
		dir = tx_villager;
		type = "player";
		System.out.println("Loading villager sprites from: " + tx_villager);
		villagerImages = initSubImages(dir, type);

	}

	private ArrayList<UtilsImagesEntity> initSubImages(String dir, String type) {
		Image img;
		ArrayList<UtilsImagesEntity> tmp = new ArrayList<UtilsImagesEntity>();
		for(int subtype = 0; subtype < 20; subtype++) {

			img = uFiles.loadImage(dir + subtype + "D" + 0 + imgExt);

			if(img != null) {
				// Make image instance
				UtilsImagesEntity instance = new UtilsImagesEntity(dir, type, subtype);
				tmp.add(instance);

			} else {
				System.out.println("No image was loaded from: " + dir + subtype);
				break;
			}
		}
		return tmp;
	}

	//TODO Make desc
	public Image scaleImageCubic(Image img, int i) {
		int imgWidth = img.getWidth(null);
		int imgHeight = img.getHeight(null);
		int newWidth;
		int newHeight;
		if (i > 0) {
			newWidth = imgWidth * i;
			newHeight = imgHeight * i;
		} else if (i < 0) {
			i = -i;
			newWidth = imgWidth / i;
			newHeight = imgHeight / i;
		} else {
			System.err.println("Invalid scaling!");
			return null;
		}
		BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		try {
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.drawImage(img, 0, 0, newWidth, newHeight, null);
		} finally {
			g.dispose();
		}
		return newImage;
	}

	/**
	 * TODO Other interpolation This function can scale any image using a
	 * neighbour interpolation given as the parameter.
	 * 
	 * @return <b>Image</b> The new and scaled version of the input Image.
	 * @param img
	 *            Image: This is the Image that needs to be converted.
	 * @param newWidth
	 *            Integer: The scaling amount for x.
	 * @param newHeight
	 *            Integer: The scaling amount for y.
	 */
	public Image scaleImageDetailed(Image img, int newWidth, int newHeight) {
		BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		try {
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.drawImage(img, 0, 0, newWidth, newHeight, null);
		} finally {
			g.dispose();
		}
		return newImage;
	}

	// TODO Make desc
	public Image rotateImage(Image img, int degrees) {
		// The required drawing location
		double locationX = img.getWidth(null) / 2;
		double locationY = img.getHeight(null) / 2;

		if ((locationX % 2 != 0 || locationY % 2 != 0) && DEBUG) {
			System.err.println("Image rotation has quality loss!");
		}

		double rotation = Math.toRadians(degrees);
		AffineTransform transform = new AffineTransform();
		transform.rotate(rotation, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		img = op.filter((BufferedImage) img, null);

		return img;
	}

	public Image getEntityImage(String TYPE, int subtype, String face, int mode, boolean attackMode) {
		if(TYPE=="player") {
			if(!DEBUG) {
				if(attackMode) return playerImages.get(subtype).getAttackImage(face, mode);
				return playerImages.get(subtype).getImage(face, mode);
			} else {
				return debug;
			}
		}
		if(TYPE=="monster") {
			if(attackMode) return monsterImages.get(subtype).getAttackImage(face, mode);
			return monsterImages.get(subtype).getImage(face, mode);
		}
		if(TYPE=="animal") {
			if(attackMode) return animalImages.get(subtype).getAttackImage(face, mode);
			return animalImages.get(subtype).getImage(face, mode);
		}
		if(TYPE=="villager") {
			if(attackMode) return villagerImages.get(subtype).getAttackImage(face, mode);
			return villagerImages.get(subtype).getImage(face, mode);
		}

		System.err.println("Wrong image request!");
		return debug;
		
	}

}
