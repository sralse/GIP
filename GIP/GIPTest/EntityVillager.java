package GIP.GIPTest;

class EntityVillager extends Entity {

	private static int counter;
	private int villagerSpeed = (playerSpeed * 2) / 3;
	
	/**
	 * Create a new entity to represent the players ship
	 * 
	 * @param game
	 *            The game in which the ship is being created
	 * @param ref
	 *            The reference to the sprite to show for the ship
	 * @param x
	 *            The initial x location of the player's ship
	 * @param y
	 *            The initial y location of the player's ship
	 */
	public EntityVillager(int x, int y, int HEALTH, String NAME, int SUBTYPE) {
		this.TYPE = 1;
		this.x = x * 16;
		this.y = y * 16;
		this.aX = (int) this.x;
		this.aY = (int) this.y;
		this.nX = this.aX;
		this.nY = this.aY;
		this.HEALTH = HEALTH;
		this.NAME = NAME;
		this.SUBTYPE = SUBTYPE;
		if (this.SUBTYPE < ENTITY_NPC.VILLAGER_MALE.value) {
			this.anchor = true;
		}
		this.ID = uID.newID();
		System.out.println("TYPE: " + TYPE + " Name of TYPE: VILLAGER" + " SUBTYPE: " + SUBTYPE + " Display name: " + NAME);
		this.IMAGE = uFiles.loadImage(tx_npc + SUBTYPE + face + mode + imgExt);
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
				if(((x + adx <= aX + radius) && (x - adx >= aX - radius))) {
					nX = aX;
					nX += adx;
					if(DEBUG || INFO) System.out.println("MOVEMENT - Type: VILLAGER ID: " + ID 
							+ " Name: " + NAME 
							+ " New X: " + nX
							+ " Old X: " + x);
				}
				if(((y + ady <= aY + radius) && (y - ady >= aY - radius))) {
					nY = aY;
					nY += ady;
					if(DEBUG || INFO) System.out.println("MOVEMENT - Type: VILLAGER ID: " + ID 
							+ " Name: " + NAME 
							+ " New Y: " + nY
							+ " Old Y:" + y);
				}
			}
			counter = 0;
		}
		
		if(walking) counter = 0;
		
		if(interact) {
			int afstandTotSpelerX = (int) Math.abs(player.getX() - x);
			int afstandTotSpelerY = (int) Math.abs(player.getY() - y);
			
			if(afstandTotSpelerY > afstandTotSpelerX) {
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
		super.movementCheck(gameLoopTime, villagerSpeed);
		super.animationWalk(tx_npc);
	}

}
