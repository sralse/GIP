package GIP.GIPTest;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class MapObject extends Settings {
	protected String NAME;
	protected int x;
	protected int y;
	protected Rectangle collisionRect;
	protected Rectangle imageRect;
	protected int width;
	protected int height;
	protected ArrayList<Image> images = new ArrayList<Image>();
	protected Image IMAGE;
	private int imageTimer;
	private int imageCount;
	
	MapObject(String objectType, int x, int y) {
		this.x = x * 16;
		this.y = y * 16;
		this.NAME = objectType;
		int subtype = 0;
		this.IMAGE = uFiles.loadImage(tx_objects + objectType + subtype + imgExt);
		Image tmp = IMAGE;
		do {
			images.add(tmp);
			tmp = uFiles.loadImage(tx_objects + objectType + subtype + imgExt);
			subtype++;
		} while (tmp != null);
		this.height = images.get(0).getHeight(null);
		this.width = images.get(0).getWidth(null);
		this.imageRect = new Rectangle(this.x,this.y,width,height);
		this.collisionRect = new Rectangle(this.x + (width / 3), this.y + (height / 3), width / 3, height / 3);
	}
	
	/**
	 * This function provides support for objects wich appear to 'move'
	 * <br>Whenever asking for the current object's picture it will 
	 * <br>automaticly update. The interval is currently 250
	 * @return Returns the next image (if any).
	 * */
	public Image getImage() {
		if(images.size() > 1) {
			imageTimer += gameLoopTime;
			if(imageTimer >= 200) {
				imageTimer = 0;
				imageCount += 1;
				if(imageCount >= images.size()) imageCount = 0;
				this.IMAGE = images.get(imageCount);
			}
		}
		
		return IMAGE;
	}
	
}
