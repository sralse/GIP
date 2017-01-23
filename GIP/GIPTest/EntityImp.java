package GIP.GIPTest;

/**
 * Create a new entity that represents an Imp
 * @author Lars Carré
 */
public class EntityImp extends Entity {
	
	private int counter;
	private int impSpeed = (playerSpeed * 3) / 2;

	public EntityImp(int x, int y, int HEALTH, int SUBTYPE) {
		this.x = x * 16;
		this.y = y * 16;
		this.nX = (int) this.x;
		this.nY = (int) this.y;
		this.anchor = true;
		this.HEALTH = HEALTH;
		this.TYPE = 2;
		this.SUBTYPE = SUBTYPE - 9;
		if(this.SUBTYPE == 0) {this.NAME = "Small Imp";} else {this.NAME = "Big Imp";}
		this.canInteract = false;
		this.IMAGE = uFiles.loadImage(tx_monster + this.SUBTYPE + face + mode + imgExt);
		System.out.println("TYPE: " + TYPE + " Name of TYPE: MONSTER" + " SUBTYPE: " + this.SUBTYPE + " Display name: " + NAME);
		if (IMAGE == null) {
			this.IMAGE = uFiles.loadImage(tx_player + 0 + face + mode + imgExt);
		}
		this.entityRectangle.setBounds((int) this.x, (int) this.y, IMAGE.getWidth(null), IMAGE.getHeight(null));
	}

	public void doLogic() {
		counter += gameLoopTime;
		int adx = 0;
		int ady = 0;
		
		if(HEALTH <= 0) {
			uID.removeID(ID);
			return;	
		}
		
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
				nX = aX;
				nX += adx;
				nY = aY;
				nY += ady;
				if(DEBUG || INFO) System.out.println("MOVEMENT - Type: VILLAGER ID: " + ID 
						+ " Name: " + NAME 
						+ " New X: " + nX
						+ " Old X: " + x);
				if(DEBUG || INFO) System.out.println("MOVEMENT - Type: VILLAGER ID: " + ID 
						+ " Name: " + NAME 
						+ " New Y: " + nY
						+ " Old Y:" + y);
			}
			
			counter = 0;
		}
		
		if(walking) counter = 0;
		
		if(interact) {
			int dpX = (int) Math.abs(player.getX() - x);
			int dpY = (int) Math.abs(player.getY() - y);
			
			if(dpY > dpX) {
				if(player.getY() - y < 0) {
					face = "U";
				} else {
					face = "D";
				}
			} else {
				if(player.getX() - x < 0) {
					face = "L";
				} else {
					face = "R";
				}
			}
		}
		
		super.movementCheck(gameLoopTime, impSpeed);
		super.animationWalk(tx_monster, 150);
	}
	
	
	
}
