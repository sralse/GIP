package GIP.GIPTest;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The entity that represents the player
 * @author Lars Carré
 */
public class EntityPlayer extends Entity {
	
	public EntityPlayer(int x, int y, int HEALTH, String NAME, int SUBTYPE) {
		this.TYPE = "player";
		this.x = x * 16;
		this.y = y * 16;
		this.HEALTH = HEALTH;
		this.oldHealth = HEALTH;
		this.maxHealth = HEALTH;
		this.NAME = NAME;
		this.DMG = 1;
		if (SUBTYPE == 0) {
			this.IMAGE = uFiles.loadImage(tx_player + 0 + face + mode + imgExt);
			this.SUBTYPE = 0;
		} else {
			this.IMAGE = uFiles.loadImage(tx_player + SUBTYPE + face + mode + imgExt);
			this.SUBTYPE = SUBTYPE;
		}
		this.imgH = this.IMAGE.getHeight(null);
		this.imgW = this.IMAGE.getWidth(null);
		this.entityRectangle.setBounds((int) this.x, (int) this.y + 10, imgW, 16);
		this.STATS.add(STAT_HEALTH, new StatHealth());
		this.STATS.add(STAT_ATTACK, new StatAttack());
		this.STATS.add(STAT_DEFENSE, new StatDefense());
		uCursor.maxInventoryCursorSize = 50 + STATS.size();
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
			entityRectangle.setBounds((int) x, (int) y + 10, 32, 22);
			for (int i = 0; i < mapLines; i++) {
				for (int j = 0; j < mapColumns; j++) {
					int k = (mapColumns * i) + j;
					if (mapBounds.get(k).equals("1")) {
						int x1 = j * 16;
						int x2 = j * 16 + 16;
						int y1 = i * 16;
						int y2 = i * 16 + 16;
						if (!DEBUG) {
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
		
		// Check for object boxes
		entityRectangle.setBounds((int) x, (int) y + 10, 32, 16);
		if(mapObjects != null) {
			for(MapObject mapObject: mapObjects) {
				// TOP
				if(dy > 0) {
					if(entityRectangle.intersects(
							mapObject.collisionRect.getX() + screenCorrection, 
							mapObject.collisionRect.getY(), 
							mapObject.collisionRect.getWidth() - screenCorrection, 1)) {
						setVerticalMovement(0);
						walking = false;
					}
				}
				// BOTTOM
				if(dy < 0) {
					if(entityRectangle.intersects(
							mapObject.collisionRect.getX() + screenCorrection, 
							mapObject.collisionRect.getMaxY(), 
							mapObject.collisionRect.getWidth() - screenCorrection, 1)) {
						setVerticalMovement(0);
						walking = false;
					}
				}
				// LEFT
				if(dx > 0) {
					if(entityRectangle.intersects(
							mapObject.collisionRect.getX(), 
							mapObject.collisionRect.getY() + screenCorrection, 
							1, mapObject.collisionRect.getHeight() -screenCorrection)) {
						setHorizontalMovement(0);
						walking = false;
					}
				}
				// RIGHT
				if(dx < 0) {
					if(entityRectangle.intersects(
							mapObject.collisionRect.getMaxX(), 
							mapObject.collisionRect.getY() + screenCorrection, 
							1, mapObject.collisionRect.getHeight() -screenCorrection)) {
						setHorizontalMovement(0);
						walking = false;
					}
				}
			}
		}
		
		for(Entity entity: ENTITIES) {
			// TOP
			if(dy > 0) {
				if(entityRectangle.intersects(
						entity.entityCollision.getX() + screenCorrection, 
						entity.entityCollision.getY(), 
						entity.entityCollision.getWidth() - screenCorrection, 1)) {
					setVerticalMovement(0);
					walking = false;
				}
			}
			// BOTTOM
			if(dy < 0) {
				if(entityRectangle.intersects(
						entity.entityCollision.getX() + screenCorrection, 
						entity.entityCollision.getMaxY(), 
						entity.entityCollision.getWidth() - screenCorrection, 1)) {
					setVerticalMovement(0);
					walking = false;
				}
			}
			// LEFT
			if(dx > 0) {
				if(entityRectangle.intersects(
						entity.entityCollision.getX(), 
						entity.entityCollision.getY() + screenCorrection, 
						1, entity.entityCollision.getHeight() -screenCorrection)) {
					setHorizontalMovement(0);
					walking = false;
				}
			}
			// RIGHT
			if(dx < 0) {
				if(entityRectangle.intersects(
						entity.entityCollision.getMaxX(), 
						entity.entityCollision.getY() + screenCorrection, 
						1, entity.entityCollision.getHeight() -screenCorrection)) {
					setHorizontalMovement(0);
					walking = false;
				}
			}
		}

		super.move(delta);
	}

	public void inflictDamage(double dMG) {
		uEffects.newEffect(player.getCenterX() - 8, player.getCenterY() - 8, uEffects.ef_SCRATCH);
		this.HEALTH -= dMG;
		if(HEALTH < 1) {
			uEffects.newEffect(player.getCenterX() - 8, player.getCenterY() - 8, uEffects.ef_SMOKE);
			MENU_TYPE = MENU_DIED;
			menuOpen = true;
		}
	}
	
	public void update() {
		player.updateAttackTimer();
		if(!player.inAttack) {
			player.animationWalk(tx_player, aDefInt);
		} else {
			player.animationWalk(tx_player + "a", aDefInt);
		}

		if (DEBUG) player.face = "0";
		player.movementCheck(gameLoopTime);

		if(spacePressed || slot1C || slot2V || slot3B || slot4N) {
			for(int i = 1; i < ENTITIES.size(); i++) {
				if(!ENTITIES.get(i).canInteract 
						&& player.canAttack()
						&& ENTITIES.get(i).HEALTH > 0 
						&& player.entityRectangle.intersects(ENTITIES.get(i).entityRectangle)) {
					ENTITIES.get(i).HEALTH -= player.DMG + (player.STATS.get(STAT_ATTACK).level / 25);
					ENTITIES.get(i).dmgTaken = 0 - player.DMG - (player.STATS.get(STAT_ATTACK).level / 25);
					int index = 0;
					if(slot4N) index = 3;
					if(slot3B) index = 2;
					if(slot2V) index = 1;
					if(slot1C || spacePressed) index = 0;
					if(player.getWeapon(index) != null) {
						
						ENTITIES.get(i).HEALTH -= player.getWeapon(index).DMG;
						ENTITIES.get(i).dmgTaken -= player.getWeapon(index).DMG;
					}
					ENTITIES.get(i).inAttack = true;
					player.inAttack = true;
					player.resetAttackTimer();
					if(ENTITIES.get(i).HEALTH > 0) uEffects.newEffect(ENTITIES.get(i).getCenterX(), ENTITIES.get(i).getCenterY(), uEffects.ef_SCRATCH);
				}
			}
		}
		
		player.updateHealthTimer();
		
		if(player.getAttackTimer() >= 5000 
				&& player.HEALTH < player.maxHealth
				&& player.healthCounter >= 2000 
				&& !player.inAttack) {
			player.HEALTH += 0.5d;
			player.healthCounter = 0;
		}

	}

}