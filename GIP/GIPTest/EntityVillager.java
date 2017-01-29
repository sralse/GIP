package GIP.GIPTest;

/**
 * Create a new entity that represents a villager
 * @author Lars Carré
 */
class EntityVillager extends Entity {

	private int counter;
	private int villagerSpeed = (playerSpeed * 2) / 3;

	public EntityVillager(int x, int y, double d, String NAME, int SUBTYPE) {
		this.x = x * 16;
		this.y = y * 16;
		this.aX = (int) this.x;
		this.aY = (int) this.y;
		this.nX = this.aX;
		this.nY = this.aY;
		this.HEALTH = d;
		this.oldHealth = d;
		this.maxHealth = d;
		this.NAME = NAME;
		this.TYPE = "villager";
		this.SUBTYPE = SUBTYPE;
		this.canInteract = true;
		if (this.SUBTYPE < ENTITY_VILLAGER.VILLAGER_MALE.value) {
			this.anchor = true;
		}
		this.IMAGE = uFiles.loadImage(tx_villager + SUBTYPE + face + mode + imgExt);
		this.imgH = this.IMAGE.getHeight(null);
		this.imgW = this.IMAGE.getWidth(null);
		this.entityRectangle.setBounds((int) this.x, (int) this.y, imgW, imgH);
	}

	public void doLogic() {
		counter += gameLoopTime;
		int adx = 0;
		int ady = 0;
		if((counter > 1000) && !(walking)) {
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
				
				// Test if points are in square of the anchor point
				if(anchor) {
					if(((x + adx <= aX + radius) && (x - adx >= aX - radius))) {
						nX = aX;
						nX += adx;
						if(DEBUG || INFO) System.out.println("MOVEMENT - Type: " + TYPE + " ID: " + ID 
								+ " Name: " + NAME 
								+ " New X: " + nX
								+ " Old X: " + x);
					}
					if(((y + ady <= aY + radius) && (y - ady >= aY - radius))) {
						nY = aY;
						nY += ady;
						if(DEBUG || INFO) System.out.println("MOVEMENT - Type: " + TYPE + " ID: " + ID 
								+ " Name: " + NAME 
								+ " New Y: " + nY
								+ " Old Y:" + y);
					}
				} else {
					nX = aX;
					nX += adx;
					nY = aY;
					nY += ady;
					if(DEBUG || INFO) System.out.println("MOVEMENT - Type: " + TYPE + " ID: " + ID 
							+ " Name: " + NAME 
							+ " New X: " + nX
							+ " Old X: " + x);
					if(DEBUG || INFO) System.out.println("MOVEMENT - Type: " + TYPE + " ID: " + ID 
							+ " Name: " + NAME 
							+ " New Y: " + nY
							+ " Old Y:" + y);
				}
			}
			
			counter = 0;
		}
		
		if(walking) counter = 0;
		
		if(interact) {
			int dpX = (int) Math.abs(player.x - x);
			int dpY = (int) Math.abs(player.y - y);
			
			if(dpY > dpX) {
				if(player.y - y < 0) {
					face = "U";
				} else {
					face = "D";
				}
			} else {
				if(player.x - x < 0) {
					face = "L";
				} else {
					face = "R";
				}
			}
		}
		super.movementCheck(gameLoopTime, villagerSpeed);
		super.animationWalk(tx_villager, aDefInt);
	}

}
