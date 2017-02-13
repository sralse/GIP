package GIP.GIPTest;

public class ItemWood extends Item {

	private final static int WOOD_OAK = 1;
	private final static int WOOD_MAPLE = 2;
	private final static int WOOD_YELLO = 3;
	private final static int WOOD_MAGIC = 4;
	
	public ItemWood(String name, int amount,int SUBTYPE) {
		if(name != null) this.NAME = name;
		this.AMOUNT = amount;
		if(amount == 0) this.AMOUNT = 1;
		this.TYPE = "wood";
		this.SUBTYPE = SUBTYPE;
		
		if (SUBTYPE != 0) {
			switch(SUBTYPE) {
			case WOOD_OAK:
				if(name == null) this.NAME = "Oak Logs";
				break;
			case WOOD_MAPLE:
				if(name == null) this.NAME = "Maple Logs";
				break;
			case WOOD_YELLO:
				if(name == null) this.NAME = "Yello Logs";
				break;
			case WOOD_MAGIC:
				if(name == null) this.NAME = "Magic Logs";
				break;
			}
		}
		
		this.IMAGE = uFiles.loadImage(itemDir + this.TYPE + "_" + this.SUBTYPE + imgExt);
	}
}
