package GIP.GIPTest;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The entity that represents the player
 * @author Lars Carré
 */
public class EntityPlayer extends Entity {
	
	public EntityPlayer(int x, int y, int HEALTH, String NAME, String IMAGE) {
		this.TYPE = "player";
		this.x = x * 16;
		this.y = y * 16;
		this.HEALTH = HEALTH;
		this.oldHealth = HEALTH;
		this.maxHealth = HEALTH;
		this.NAME = NAME;
		this.DMG = 1;
		if (IMAGE == null) {
			this.IMAGE = uFiles.loadImage(tx_player + 0 + face + mode + imgExt);
		} else {
			this.IMAGE = uFiles.loadImage(tx_player + IMAGE + face + mode + imgExt);
		}
		this.imgH = this.IMAGE.getHeight(null);
		this.imgW = this.IMAGE.getWidth(null);
		this.entityRectangle.setBounds((int) this.x, (int) this.y, imgW, imgH);
	}

	public void movementCheck(long delta) {
		setHorizontalMovement(0);
		setVerticalMovement(0);

		// Check movement
		walking = false;
		if ((leftPressed) && (!rightPressed)) {
			setHorizontalMovement(-playerSpeed);
			face = "L";
			walking = true;
		} else if ((rightPressed) && (!leftPressed)) {
			setHorizontalMovement(playerSpeed);
			face = "R";
			walking = true;
		}
		if ((upPressed) && (!downPressed)) {
			setVerticalMovement(-playerSpeed);
			face = "U";
			walking = true;
		} else if ((downPressed) && (!upPressed)) {
			setVerticalMovement(playerSpeed);
			face = "D";
			walking = true;
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

		// Detect where we can or cannot walk
		g = (Graphics2D) graphicsBuffer.getDrawGraphics();
		if (mapBounds != null) {
			for (int i = 0; i < mapLines; i++) {
				for (int j = 0; j < mapColumns; j++) {
					int k = (mapColumns * i) + j;
					if (mapBounds.get(k).equals("1")) {
						int x1 = j * 16;
						int x2 = j * 16 + 16;
						int y1 = i * 16;
						int y2 = i * 16 + 16;
						if (!DEBUG) {
							entityRectangle.setBounds((int) x, (int) y, 32, 32);
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
						// Debug
						if (DEBUG || INFO) {
							g.setColor(Color.RED);
							g.drawRect(x1, y1, 16, 16);
						}
					}
				}
			}
		} else {
			System.out.println("No map bounds set.");
		}

		super.move(delta);
	}

	public void inflictDamage(double dMG) {
		uEffects.newEffect(player.getCenterX() - 8, player.getCenterY() - 8, uEffects.ef_SCRATCH);
		this.HEALTH -= dMG;
	}
	
}