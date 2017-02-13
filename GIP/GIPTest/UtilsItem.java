package GIP.GIPTest;

import java.awt.Image;

public class UtilsItem extends Settings{
	public final Image IMAGE_MELEE = uImages.scaleImageLinear(uFiles.loadImage(itemDir + "sword_1" + imgExt), 16, 16);

	public void init() {
		//Test items
		player.clearItems();
		Item item = new ItemSword("Sam", 1);
		player.addWeapon(0, item);
		item = new ItemSword("Carl", 2);
		player.addWeapon(1, item);
		item = new ItemSword("Lol", 3);
		player.addWeapon(2, item);
		item = new ItemWood(null, 0, 1);
		player.addInvItem(0, item);
		item = new ItemWood(null, 0, 2);
		player.addInvItem(9, item);
		item = new ItemWood(null, 0, 3);
		player.addInvItem(41, item);
	}
	
	@Deprecated
	public Item getPlayerHotbarItem(int i) {
		return player.getWeapon(i);
	}
	
	public Item getPlayerHotbarItem(String i) {
		if(i.toLowerCase().equals("c")) return player.getWeapon(0);
		if(i.toLowerCase().equals("v")) return player.getWeapon(1);
		if(i.toLowerCase().equals("b")) return player.getWeapon(2);
		if(i.toLowerCase().equals("n")) return player.getWeapon(3);
		return null;
	}
	
	
}
