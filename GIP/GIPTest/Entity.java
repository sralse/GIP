package GIP.GIPTest;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Entity extends Settings {
	/** The uID of the entity */
	protected int ID;
	protected String TYPE;
	protected int SUBTYPE;
	// Anchor
	protected double x;
	protected double y;
	protected int aX;
	protected int aY;
	protected int nX;
	protected int nY;
	protected boolean anchor;
	protected boolean noMove;
	protected double dx;
	protected double dy;
	protected double HEALTH;
	protected double maxHealth;
	protected double oldHealth;
	protected double dmgTaken = 0;
	protected int healthCounter;
	protected String NAME;
	protected Image IMAGE;
	protected int imgW;
	protected int imgH;
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
	// Attack stuff
	protected boolean inAttack = false;
	protected int TSLA = 0;
	protected int attackSpeedInterval = 1000;
	protected int attackFindingRadius = tileWidth * 8;
	protected int attackingRadius = tileWidth * 3;
	protected double DMG = 0;
	protected Entity target = null;
	/** The way the player is facing default is D (Down/Left/Up/Right) */
	protected String face = "D";
	protected String oldFace = face;
	protected boolean forceAnimation = false;
	// Entity speed and reach settings
	protected static int radius = 100;
	protected static int defaultEntitySpeed = (playerSpeed * 3) / 4;
	// ITEMS
	protected static ArrayList<Item> ITEMS = new ArrayList<Item>();

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
		// If angry it will stop walking once touching you
		if(inAttack && target != null) {
			if(entityRectangle.intersects(target.entityRectangle)) {
				setHorizontalMovement(0);
				setVerticalMovement(0);
				return;
			}
		}
		// If we can't move, we can't move!
		if(noMove) {
			setHorizontalMovement(0);
			setVerticalMovement(0);
			return;
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
		
		entityRectangle.setBounds((int) x, (int) y, imgW, imgH);
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

	/**
	 * @param path The directory of the image + image prefix.
	 * @param interval The interval at which the entity animation should update
	 * */
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

		if((face != oldFace || mode != oldMode) && !inAttack) {
			IMAGE = uImages.getEntityImage(TYPE, SUBTYPE, face, mode, inAttack);
			imgH = IMAGE.getHeight(null);
			imgW = IMAGE.getWidth(null);
			oldFace = face;
			oldMode = mode;
		} else if (walkCounter > interval && inAttack) {
			IMAGE = uImages.getEntityImage(TYPE, SUBTYPE, face, mode, inAttack);
			imgH = IMAGE.getHeight(null);
			imgW = IMAGE.getWidth(null);
			oldFace = face;
			oldMode = mode;
		}

		if(IMAGE == null) {
			IMAGE = uImages.getEntityImage(TYPE, SUBTYPE, "D", mode, inAttack);
			imgH = IMAGE.getHeight(null);
			imgW = IMAGE.getWidth(null);
		}
	}

	public int getCenterX() {
		return (int) (x + (imgW / 2));
	}
	
	public int getCenterY() {
		return (int) (y + (imgH / 2));
	}
	
	public long getAttackTimer() {
		return TSLA;
	}
	
	public void addToAttackTimer(long delta) {
		if(TSLA <= 5000) TSLA += delta;
	}
	
	public void updateAttackTimer() {
		if(TSLA <= 5000) TSLA += gameLoopTime;
		if(TSLA >= 1000) inAttack = false;
	}
	
	public void resetAttackTimer() {
		TSLA = 0;
	}
	
	public boolean canAttack() {
		return (TSLA > attackSpeedInterval);
	}

	public void setTarget(Entity ent) {
		target = ent;
		if(target != null && inAttack) {
			if (!target.entityRectangle.intersects(entityRectangle)) {
				nX = (int) target.getCenterX();
				nY = (int) target.getCenterY();
			}
		}
	}
	
	public void updateHealthTimer() {
		if (healthCounter <= 2000) healthCounter += gameLoopTime;
	}
	
	public void clearItems() {
		ITEMS.clear();
	}
	
	/**
	  * Adds a certain item to your hotbar from slot 0 to 3
	  * @param index The index at which your item should be inserted (0...3)
	  * @param item The item that you want to add
	  */
	public void addWeapon(int index, Item item) {
		if(index > 3) return;
		ITEMS.add(index, item);
		item.invPOS = index;
		
		// TODO Replace weapon
		
	}
	
	 /**
	  * Adds a certain piece of armor
	  * @param item The armor that you want to add
	  */
	public void addArmor(Item item) {
		String string = item.TYPE.toLowerCase();
		int index = 0;
		if(string.equals("helmet")) index = 1;
		if(string.equals("chestplate")) index = 2;
		if(string.equals("leggings")) index = 3;
		if(string.equals("boots")) index = 4;
		if(string.equals("shield")) index = 5;
		if(index == 0) return;
		
		// TODO Replace armor
		
		ITEMS.add(index + 3, item);
		item.invPOS = index + 3;
	}
	
	/**
	  * Adds a certain item to your hotbar from slot 0 to the max Inventory size
	  * @param index The index at which your item should be inserted (0...inventorySize)
	  * @param item The item that you want to add to your inventory
	  */
	public void addInvItem(int index, Item item) {
		ITEMS.add(index + 9, item);
		item.invPOS = index + 9;
		
		// TODO replace item
		
	}
	
	 /**
	  * Returns the weapon in the desired slot
	  * @param index Index of the Item Weapon
	  */
	public Item getWeapon(int index) {
		if(index > 3) return null;
		if(index >= ITEMS.size()) return null;
		return ITEMS.get(index);
	}
}
