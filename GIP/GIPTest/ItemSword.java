package GIP.GIPTest;

import java.awt.Image;

public class ItemSword extends Item {
	
	// Keys
	private final static int SWORD_WOOD = 1;
	private final static int SWORD_STONE = 2;
	private final static int SWORD_IRON = 3;
	private final static int SWORD_DIAMOND = 5;
	
	public ItemSword(String name, double damage, int SUBTYPE,Image img) {
		this.NAME = name;
		this.DMG = damage;
		this.TYPE = "sword";
		this.SUBTYPE = SUBTYPE;
		
		if (SUBTYPE != 0) {
			switch(SUBTYPE) {
			case SWORD_WOOD:
				this.DMG = 1.5d;
				break;
			case SWORD_STONE:
				this.DMG = 2.5d;
				break;
			case SWORD_IRON:
				this.DMG = 4.0d;
				break;
			case SWORD_DIAMOND:
				this.DMG = 6.0d;
				break;
			}
		}
		
		this.IMAGE = uImages.scaleImageDetailed(uFiles.loadImage(itemDir + this.TYPE + "_" + this.SUBTYPE + imgExt), 32, 32);
		
	}

}
