package GIP.GIPTest;

import java.awt.Image;
import java.awt.Rectangle;

public abstract class Entity extends Settings {
	/** The uID of the entity */
	protected int ID;
	protected int TYPE;
	// Anchor
	protected double x;
	protected double y;
	protected double aX;
	protected double aY;
	protected boolean anchor;
	protected double dx;
	protected double dy;
	protected int HEALTH;
	protected String NAME;
	protected Image IMAGE;
	protected Rectangle entityRectangle = new Rectangle();
	
	/** Animation status */
	protected int mode = 0;
	/** Allows us to interact with the Entity */
	protected boolean interact;
	/** The way the player is facing default is D (Down/Left/Up/Right) */
	protected String face = "D";

	/**
	 * 3 Request that this entity move itself based on a certain ammount of time passing.
	 * @param delta The ammount of time that has passed in milliseconds
	 */
	public void move(long delta) {
		// public void move(long delta) {
		// update the location of the entity based on move speeds
		x += (dx * delta) / 1008;
		y += (dy * delta) / 1008;
	}

	/**
	 * Set the horizontal speed of this entity
	 * @param dx The horizontal speed of this entity (pixels/sec)
	 */
	public void setHorizontalMovement(double dirX) {
		dx = dirX;
	}

	/**
	 * Set the vertical speed of this entity
	 * @param dx The vertical speed of this entity (pixels/sec)
	 */
	public void setVerticalMovement(double dirY) {
		dy = dirY;
	}

	/**
	 * Get the horizontal speed of this entity
	 * @return The horizontal speed of this entity (pixels/sec)
	 */
	public double getHorizontalMovement() {
		return dx;
	}

	/**
	 * Get the vertical speed of this entity
	 * @return The vertical speed of this entity (pixels/sec)
	 */
	public double getVerticalMovement() {
		return dy;
	}

	/**
	 * Get the x location of this entity
	 * @return The x location of this entity
	 */
	public int getX() {
		return (int) x;
	}

	/**
	 * Get the y location of this entity
	 * @return The y location of this entity
	 */
	public int getY() {
		return (int) y;
	}

	/**
	 * Adds an amount of health
	 * @param d <br>The amount of health to add (or remove)
	 */
	public void addHealth(double d) {
		HEALTH += d;
	}

	/**
	 * Get the health
	 * @return The health
	 */
	public int getHealth() {
		return (int) HEALTH;
	}

	/**
	 * Set's the DISPLAY name of this Entity
	 * @param d <br>The amount of health to add (or remove)
	 */
	public void setName(String s, int index) {
		// int i = uID.getID(NAME);
		NAME = s;
	}

	/**
	 * Get the DISPLAY name
	 * @return NAME
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * Do the logic associated with this entity. This method will be called
	 * periodically based on game events
	 */
	public void doLogic() {
	}

	public void setImage(Image loadImage) {
		IMAGE = loadImage;
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

	public void setFace(String f) {
		face = f;
	}

	public void setMode(int m) {
		mode = m;
	}

	/**
	 * Will move the Entity based on it's current anchor. Entities will stay in
	 * range of 5 of their anchor.
	 */
	public void moveAnchor(int x, int y) {
		int d = (int) Math.round(Math.random() * 10);
		switch (d) {
		case 0:
			this.setHorizontalMovement(100);
			break;
		case 1:
			this.setHorizontalMovement(-100);
			break;
		case 2:
			this.setVerticalMovement(100);
			break;
		case 3:
			this.setVerticalMovement(-100);
			break;
		default:
			this.setHorizontalMovement(0);
			this.setVerticalMovement(0);
			break;
		}
		this.move((long) (Math.random() * 10));
	}

	public double getAnchorX() {
		return aX;
	}

	public double getAnchorY() {
		return aY;
	}

	public void setInteraction(boolean b) {
		interact = b;
	}
}
