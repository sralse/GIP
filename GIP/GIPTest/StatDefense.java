package GIP.GIPTest;

public class StatDefense extends StatBase{
	public StatDefense() {
		this.level = 1;
		this.statName = menuLang[19];
		this.statType = STAT_DEFENSE;
		this.icon = uFiles.loadImage(tx_icons + "defense.png");
		super.calcXPNeeded();
	}
}
