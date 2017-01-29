package GIP.GIPTest;

/**
 * Create a new entity that represents an Imp
 * @author Lars Carré
 */
public class EntityImp extends Entity {
	
	private int counter;
	private int impSpeed = (playerSpeed * 3) / 2;

	public EntityImp(int x, int y, double HEALTH, int SUBTYPE) {
		this.x = x * 16;
		this.y = y * 16;
		this.nX = (int) this.x;
		this.nY = (int) this.y;
		this.HEALTH = HEALTH;
		this.oldHealth = HEALTH;
		this.maxHealth = HEALTH;
		this.DMG = 1.0d;
		this.TYPE = "monster";
		this.SUBTYPE = ENTITY_MONSTER.IMP_SMALL.value;
		this.NAME = "Small Imp";
		this.canInteract = false;
		this.IMAGE = uFiles.loadImage(tx_monster + this.SUBTYPE + face + mode + imgExt);
		if (IMAGE == null) {
			this.IMAGE = debug;
		}
		this.imgH = this.IMAGE.getHeight(null);
		this.imgW = this.IMAGE.getWidth(null);
		this.entityRectangle.setBounds((int) this.x, (int) this.y, imgW, imgH);
	}

	public void doLogic() {
		super.addToAttackTimer(gameLoopTime);
		counter += gameLoopTime;
		int adx = 0;
		int ady = 0;
		
		if(HEALTH <= 0) {
			uID.removeID(ID);
			return;	
		}
		
		if((counter > 1000) && !(walking) && !(inAttack)) {
			int chanse = randGen.nextInt(20);
			if(chanse == 0) {
				int rand = randGen.nextInt(radius);
				int DIRECTION = randGen.nextInt(2);
				if(DIRECTION == 0) {
					int rand1 = randGen.nextInt(2);
					if(rand1 == 0) {
						adx += rand;
						face = "L";
					} else {
						adx -= rand;
						face = "R";
					}
				} else {
					int rand2 = randGen.nextInt(2);
					if(rand2 == 0) {
						ady += rand;
						face = "U";
					} else {
						ady -= rand;
						face = "D";
					}
				}
				nX = (int) x;
				nY = (int) y;
				// Prevent sticky edges
				if(aX + adx > 100 && aX + adx < screenWidth - 100) {nX += adx;} else {nX -= adx;}
				if(aY + ady > 100 && aY + ady < screenWidth - 100) {nY += ady;} else {nY -= ady;}
				
				if(DEBUG || INFO) System.out.println("MOVEMENT - Type: " + TYPE + " ID: " + ID 
						+ " Name: " + NAME 
						+ " New X: " + nX
						+ " Old X: " + x);
				if(DEBUG || INFO) System.out.println("MOVEMENT - Type: " + TYPE + " ID: " + ID 
						+ " Name: " + NAME 
						+ " New Y: " + nY
						+ " Old Y:" + y);
			}
			
			counter = 0;
		}
		
		if(walking) counter = 0;
		
		if(inAttack) {
			int cX = super.getCenterX();
			int cY = super.getCenterY();
			int dpX = (int) Math.abs(player.getCenterX() - cX);
			int dpY = (int) Math.abs(player.getCenterY() - cY);
			
			if(dpY > dpX) {
				if(player.getCenterY() - cY < 0) {
					face = "U";
				} else {
					face = "D";
				}
			} else {
				if(player.getCenterX() - cX < 0) {
					face = "L";
				} else {
					face = "R";
				}
			}
			
			if(oldFace != face) counter = 0;
			
			int dtp = (int) Math.sqrt((dpX*dpX) + (dpY*dpY));
			// Update Attack AI
			if(dtp < attackFindingRadius) {
				if(dtp < attackingRadius && super.canAttack()) {
					player.inflictDamage(DMG);
					super.resetAttackTimer();
				}
			} else {
				super.updateAttackTimer();
			}
			
			// set targets
			super.setTarget(player);
		} else {
			super.setTarget(null);
		}
		
		super.movementCheck(gameLoopTime, impSpeed);
		super.animationWalk(tx_monster, 150);
	}
	
	
	
}
