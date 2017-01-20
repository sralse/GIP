package GIP.GIPTest;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class UtilsImages extends Settings {
	
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
}
