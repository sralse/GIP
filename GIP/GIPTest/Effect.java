package GIP.GIPTest;

public class Effect extends Settings {

	protected int x;
	protected int y;
	protected int INTERVAL;
	protected int STAGES;
	protected int TIMER;
	protected int TIME;
	protected int TYPE;
	protected int ID;
	
	/**
	 * When initialising an effect it is important to know it's params:
	 * @param x The x location where the Effect takes place (raw double)
	 * @param y The y location where the Effect takes place (raw double)
	 * @param effect_TYPE This is the type of Effect (used for extra info)
	 * @param repeats The amount of repeats that the effect has to do given the amount of time
	 * @param maxTimeAlive The amount of time this effect should last or 'stay alive'
	 * @param interval The interval of each image during an effect
	 * @param effectID The uID of the effect
	 * */
	public Effect(int x, int y, int effect_TYPE, int repeats, int maxTimeAlive, int interval, int effectID) {
		this.x = x;
		this.y = y;
		this.TYPE = effect_TYPE;
		this.TIMER = 0;
		this.TIME = maxTimeAlive * repeats;
		this.INTERVAL = maxTimeAlive / interval;
		this.ID = effectID;
	}
}
