package GIP.GIPTest;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The entity that represents the players ship
 * 
 * @author Kevin Glass
 */
public class EntityPlayer extends Entity {
	
	/**
	 * Create a new entity to represent the players ship
	 *  
	 * @param game The game in which the ship is being created
	 * @param ref The reference to the sprite to show for the ship
	 * @param x The initial x location of the player's ship
	 * @param y The initial y location of the player's ship
	 */
	public EntityPlayer(int x, int y, int HEALTH, String NAME, String IMAGE) {
		this.x = x * 16;
		this.y = y * 16;
		this.HEALTH = HEALTH;
		this.NAME = NAME;
		this.ID = uID.newID(this.NAME);
		if(IMAGE == null) {
			this.IMAGE = dynamics.loadImage(tx_npc + 0 + face + mode + imgExt);
		} else {
			this.IMAGE = dynamics.loadImage(tx_npc + IMAGE + face + mode + imgExt);
		}
	}
	
	public void movementCheck(long delta) {
		setHorizontalMovement(0);
		setVerticalMovement(0);
		
		// Check movement
		if ((leftPressed) && (!rightPressed)) {
			setHorizontalMovement(-moveSpeed);
			player.setFace("L");
		} else if ((rightPressed) && (!leftPressed)) {
			setHorizontalMovement(moveSpeed);
			player.setFace("R");
		}
		if ((upPressed) && (!downPressed)) {
			setVerticalMovement(-moveSpeed);
			player.setFace("U");
		} else if ((downPressed) && (!upPressed)) {
			setVerticalMovement(moveSpeed);
			player.setFace("D");
		}
		
		// if we're moving left and have reached the left hand side
		// of the screen, don't move
		if ((dx < 0) && (x <= 1)) {
			dx = 0;
		}
		// if we're moving right and have reached the right hand side
		// of the screen, don't move
		if ((dx > 0) && (x >= screenWidth - 32)) {
			dx = 0;
		}
		// if we're moving up and have reached the up hand side
		// of the screen, don't move
		if ((dy < 0) && (y <= 16)) {
			dy = 0;
		}
		// if we're moving down and have reached the down hand side
		// of the screen, don't move
		if ((dy > 0) && (y >= screenHeight - 34 - screenCorrection)) {
			dy = 0;
		}
		
		// Detect where we can or cannot walk
		g = (Graphics2D) graphicsBuffer.getDrawGraphics();
		if(mapBounds != null) {
			for(int i = 0; i < mapLines; i++) {
				for (int j = 0; j < mapColumns; j++) {
					int k = (mapColumns * i) + j;
					if(mapBounds.get(k).equals("1")) {
						int x1 = j * 16;
						int x2 = j * 16 + 16;
						int y1 = i * 16;
						int y2 = i * 16 + 16;
						if(!DEBUG) {
							entityRectangle.setBounds((int) x, (int) y, 32, 32);
							// TOP
							if(dy > 0) {
								if(entityRectangle.intersects(x1 + screenCorrection, y1, 16 - screenCorrection, 1)) {
									dy = 0;
								}
							}
							// BOTTOM
							if(dy < 0) {
								if(entityRectangle.intersects(x1 + screenCorrection, y2, 16 - screenCorrection, 1)) {
									dy = 0;
								}
							}
							// LEFT
							if(dx > 0) {
								if(entityRectangle.intersects(x1, y1 + screenCorrection, 1, 16 - screenCorrection)) {
									dx = 0;
								}
							}
							// RIGHT
							if(dx < 0) {
								if(entityRectangle.intersects(x2, y1 + screenCorrection, 1, 16 - screenCorrection)) {
									dx = 0;
								}
							}
						}
						// Debug
						if(DEBUG || INFO) {
							g.setColor(Color.RED);
							g.drawRect(x1, y1, 16, 16);
						}
					}
				}
			}
		} else { System.out.println("No map bounds set."); }

		super.move(delta);
	}
	
	/**
	 * Notification that the player's ship has collided with something
	 * 
	 * @param other The entity with which the ship has collided
	 */
//	public void collidedWith(other) {
		// if its an alien, notify the game that the player
		// is dead
//		if (other instanceof AlienEntity) {
//			game.notifyDeath();
//		}
//	}
}