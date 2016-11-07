package GIPTest;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Entity extends Settings {
	/** Starting X position of the entity */
	protected double x;
	/** Starting Y position of the entity */
	protected double y;
	/** The uID of the entity */
	protected int ID;
	/** The current speed of this entity horizontally (pixels/sec) */
	protected double dx;
	/** The current speed of this entity vertically (pixels/sec) */
	protected double dy;
	/** The way the player is facing default is D (Down/Left/Up/Right) */
	protected String face = "D";
	/** The mode wich the current Entity has (0 = Default/Standing, 1&2... = Walking/Animated) */
	protected int mode = 0;
	/** The HEALTH of the entity */
	protected int HEALTH;
	/** The TYPE of the entity */
	protected String NAME;
	/** The IMAGE of the entity */
	protected Image IMAGE;
	/** The rectangle or collision box of this entity */
	protected Rectangle entityRectangle = new Rectangle();
	// NPC data
	private static String entDataDir = lvlDir + entDir;
	private static List<String> entNPC = new ArrayList<String>();
	private static int entNPCSize = 5;
	
	/** 3
	 * Request that this entity move itself based on a certain ammount
	 * of time passing.
	 * 
	 * @param delta The ammount of time that has passed in milliseconds
	 */
	public void move(long delta) {
		//public void move(long delta) {
		// update the location of the entity based on move speeds
		x += (dx * delta) / 1008;
		y += (dy * delta) / 1008;
	}
	
	/**
	 * Set the horizontal speed of this entity
	 * 
	 * @param dx The horizontal speed of this entity (pixels/sec)
	 */
	public void setHorizontalMovement(double dirX) {
		dx = dirX;
	}

	/**
	 * Set the vertical speed of this entity
	 * 
	 * @param dx The vertical speed of this entity (pixels/sec)
	 */
	public void setVerticalMovement(double dirY) {
		dy = dirY;
	}
	
	/**
	 * Get the horizontal speed of this entity
	 * 
	 * @return The horizontal speed of this entity (pixels/sec)
	 */
	public double getHorizontalMovement() {
		return dx;
	}

	/**
	 * Get the vertical speed of this entity
	 * 
	 * @return The vertical speed of this entity (pixels/sec)
	 */
	public double getVerticalMovement() {
		return dy;
	}
	
	/**
	 * Get the x location of this entity
	 * 
	 * @return The x location of this entity
	 */
	public int getX() {
		return (int) x;
	}

	/**
	 * Get the y location of this entity
	 * 
	 * @return The y location of this entity
	 */
	public int getY() {
		return (int) y;
	}
	
	/**
	 * Adds an amount of health
	 * 
	 * @param d The amount of health to add (or remove)
	 * */
	public void addHealth(double d) {
		this.HEALTH += d;
	}
	
	/**
	 * Get the health
	 *  
	 * @return The health
	 */
	public int getHealth() {
		return (int) HEALTH;
	}
	
	/**
	 * Set's the DISPLAY name of this Entity
	 * 
	 * @param d The amount of health to add (or remove)
	 * */
	public void setName(String s, int index) {
//		int i = uID.getID(NAME);
		uID.setName(index, s);
		this.NAME = s;
	}
	
	/**
	 * Get the DISPLAY name
	 *  
	 * @return NAME
	 */
	public String getName() {
		return NAME;
	}
	
	/**
	 * Do the logic associated with this entity. This method
	 * will be called periodically based on game events
	 */
	public void doLogic() {
	}

	public void setImage(Image loadImage) {
		this.IMAGE = loadImage;	
	}
	
	public Image getImage() {
		return IMAGE;
	}
	
	public String getFace() {
		return face;
	}
	
	public int getMode() {
		return mode;
	}
	
	public void setFace(String face) {
		this.face = face;
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}

	public static void init() {
		player = new EntityPlayer(10, 1, 100, "name", null);
		initNPC();
	}

	private static void initNPC() {
		entNPC = Arrays.asList(dynamics.readFile("/" + entDataDir + mapName + "npc_" + mapID + lvlExt).split(splitSymbol));
		if(entNPC.size() >= entNPCSize) {
			for(int i = 0; i < (entNPC.size() / entNPCSize); i++) {
				Entity npc = new EntityNPC(
						Integer.parseInt(entNPC.get(i)), 
						Integer.parseInt(entNPC.get(i+1)), 
						Integer.parseInt(entNPC.get(i+2)), 
						entNPC.get(i+3), 
						ENTITY_NPC.valueOf(entNPC.get(i+4)).value);
				ENTITIES.add(npc);
				System.out.println(ENTITIES.size());
			}
		}	
	}
	
}