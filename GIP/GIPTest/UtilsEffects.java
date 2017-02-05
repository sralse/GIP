package GIP.GIPTest;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

public class UtilsEffects extends Settings {

	private ArrayList<Effect> effects = new ArrayList<Effect>();
	private ArrayList<Image> effectSmoke = new ArrayList<Image>();
	private ArrayList<Image> effectFire = new ArrayList<Image>();
	private String effectPath = lvlDir + "effects/effect_";
	public final int ef_SMOKE = 0;
	public final int ef_FIRE = 1;

	public void init() {
		Image tmp;

		// Smoke effect
		for(int subtype = 0; subtype < 10; subtype++) {
			tmp = uFiles.loadImage(effectPath + "0" + subtype + imgExt);
			if(tmp == null) break;
			effectSmoke.add(tmp);
		}
		// Fire effect
		for(int subtype = 0; subtype < 10; subtype++) {
			tmp = uFiles.loadImage(effectPath + "1" + subtype + imgExt);
			if(tmp == null) break;
			effectFire.add(tmp);
		}
		
		System.out.println("Size of effectSmoke: " + effectSmoke.size());
		System.out.println("Size of effectFire: " + effectFire.size());

	}

	public void newEffect(double x, double y, int effectType) {
		Effect effect = null;
		switch(effectType) {
		case ef_SMOKE:
			effect = new Effect((int) x,(int) y, ef_SMOKE, 1, 1000, effectSmoke.size(), effects.size());
			break;
		case ef_FIRE:
			effect = new Effect((int) x,(int) y, ef_FIRE, 1, 1000, effectFire.size(), effects.size());
			break;
		}
		
		if(DEBUG || INFO) System.out.println("Effect added TYPE: " + effect.TYPE + " Time: " + effect.TIME);

		effects.add(effect);
	}

	public void update(Graphics2D g) {
		if(effects.size() != 0) {
			for(int index = effects.size(); index > 0; index--) {
				Effect effect = effects.get(index - 1);
				ArrayList<Image> tmp = new ArrayList<Image>();
				switch(effect.TYPE) {
				case ef_SMOKE:
					tmp = effectSmoke;
					break;
				case ef_FIRE:
					tmp = effectFire;
					break;
				}
				int step = tmp.size() - (effect.TIMER / effect.INTERVAL) - 1;
				if(step < 0) step = 0;
				g.drawImage(tmp.get(step), effect.x, effect.y, null);
				effect.TIMER += gameLoopTime;
				if(effect.TIMER >= effect.TIME) effects.remove(effect.ID);
			}
		}
	}

}
