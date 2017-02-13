package GIP.GIPTest;

public class StatHealth extends StatBase {
	public StatHealth() {
		this.level = 1;
		this.statName = menuLang[16];
		this.statType = STAT_HEALTH;
		this.icon = uFiles.loadImage(tx_icons + "health.png");
		super.calcXPNeeded();
	}
}
