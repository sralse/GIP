package GIP.GIPTest;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

public class UtilsEffects extends Settings {

	private ArrayList<Effect> effects = new ArrayList<Effect>();
	private ArrayList<Image> effect0 = new ArrayList<Image>();
	private ArrayList<Image> effect1 = new ArrayList<Image>();
	private ArrayList<Image> effect2 = new ArrayList<Image>();
	private ArrayList<Image> effect3 = new ArrayList<Image>();
	private String effectPath = lvlDir + "effects/effect_";
	public final int effectBatch = 10;
	public final int ef_SMOKE = 0;
	public final int ef_FIRE = 1;
	public final int ef_SCRATCH = 2;
	public final int ef_SPARKS = 3;

	public void init() {
		Image tmp;

		// Smoke effect
		for(int subtype = 0; subtype < effectBatch + 1; subtype++) {
			tmp = uFiles.loadImage(effectPath + ef_SMOKE + subtype + imgExt);
			if(tmp == null) break;
			effect0.add(tmp);
		}
		// Fire effect
		for(int subtype = 0; subtype < effectBatch + 1; subtype++) {
			tmp = uFiles.loadImage(effectPath + ef_FIRE + subtype + imgExt);
			if(tmp == null) break;
			effect1.add(tmp);
		}
		// Scratch effect
		for(int subtype = 0; subtype < effectBatch + 1; subtype++) {
			tmp = uFiles.loadImage(effectPath + ef_SCRATCH + subtype + imgExt);
			if(tmp == null) break;
			effect2.add(tmp);
		}
		// Sparks effect
		for(int subtype = 0; subtype < effectBatch + 1; subtype++) {
			tmp = uFiles.loadImage(effectPath + ef_SPARKS + subtype + imgExt);
			if(tmp == null) break;
			effect3.add(tmp);
		}
		
		System.out.println("Size of effectSmoke: " + effect0.size());
		System.out.println("Size of effectFire: " + effect1.size());
		System.out.println("Size of effectScratch: " + effect2.size());
		System.out.println("Size of effectSparks: " + effect3.size());

	}

	public void newEffect(double x, double y, int effectType) {
		Effect effect = null;
		switch(effectType) {
		case ef_SMOKE:
			effect = new Effect((int) x,(int) y, ef_SMOKE, 1, 1000, effect0.size(), effects.size());
			break;
		case ef_FIRE:
			effect = new Effect((int) x,(int) y, ef_FIRE, 1, 1000, effect1.size(), effects.size());
			break;
		case ef_SCRATCH:
			effect = new Effect((int) x,(int) y, ef_SCRATCH, 1, 1000, effect2.size(), effects.size());
			break;
		case ef_SPARKS:
			effect = new Effect((int) x,(int) y, ef_SCRATCH, 1, 1000, effect3.size(), effect3.size());
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
					tmp = effect0;
					break;
				case ef_FIRE:
					tmp = effect1;
					break;
				case ef_SCRATCH:
					tmp = effect2;
					break;
				case ef_SPARKS:
					tmp = effect3;
					break;
				}
				int step = tmp.size() - (effect.TIMER / effect.INTERVAL) - 1;
				if(step < 0) step = 0;
				g.drawImage(tmp.get(step), effect.x, effect.y, null);
				effect.TIMER += gameLoopTime;
				effect.ID = index - 1;
				if(effect.TIMER >= effect.TIME) effects.remove(effect.ID);
			}
		}
	}

}
