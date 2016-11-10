package GIP.GIPTest;

class EntityNPC extends Entity{
	
	/**
	 * Create a new entity to represent the players ship
	 *  
	 * @param game The game in which the ship is being created
	 * @param ref The reference to the sprite to show for the ship
	 * @param x The initial x location of the player's ship
	 * @param y The initial y location of the player's ship
	 */
	public EntityNPC(int x, int y, int HEALTH, String NAME, int TYPE) {
		this.x = x * 16;
		this.y = y * 16;
		this.aX = this.x;
		this.aY = this.y;
		this.HEALTH = HEALTH;
		this.NAME = NAME;
		this.TYPE = TYPE;
		System.out.println(TYPE);
		if(this.TYPE < ENTITY_NPC.VILLAGER_MALE.value) {this.anchor = true;}
		this.ID = uID.newID(this.NAME);
		this.IMAGE = dynamics.loadImage(tx_npc + TYPE + face + mode + imgExt);
	}
	
}
