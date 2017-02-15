package GIP.GIPTest;

/**
 * Create a new entity that represents a Gargoyle
 * @author Lars Carré
 */
public class EntityGargoyle extends Entity {
	
	private int counter;
	private int gargoyleSpeed = (playerSpeed * 3) / 2;

	public EntityGargoyle(int x, int y, double HEALTH, int SUBTYPE) {
		this.TYPE = "monster";
		this.SUBTYPE = ENTITY_MONSTER.GARGOYLE_NORMAL.value;
		this.DMG = 2.0d;
		this.NAME = "Gargoyle";
		this.canInteract = false;
		this.alwaysAnimated = true;
		this.xpReward = xpReward + HEALTH;
		super.init(x, y, HEALTH);
		
	}

	public void doLogic() {
		super.addToAttackTimer(gameLoopTime);
		counter += gameLoopTime;
		int adx = 0;
		int ady = 0;
		
		if(HEALTH <= 0) {
			uID.removeID(ID);
			uEffects.newEffect(super.getCenterX(), super.getCenterY(), uEffects.ef_FIRE);
			player.STATS.get(STAT_ATTACK).addXP(xpReward);
			player.STATS.get(STAT_HEALTH).addXP(xpReward / 10);
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
						face = "R";
					} else {
						adx -= rand;
						face = "L";
					}
				} else {
					int rand2 = randGen.nextInt(2);
					if(rand2 == 0) {
						ady += rand;
						face = "D";
					} else {
						ady -= rand;
						face = "U";
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
		
		super.movementCheck(gameLoopTime, gargoyleSpeed);
		super.animationWalk(tx_monster, 300);
	}
	
	
	
}
