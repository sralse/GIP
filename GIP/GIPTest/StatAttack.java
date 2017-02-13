package GIP.GIPTest;

public class StatAttack extends StatBase {
	public StatAttack() {
		this.level = 1;
		this.statName = menuLang[22];
		this.statType = STAT_ATTACK;
		this.icon = uFiles.loadImage(tx_icons + "attack.png");
		super.calcXPNeeded();
	}
}
