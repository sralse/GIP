package GIP.GIPTest;

import java.awt.Image;

public class UtilsItem extends Settings{
	public final Image IMAGE_MELEE = uImages.scaleImageDetailed(uFiles.loadImage(itemDir + "sword_1" + imgExt), 16, 16);

	public void init() {
		player.clearItems();
		Item wsword = new ItemSword("Sam", 0, 1, null);
		player.addItem(0, wsword);
		player.DMG = wsword.DMG;
	}
	
	public Item getPlayerHotbarItem(int i) {
		return player.getItem(i);
	}
	
	public Item getPlayerHotbarItem(String i) {
		if(i.toLowerCase().equals("m")) return player.getItem(0);
		if(i.toLowerCase().equals("v")) return player.getItem(1);
		if(i.toLowerCase().equals("b")) return player.getItem(2);
		if(i.toLowerCase().equals("n")) return player.getItem(3);
		return null;
	}
	
	
}
