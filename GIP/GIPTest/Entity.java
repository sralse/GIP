package GIP.GIPTest;

import java.awt.Image;
import java.awt.Rectangle;

public abstract class Entity extends Settings {
	/** The uID of the entity */
	protected int ID;
	protected int TYPE;
	protected int SUBTYPE;
	// Anchor
	protected double x;
	protected double y;
	protected int aX;
	protected int aY;
	protected int nX;
	protected int nY;
	protected boolean anchor;
	protected double dx;
	protected double dy;
	protected int HEALTH;
	protected int maxHealth;
	protected String NAME;
	protected Image IMAGE;
	protected Rectangle entityRectangle = new Rectangle();
	// Animations modes of an entity
	protected int mode = 0;
	protected int oldMode = mode;
	// Helps detecting when to animate
	protected int walkCounter;
	protected boolean walking = false;
	// Helps detecting if we can interact with an entity
	protected boolean canInteract;
	protected boolean interact = false;
	/** The way the player is facing default is D (Down/Left/Up/Right) */
	protected String face = "D";
	protected String oldFace = face;
	// Entity speed and reach settings
	protected static int radius = 100;
	protected static int defaultEntitySpeed = (playerSpeed * 3) / 4;
	/** Entity AttackRadius */
	protected static int AR = 25;

	/**
	 * 3 Request that this entity move itself based on a certain ammount of time passing.
	 * @param delta The ammount of time that has passed in milliseconds
	 * @param speed The speed at wich the entity moves
	 */
	public void movementCheck(long delta, int speed) {
		int adx = (int) (x - nX);
		int ady = (int) (y - nY);
		int absDX = (int) Math.abs(adx);
		int absDY = (int) Math.abs(ady);
		setHorizontalMovement(0);
		setVerticalMovement(0);
		walking = false;

		if(!(adx == 0 && ady == 0) && !(interact)) {
			walking = true;
			if(absDX > absDY) {
				if(adx > 0) {
					setHorizontalMovement(-speed);
				} else if (adx < 0) {
					setHorizontalMovement(speed);
				} else {
					setHorizontalMovement(0);
				}
			} else {
				if(ady > 0) {
					setVerticalMovement(-speed);
				} else if (ady < 0) {
					setVerticalMovement(speed);
				} else {
					setVerticalMovement(0);
				}
			}
		} else {
			walking = false;
		}

		// if we're moving left and have reached the left hand side
		// of the screen, don't move
		if ((dx < 0) && (x <= 1)) {
			setHorizontalMovement(0);
			walking = false;
		}
		// if we're moving right and have reached the right hand side
		// of the screen, don't move
		if ((dx > 0) && (x >= screenWidth - 32)) {
			setHorizontalMovement(0);
			walking = false;
		}
		// if we're moving up and have reached the up hand side
		// of the screen, don't move
		if ((dy < 0) && (y <= 16)) {
			setVerticalMovement(0);
			walking = false;
		}
		// if we're moving down and have reached the down hand side
		// of the screen, don't move
		if ((dy > 0) && (y >= screenHeight - 34 - screenCorrection)) {
			setVerticalMovement(0);
			walking = false;
		}

		// Detect if the entity can be there
		if (mapBounds != null) {
			for (int i = 0; i < mapLines; i++) {
				for (int j = 0; j < mapColumns; j++) {
					int k = (mapColumns * i) + j;
					if (mapBounds.get(k).equals("1")) {
						int x1 = j * 16;
						int x2 = j * 16 + 16;
						int y1 = i * 16;
						int y2 = i * 16 + 16;
						// TOP
						if (dy > 0) {
							if (entityRectangle.intersects(x1 + screenCorrection, y1, 16 - screenCorrection, 1)) {
								setVerticalMovement(0);
								walking = false;
							}
						}
						// BOTTOM
						if (dy < 0) {
							if (entityRectangle.intersects(x1 + screenCorrection, y2, 16 - screenCorrection, 1)) {
								setVerticalMovement(0);
								walking = false;
							}
						}
						// LEFT
						if (dx > 0) {
							if (entityRectangle.intersects(x1, y1 + screenCorrection, 1, 16 - screenCorrection)) {
								setHorizontalMovement(0);
								walking = false;
							}
						}
						// RIGHT
						if (dx < 0) {
							if (entityRectangle.intersects(x2, y1 + screenCorrection, 1, 16 - screenCorrection)) {
								setHorizontalMovement(0);
								walking = false;
							}
						}
					}
				}
			}
		} else {
			System.out.println("No map bounds set.");
		}

		move(delta);
	}

	public void move(long delta) {
		// public void move(long delta) {
		// update the location of the entity based on move speeds
		x += (dx * delta) / 1000;
		y += (dy * delta) / 1000;
		
		entityRectangle.setBounds((int) x, (int) y, IMAGE.getWidth(null), IMAGE.getHeight(null));
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

	public int getX() {
		return (int) x;
	}

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

	public int getID() {
		return ID;
	}

	public void setImage(Image loadImage) {
		IMAGE = loadImage;
	}

	public Image getImage() {
		return IMAGE;
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

	public void animationWalk(String path, int interval) {
		// Walk animation
		walkCounter += gameLoopTime;

		if(walkCounter > interval && walking == true) {
			mode += 1;
			if(mode > 2) {mode = 1;}
			walkCounter = 0;
		} else if (walking == false) {
			mode = 0;
		}

		if(face != oldFace || mode != oldMode) {
			IMAGE = uFiles.loadImage(path + SUBTYPE + face + mode + imgExt);
			oldFace = face;
			oldMode = mode;
		}

		if(IMAGE == null) {
			IMAGE =  uFiles.loadImage(path + SUBTYPE + "D" + 0 + imgExt);
		}
	}

	public int getCenterX() {
		return (int) (x + (IMAGE.getWidth(null) / 2));
	}
	
	public int getCenterY() {
		return (int) (y + (IMAGE.getHeight(null) / 2));
	}
}
