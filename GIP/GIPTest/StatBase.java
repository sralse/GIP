package GIP.GIPTest;

import java.awt.Image;

public class StatBase extends Settings{
	protected int level = 0;
	protected int statType;
	private double xpTotal = 0;
	private double xpNeeded = 1;
	protected String statName;
	protected Image icon;
	
	public void calcXPNeeded() {
		for(int i = 0; i < level + 1; i++) {
			this.xpNeeded += (Math.pow(5 * (i+1), 3) - Math.pow(25 * (i+1), 2) + 100 * (i+1) + 2000) / 10;
		}
		System.out.println("XP total needed for next level(" + level + "): " + statName + " = " + xpNeeded);
	}
	
	public void updateStat() {
		if(xpTotal >= xpNeeded) {
			level += 1;
			calcXPNeeded();
		}
	}
	
	public void addXP(double amount) {
		this.xpTotal += amount;
		System.out.println(statName + " XP Added: " + amount + " XP total: " + xpTotal);
		updateStat();
	}
	
	public int getLevel() {
		return level;
	}
	
	public double getXPTotal() {
		return xpTotal;
	}
	
	public double getXPNeeded() {
		return xpNeeded;
	}
	
	public double getXPRemainder() {
		return xpNeeded - xpTotal;
	}
	
	public String getLevelString() {
		return String.valueOf(level);
	}
	
	public String getNextLevelString() {
		return String.valueOf(level + 1);
	}
	
	/**
	 * <p>This method returns the amount of xp in a 'shortened' way</p>
	 * Example:
	 * <ul>
	 * <li>	2200 	will return 2.2k</li>
	 * <li>	1300500	will return 1.3M</li>
	 * </ul>
	 * This method has double precision if the xp amount is > 999.
	 * @return String <b>xpTotal</b> (shortened)
	 * */
	public String getXPShort() {
		if(xpTotal < 1000) return String.valueOf((int) xpTotal);
		if(xpTotal < 1000000) return String.valueOf((double) xpTotal / 1000.0d) + "K";
		return String.valueOf((double) xpTotal / 1000000.0d) + "M";
	}
	
	/**
	 * <p>This method returns the amount of xp needed in a 'shortened' way</p>
	 * Example:
	 * <ul>
	 * <li>	2200 	will return 2.2k</li>
	 * <li>	1300500	will return 1.3M</li>
	 * </ul>
	 * This method has double precision if the xp amount is > 999.
	 * @return String <b>xpNeeded</b> (shortened)
	 * */
	public String getXPNeededShort() {
		if(xpNeeded < 1000) return String.valueOf((int) xpNeeded);
		if(xpNeeded < 1000000) return String.valueOf((double) xpNeeded / 1000.0d) + "K";
		return String.valueOf((double) xpNeeded / 1000000.0d) + "M";
	}
	
	/**
	 * <p>This method returns the amount of xp that remains to level up in a 'shortened' way</p>
	 * Example:
	 * <ul>
	 * <li>	2200 	will return 2.2k</li>
	 * <li>	1300500	will return 1.3M</li>
	 * </ul>
	 * This method has double precision if the xp remainder is > 999.
	 * @return String <b>xpNeeded</b> (shortened)
	 * */
	public String getXPRemainderShort() {
		if(xpNeeded - xpTotal < 1000) return String.valueOf((int) (xpNeeded  - xpTotal));
		if(xpNeeded - xpTotal < 1000000) return String.valueOf((double) ((xpNeeded - xpTotal) / 1000.0d)) + "K";
		return String.valueOf((double) ((xpNeeded - xpTotal) / 1000000.0d)) + "M";
	}
}
