package GIP.GIPTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.GlyphVector;
import java.util.StringTokenizer;

public class UtilsGUI extends Settings{
	// FPS Counter
	private long nextSecond = System.currentTimeMillis() + 1000;
	private int framesIncurrentSecond = 0;
	private int frameInLastSecond = 0;

	// UI Images
	private Image msgBox = uImages.scaleImageDetailed(uFiles.loadImage(uiImageDir + box1_brown), 450, 150);
	private Image healthBlock = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_Block), -3);
	private Image healthBlockL = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_BlockL), -3);
	private Image healthBlockR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_BlockR), -3);
	private Image healthBlockMO = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_BlockMO), -3);
	private Image healthBlockRO = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_BlockRO), -3);
	private Image healthBlockMR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_BlockMR), -3);
	private Image healthBlockRR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_BlockRR), -3);
	private Image healthHolder = uImages.scaleImageDetailed(uFiles.loadImage(uiImageDir + health_Holder), 130, 25);
	private Image healthCircleMG = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_CircleM), -3);
	private Image healthCircleLG = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_CircleL), -3);
	private Image healthCircleRG = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_CircleR), -3);
	private Image healthCircleMR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_CircleMR), -3);
	private Image healthCircleLR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_CircleLR), -3);
	private Image healthCircleRR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_CircleRR), -3);
	private Image itemBar = uFiles.loadImage(uiImageDir + gui_itemBar);
	private Image AttackBG = uFiles.loadImage(uiImageDir + "AttackT.png");

	int hBlockWFixed = healthCircleLR.getWidth(null);

	// User space info
	private int barHolderX = screenWidth - healthHolder.getWidth(null) - screenCorrection * 2;
	private int itemBarW = itemBar.getWidth(null);
	private int itemBarH = itemBar.getHeight(null);

	public void entityMessage(Entity ent, String msg) {
		StringTokenizer tok = new StringTokenizer(msg, " ");
		StringBuilder output = new StringBuilder(msg.length());
		int lineLen = 0;
		while (tok.hasMoreTokens()) {
			String word = tok.nextToken() + " ";

			if (lineLen + word.length() > msgBoxImgSpace * 2 + screenCorrection * 2) {
				output.append("::");
				lineLen = 0;
			}
			output.append(word);
			lineLen += word.length();
		}
		String[] msgTempString = (output.toString()).split("::");

		// Message box
		g.drawImage(msgBox, msgBoxX, msgBoxY, null);
		g.drawImage(ent.IMAGE, msgBoxX + msgBoxImgSpace, msgBoxY + msgBoxImgSpace, null);
		Font tempF = font_med_1.deriveFont(16.0f);
		for (int i = 0; i < msgTempString.length; i++) {
			msg = msgTempString[i];
			GlyphVector gv = tempF.createGlyphVector(frc, msg);
			gv = tempF.createGlyphVector(frc, msg);
			g.setColor(Color.black);
			g.drawGlyphVector(gv, 
					msgBoxX + msgBoxImgSpace * 3,
					msgBoxY + msgBoxImgSpace * 2 + msgBoxImgSpace * i + screenCorrection);
		}
	}

	public void update(Graphics2D g) {
		// init
		String s = "";
		Font tempF = font_med_1.deriveFont(20.0f);
		GlyphVector gv = tempF.createGlyphVector(frc, s);
		Entity ent;

		// Entity Health display
		for (int i = 1; i < ENTITIES.size(); i++) {
			ent = ENTITIES.get(i);
			double maxHealth = ENTITIES.get(i).maxHealth;
			// Entity health
			if(maxHealth > 0) {
				ent.healthCounter += gameLoopTime;
				double oH = ent.oldHealth;
				double nH = ent.HEALTH;
				int TIMER = ent.healthCounter;
				int DMG = (int) ent.dmgTaken;
				double hDif = nH - oH;

				if(hDif != 0) {
					ent.oldHealth = nH;
					ent.healthCounter = 0;
					TIMER = 0;
				}

				if(TIMER < (player.attackSpeedInterval - 300) && DMG != 0) {
					if(DMG < 0) {
						g.setColor(Color.red);
						s = "" + DMG;
					} else {
						g.setColor(Color.green);
						s = "" + DMG;
					}

					// TODO Attack animation
					g.drawImage(uItems.IMAGE_MELEE, 
							player.getCenterX() - 8, 
							(int) player.y - 32, null);

					Font tempF2 = font_2D_2.deriveFont(10.0f);
					gv = tempF2.createGlyphVector(frc, s);
					g.drawImage(AttackBG, 
							(int) ent.x + hBlockWFixed * 8 + screenCorrection - 2, 
							(int) ent.y - screenCorrection * 3 + 2, null);
					g.drawGlyphVector(
							gv, 
							(int) ent.x + hBlockWFixed * 9 + screenCorrection, 
							(int) ent.y);
				}

				if(TIMER > (player.attackSpeedInterval - 300) && DMG != 0) ENTITIES.get(i).dmgTaken = 0;

				// Red Health holder
				g.drawImage(healthCircleLR, 
						(int) ent.getCenterX() - hBlockWFixed * 6 + 1, 
						(int) ent.y - screenCorrection * 2 + 2, null);
				g.drawImage(healthCircleRR,
						(int) ent.getCenterX() + hBlockWFixed * 4 + 1, 
						(int) ent.y - screenCorrection * 2 + 2, null);
				for(int k = 1; k < 9; k++) {
					g.drawImage(healthCircleMR,
							(int) ENTITIES.get(i).getCenterX() - hBlockWFixed * 6 + hBlockWFixed * k + 1, 
							(int) ent.y - screenCorrection * 2 + 2, null);
				}

				// Begin of health
				g.drawImage(healthCircleRG,
						(int) ENTITIES.get(i).getCenterX() + hBlockWFixed * 4 + 1, 
						(int) ent.y - screenCorrection * 2 + 2, null);

				// Entity health
				double c = 8.0d;
				double factor = maxHealth / c;
				double hfactor = nH / factor;
				int beginH = (int) (ENTITIES.get(i).getCenterX() - hBlockWFixed * 6 + 1);

				if(hfactor > 1.0d) {
					for(int k = 1; k < hfactor + 1; k++) {
						// Blocks of health
						g.drawImage(healthCircleMG, 
								(int) (ENTITIES.get(i).getCenterX() + hBlockWFixed * 3 - hBlockWFixed * k + 1), 
								(int) (ent.y - screenCorrection * 2 + 2), null);
					}
				}

				if(nH == maxHealth) {
					// Full health
					g.drawImage(healthCircleLG,
							(int) beginH, 
							(int) ent.y - screenCorrection * 2 + 2, null);
				}
			}
		}

		// GUI Health text
		g.setColor(Color.black);
		s = menuLang[16];
		Font tempF2 = tempF.deriveFont(Font.BOLD, 18.0f);
		gv = tempF2.createGlyphVector(frc, s);
		g.drawGlyphVector(
				gv, 
				(float) (screenWidth - gv.getLogicalBounds().getWidth() - screenCorrection * 4), 
				screenCorrection * 7);
		
		// GUI Healthbar holder
		g.drawImage(healthHolder, barHolderX, screenCorrection * 9, null);

		// GUI Health blocks
		for(int i = 1; i < ((player.HEALTH + 9 )/ 10); i++) {
			if(player.HEALTH <= 30) {
				g.drawImage(healthBlockRR, 
						screenWidth - healthBlockRR.getWidth(null) - screenCorrection * 3, 
						screenCorrection * 10 + screenCorrection / 2, null);
				g.drawImage(healthBlockMR,  
						screenWidth - i * healthBlockMR.getWidth(null) - screenCorrection * 3, 
						screenCorrection * 10 + screenCorrection / 2, null);
			} else if(player.HEALTH > 30) {
				g.drawImage(healthBlockRO, 
						screenWidth - healthBlockRO.getWidth(null) - screenCorrection * 3, 
						screenCorrection * 10 + screenCorrection / 2, null);
				g.drawImage(healthBlockMO,  
						screenWidth - i * healthBlockMO.getWidth(null) - screenCorrection * 3, 
						screenCorrection * 10 + screenCorrection / 2, null);
			} 
			if(player.HEALTH >= 60) {
				g.drawImage(healthBlockR, 
						screenWidth - healthBlockR.getWidth(null) - screenCorrection * 3, 
						screenCorrection * 10 + screenCorrection / 2, null);
				g.drawImage(healthBlock,  
						screenWidth - i * healthBlockR.getWidth(null) - screenCorrection * 3, 
						screenCorrection * 10 + screenCorrection / 2, null);
			}
			if(player.HEALTH >= 100) {
				g.drawImage(healthBlockL, 
						barHolderX + screenCorrection + screenCorrection / 2, 
						screenCorrection * 10 + screenCorrection / 2, null);
			}
		}

		// GUI Health counter
		g.setColor(Color.black);
		double maxH = player.maxHealth;
		if(player.HEALTH < (maxH / 10) * 3.5d) g.setColor(Color.white);
		tempF2 = tempF.deriveFont(11.0f);
		s = "/" + String.valueOf((int) maxH);
		gv = tempF2.createGlyphVector(frc, s);
		double tmpX = gv.getLogicalBounds().getWidth();
		g.drawGlyphVector(
				gv, 
				(float) (screenWidth - tmpX - screenCorrection * 4), 
				screenCorrection * 13);
		s = String.valueOf((int) player.HEALTH);
		gv = tempF2.createGlyphVector(frc, s);
		g.drawGlyphVector(
				gv, 
				(float) (screenWidth - gv.getLogicalBounds().getWidth() - tmpX - screenCorrection * 4), 
				screenCorrection * 13);
		
		// GUI Item holder
		g.drawImage(itemBar, 
				(screenWidth / 2) - (itemBarW / 2), 
				screenHeight - itemBarH, 
				null);

		// TODO draw items
		Image tmp;
		tmp = uItems.getPlayerHotbarItem(0).IMAGE;
		if(tmp != null) {
			if(tmp.getWidth(null) != 32) tmp = uImages.scaleImageDetailed(tmp, 32, 32);
			g.drawImage(tmp, 
					(screenWidth / 2) - (itemBarW / 2) + 12, 
					screenHeight - itemBarH + 12, 
					null);
		}

		// Attack timer
		if(!player.canAttack()) {
			g.setColor(shadow);
			int interval = player.attackSpeedInterval;
			double factor = interval / 32;
			int quantity = (int) (player.getAttackTimer() / factor);
			
			g.fillRect(
					1 + (screenWidth / 2) - (itemBarW / 2) + 12,
					1 + screenHeight - itemBarH + 12 + quantity, 
					32, 
					32 - quantity);
		}

		// TODO draw GUI key (to press) for item holder

		
		
		// FPS Counter and X&Y coords
		if (DEBUG || INFO) {
			long currentTime = System.currentTimeMillis();
			if (currentTime > nextSecond) {
				nextSecond += 1000;
				frameInLastSecond = framesIncurrentSecond;
				framesIncurrentSecond = 0;
			}
			// Column 1
			framesIncurrentSecond++;
			g.setColor(Color.black);
			g.drawString("FPS: " + frameInLastSecond, 10, screenHeight - 60);
			g.drawString("XPos : " + (int) player.x, 10, screenHeight - 45);
			g.drawString("YPos : " + (int) player.y, 10, screenHeight - 30);
			g.drawString("Facing : " + player.face, 10, screenHeight - 15);
			// Column 2
			g.drawString("Direction X : " + player.dx, 100, screenHeight - 30);
			g.drawString("Direction Y : " + player.dy, 100, screenHeight - 15);

			g.drawString("Mode : " + player.mode, 100, screenHeight - 60);
			// Player ESP
			g.setColor(Color.pink);
			g.draw(player.entityRectangle);
			// Object ESP
			for (int i = 0; i < uObjects.objectImage.size(); i++) {
				g.setColor(Color.red);
				g.drawRect(uObjects.objectX.get(i), uObjects.objectY.get(i),
						uObjects.objectImage.get(i).getWidth(null), uObjects.objectImage.get(i).getHeight(null));
				g.setColor(Color.blue);
				g.draw(uObjects.objectRectangle.get(i));
			}
			// Entity ESP
			for (int i = 1; i < ENTITIES.size(); i++) {
				ent = ENTITIES.get(i);
				if(ent.inAttack) g.setColor(Color.red); else g.setColor(Color.orange);
				g.draw(ent.entityRectangle);

				// Virtual Pathfinding
				g.setColor(Color.blue);
				if(ent.inAttack && !ent.TYPE.contains("player")) {
					g.drawLine(ent.getCenterX(), ent.getCenterY(), 
							ent.nX, ent.nY);
				}
				if(ENTITIES.get(i).HEALTH > 0) {
					int x = ent.getCenterX();
					int y = ent.getCenterY();
					int atr = ent.attackingRadius;
					int fr = ent.attackFindingRadius;
					g.setColor(Color.red);
					g.drawOval(x - atr, y - atr, atr * 2, atr * 2);
					g.setColor(Color.blue);
					g.drawOval(x - fr, y - fr, fr * 2, fr * 2);
				}
			}
		}
	}
}
