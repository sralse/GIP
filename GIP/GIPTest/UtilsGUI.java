package GIP.GIPTest;

import java.awt.AlphaComposite;
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

	// MSG Image
	private Image msgBox = uImages.scaleImageLinear(uFiles.loadImage(uiImageDir + box1_brown), 450, 150);
	// Health Images
	private Image healthBlock = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + "barGreen_horizontalMid2.png"), -3);
	private Image healthBlockL = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + "barGreen_horizontalLeft2.png"), -3);
	private Image healthBlockR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + "barGreen_horizontalRight2.png"), -3);
	private Image healthBlockMO = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + "barOrange_horizontalMid2.png"), -3);
	private Image healthBlockRO = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + "barOrange_horizontalRight2.png"), -3);
	private Image healthBlockMR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + "barRed_horizontalMid2.png"), -3);
	private Image healthBlockRR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + "barOrange_horizontalRight2.png"), -3);
	private Image healthHolder = uImages.scaleImageLinear(uFiles.loadImage(uiImageDir + "barHolder_00.png"), 130, 25);
	private Image healthCircleMG = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + "barGreen_horizontalMid.png"), -3);
	private Image healthCircleLG = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + "barGreen_horizontalLeft.png"), -3);
	private Image healthCircleRG = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + "barGreen_horizontalRight.png"), -3);
	private Image healthCircleMR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + "barRed_horizontalMid.png"), -3);
	private Image healthCircleLR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + "barRed_horizontalLeft.png"), -3);
	private Image healthCircleRR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + "barRed_horizontalRight.png"), -3);
	// Item Image
	private Image itemBar = uFiles.loadImage(uiImageDir + gui_itemBar);
	// Attack Image
	private Image AttackBG = uFiles.loadImage(uiImageDir + "AttackT.png");
	// Menu images
	private Image menu_inv_bg = uFiles.loadImage(uiImageDir + "inventory/inv_bg.png");
	private Image menu_toolbox = uFiles.loadImage(uiImageDir + "inventory/box40.png");
	private Image menu_toolboxSelected = uFiles.loadImage(uiImageDir + "inventory/box40_active.png");
	private Image menu_toolboxBig = uFiles.loadImage(uiImageDir + "inventory/box80.png");
	private Image menu_sidebar_bg = uFiles.loadImage(uiImageDir + "inventory/sidebar_bg.png");
	private Image menu_sidebar_frame = uFiles.loadImage(uiImageDir + "inventory/sidebar_edge.png");
	private Image menu_sidebar_frameSelected = uFiles.loadImage(uiImageDir + "inventory/sidebar_edgeActive.png");
	private Image menu_sidebar_stat = uFiles.loadImage(uiImageDir + "inventory/sidebar_scale.png");
	// Icons
	private Image icon_ghostWeapon = uFiles.loadImage(uiImageDir + "inventory/icons/ghost_sword.png");
	private Image icon_ghostChestplate = uFiles.loadImage(uiImageDir + "inventory/icons/ghost_body.png");
	private Image icon_ghostPants = uFiles.loadImage(uiImageDir + "inventory/icons/ghost_pants.png");
	private Image icon_ghostShield = uFiles.loadImage(uiImageDir + "inventory/icons/ghost_shield.png");
	private Image icon_ghostHelmet = uFiles.loadImage(uiImageDir + "inventory/icons/ghost_helmet.png");
	private Image icon_ghostBoots = uFiles.loadImage(uiImageDir + "inventory/icons/ghost_boots.png");
	
	// Measurement
	private final int hBlockWFixed = healthCircleLR.getWidth(null);
	private final int barHolderX = screenWidth - healthHolder.getWidth(null) - screenCorrection * 2;
	private final int itemBarW = itemBar.getWidth(null);
	private final int itemBarH = itemBar.getHeight(null);
	private float fade = 0.0f;
	private Image inv_playerImg;

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

					// TODO Fix Attack animation
					g.drawImage(uItems.IMAGE_MELEE, 
							player.getCenterX() - 8, 
							(int) player.y - 32, null);

					Font tempF2 = font_2D_2.deriveFont(10.0f);
					gv = tempF2.createGlyphVector(frc, s);
					g.drawImage(AttackBG, 
							(int) ent.x + hBlockWFixed * 8 + screenCorrection + 1, 
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

		// Draw items
		g.drawImage(player.getItemImage(0), 
				(screenWidth / 2) - (itemBarW / 2) + 13, 
				screenHeight - itemBarH + 12, 
				null);

		for(int i = 1; i < 4; i++) {
			g.drawImage(player.getItemImage(i), 
					(screenWidth / 2) - (itemBarW / 2) + 17 + 56 * i, 
					screenHeight - itemBarH + 12, 
					null);
		}

		// Attack timer
		if(!player.canAttack()) {
			g.setColor(shadow);
			int interval = player.attackSpeedInterval;
			double factor = interval / 32;
			int quantity = (int) (player.getAttackTimer() / factor);

			// First slot
			g.fillRect(
					1 + (screenWidth / 2) - (itemBarW / 2) + 12,
					1 + screenHeight - itemBarH + 12 + quantity, 
					32, 
					32 - quantity);

			// Second slot
			g.fillRect(
					1 + (screenWidth / 2) - (itemBarW / 2) + 71,
					1 + screenHeight - itemBarH + 12 + quantity, 
					32, 
					32 - quantity);

			// Third slot
			g.fillRect(
					1 + (screenWidth / 2) - (itemBarW / 2) + 127,
					1 + screenHeight - itemBarH + 12 + quantity, 
					32, 
					32 - quantity);

			// Fourth slot
			g.fillRect(
					1 + (screenWidth / 2) - (itemBarW / 2) + 183,
					1 + screenHeight - itemBarH + 12 + quantity, 
					32, 
					32 - quantity);
		}

		// GUI Keys
		g.setColor(Color.WHITE);
		tempF2 = tempF.deriveFont(Font.BOLD, 13.0f);
		s = "C"; // C key - first slot
		gv = tempF2.createGlyphVector(frc, s);
		g.drawGlyphVector(
				gv, 
				560,
				1 + screenHeight - 24);
		s = "V"; // V key - second slot
		gv = tempF2.createGlyphVector(frc, s);
		g.drawGlyphVector(
				gv, 
				616,
				1 + screenHeight - 24);
		s = "B"; // B key - third slot
		gv = tempF2.createGlyphVector(frc, s);
		g.drawGlyphVector(
				gv, 
				676,
				1 + screenHeight - 24);
		s = "N"; // N key - fourth slot
		gv = tempF2.createGlyphVector(frc, s);
		g.drawGlyphVector(
				gv, 
				730,
				1 + screenHeight - 24);

		// Menu's + logic
		if(invMenuKey && menuCooldown > 500) {
			menuCooldown = 0;
			MENU_TYPE = MENU_INVENTORY;
			if(menuOpen) {menuOpen = false;
			} else {
				menuOpen = true;
				inv_playerImg = uImages.scaleImageNearest(uFiles.loadImage(tx_player + player.SUBTYPE + "D0" + imgExt), 64, 64);
			}
		}
		if(menuCooldown <= 500) menuCooldown += gameLoopTime;
		if(menuOpen) displayMenu(g);
		
		// Debug info
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

	public void displayMenu(Graphics2D g) {
		String s = "";
		Font tempF = font_2D_2.deriveFont(20.0f);
		GlyphVector gv = tempF.createGlyphVector(frc, s);
		switch(MENU_TYPE) {
		case(MENU_INVENTORY):
			// TODO draw items
			uCursor.update();
			// Inventory
			g.drawImage(menu_inv_bg, 200, 200, null);
			g.setColor(menuHeaderColor);
			Font tempF2 = font_2D_2.deriveFont(28.0f);
			s = player.NAME;
			gv = tempF2.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, (float) (325 - (gv.getLogicalBounds().getWidth() / 2)), 240);
			tempF2 = font_2D_2.deriveFont(32.0f);
			s = menuLang[17];
			gv = tempF2.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, (float) (625 - (gv.getLogicalBounds().getWidth() / 2)), 240);
			
			// Items
			for(int i = 0; i < 7; i++) {
				for(int j = 0; j < 6; j++) {
					g.drawImage(menu_toolbox, 481 + i * 40, 285 + j * 40, null);
					if(i + j * 7 == inventoryCursor - 9) g.drawImage(menu_toolboxSelected, 481 + i * 40, 285 + j * 40, null);
					if(player.getInvItem((i + j * 7) + 9) != null) 
						g.drawImage(player.getItemImage((i + j * 7) + 9), 485 + i * 40, 289 + j * 40, null);
					if(uCursor.hasSelected && i + j * 7 == inventoryCursor - 9) {
						g.drawImage(uCursor.selectedItem.IMAGE, 485 + i * 40, 289 + j * 40, null);
						Color oldColor = g.getColor();
						g.setColor(transWhite);
						g.fillRect(481 + i * 40, 285 + j * 40, 40, 40);
						g.setColor(oldColor);
					}
				}
			}
			
			// Player
			g.drawImage(menu_toolboxBig, 230, 280, null);
			g.drawImage(inv_playerImg , 240, 288, null);
			// Inventory text (player info)
			g.setColor(menuTextColor);
			tempF2 = font_2D_2.deriveFont(18.0f);
			s = menuLang[20] + ":";
			gv = tempF2.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, (float) (270 - (gv.getLogicalBounds().getWidth() / 2)), 375);
			s = menuLang[19] + ":";
			gv = tempF2.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, (float) (375 - (gv.getLogicalBounds().getWidth() / 2)), 330);
			s = menuLang[18] + ":";
			gv = tempF2.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, (float) (375 - (gv.getLogicalBounds().getWidth() / 2)), 295);
			
			// Armor
			g.drawImage(menu_toolbox, 320, 360, null); // Helmet
			g.drawImage(menu_toolbox, 320, 404, null); // Chestplate
			g.drawImage(menu_toolbox, 320, 448, null); // Pants
			g.drawImage(menu_toolbox, 320, 492, null); // Boots
			g.drawImage(menu_toolbox, 364, 404, null); // Shield
			g.drawImage(menu_toolbox, 276, 404, null); // Sword
			if(inventoryCursor == 0) g.drawImage(menu_toolboxSelected, 276, 404, null); // Sword
			if(inventoryCursor == 4) g.drawImage(menu_toolboxSelected, 320, 360, null); // Helmet
			if(inventoryCursor == 5) g.drawImage(menu_toolboxSelected, 320, 404, null); // Chestplate
			if(inventoryCursor == 6) g.drawImage(menu_toolboxSelected, 320, 448, null); // Pants
			if(inventoryCursor == 7) g.drawImage(menu_toolboxSelected, 320, 492, null); // Boots
			if(inventoryCursor == 8) g.drawImage(menu_toolboxSelected, 364, 404, null); // Shield
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.2f));
			if(player.hasNoItemInSlot(0)) g.drawImage(icon_ghostWeapon, 280, 408, null); // Sword
			if(player.hasNoItemInSlot(4)) g.drawImage(icon_ghostHelmet, 324, 364, null); // Helmet
			if(player.hasNoItemInSlot(5)) g.drawImage(icon_ghostChestplate, 324, 408, null); // Chestplate
			if(player.hasNoItemInSlot(6)) g.drawImage(icon_ghostPants, 324, 452, null); // Pants
			if(player.hasNoItemInSlot(7)) g.drawImage(icon_ghostBoots, 324, 498, null); // Boots
			if(player.hasNoItemInSlot(8)) g.drawImage(icon_ghostShield, 368, 408, null); // Shield
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));	
			g.drawImage(player.getItemImage(0), 280, 408, null); // Sword
			// Weapons
			tempF2 = font_2D_2.deriveFont(14.0f);
			g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.5f));
			s = "V"; // V key - second slot
			g.drawImage(menu_toolbox, 232, 404, null);
			if(inventoryCursor == 1) g.drawImage(menu_toolboxSelected, 232, 404, null); // V key - Second slot
			g.drawImage(player.getItemImage(1), 236, 408, null);
			gv = tempF2.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, 260, 440);
			s = "B"; // B key - third slot
			g.drawImage(menu_toolbox, 232, 448, null);
			if(inventoryCursor == 2) g.drawImage(menu_toolboxSelected, 232, 448, null); // B key - third slot
			g.drawImage(player.getItemImage(2), 236, 452, null);
			gv = tempF2.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, 260, 484);
			s = "N"; // N key - fourth slot
			g.drawImage(menu_toolbox, 232, 492, null);
			if(inventoryCursor == 3) g.drawImage(menu_toolboxSelected, 232, 492, null); // N key - fourth slot
			g.drawImage(player.getItemImage(3), 236, 496, null);
			gv = tempF2.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, 260, 528);
			
			// Sidebar
			// TODO Draw stats info
			g.setColor(menuHeaderColor);
			tempF2 = font_2D_2.deriveFont(22.0f);
			g.drawImage(menu_sidebar_bg, screenWidth - 200 - screenCorrection * 2, 0, null);
			s = menuLang[21];
			gv = tempF2.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, (float) (screenWidth - 100 - (gv.getLogicalBounds().getWidth()) / 2), 20);
			s = player.NAME;
			tempF2 = font_2D_2.deriveFont(17.0f);
			gv = tempF2.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, (float) (screenWidth - 100 - (gv.getLogicalBounds().getWidth()) / 2), 35);
			g.setColor(menuTextColor);
			boolean active = false;
			for(int i = 0; i < player.STATS.size(); i++) {
				int j;
				s = player.STATS.get(i).getLevelString();
				if(player.STATS.get(i).statType + 51 == inventoryCursor) {
					g.drawImage(menu_sidebar_frameSelected, 
							screenWidth - 200 - screenCorrection, 
							screenCorrection + 50 * (i + 1), null);
					g.drawImage(menu_toolbox, 
							screenWidth - 200 + screenCorrection,
							50 * (i + 1) + 10, null);
					g.drawImage(player.STATS.get(i).icon, 
							screenWidth - 200 + screenCorrection * 2, 
							50 * (i + 1) + screenCorrection + 10, null);
					g.drawImage(menu_sidebar_stat, 
							screenWidth - 100, 
							50 * (i + 1) + screenCorrection * 5, null);
					Color oldColor = g.getColor();
					g.setColor(Color.green);
					g.fillRect(screenWidth - 99, 50 * (i + 1) + screenCorrection * 6, 
							(int) ((player.STATS.get(i).getXPTotal() / player.STATS.get(i).getXPNeeded()) * 80), 12);
					g.setColor(oldColor);
					
					// Stat level
					gv = tempF2.createGlyphVector(frc, s);
					g.drawGlyphVector(gv, 
							(float) (screenWidth - 130 - (gv.getLogicalBounds().getWidth()) / 2), 
							50 * (i + 1) + screenCorrection * 8);
					
					// Stat name (first line)
					int firstLine = screenCorrection * 17;
					s = player.STATS.get(i).statName;
					gv = tempF2.createGlyphVector(frc, s);
					g.drawGlyphVector(gv, 
							screenWidth - 200 + screenCorrection, 
							50 * (i + 1) + firstLine);
					s = "TOT. :";
					gv = tempF2.createGlyphVector(frc, s);
					g.drawGlyphVector(gv, 
							screenWidth - 115, 
							50 * (i + 1) + firstLine);
					g.setColor(Color.white);
					s = player.STATS.get(i).getXPShort();
					gv = tempF2.createGlyphVector(frc, s);
					g.drawGlyphVector(gv, 
							screenWidth - 65, 
							50 * (i + 1) + firstLine);
					g.setColor(menuTextColor);
					s = "XP";
					gv = tempF2.createGlyphVector(frc, s);
					g.drawGlyphVector(gv, 
							screenWidth - 35, 
							50 * (i + 1) + firstLine);
					
					// XP Needed (bottom Line)
					int bottomLine = screenCorrection * 22;
					s = "XP:";
					gv = tempF2.createGlyphVector(frc, s);
					g.drawGlyphVector(gv, 
							screenWidth - 200 + screenCorrection, 
							50 * (i + 1) + bottomLine);
					g.setColor(Color.red);
					s = player.STATS.get(i).getXPRemainderShort();
					gv = tempF2.createGlyphVector(frc, s);
					g.drawGlyphVector(gv, 
							screenWidth - 165, 
							50 * (i + 1) + bottomLine);
					g.setColor(menuTextColor);
					s = ">";
					gv = tempF2.createGlyphVector(frc, s);
					g.drawGlyphVector(gv, 
							screenWidth - 130, 
							50 * (i + 1) + bottomLine);
					g.setColor(Color.white);
					s = "LVL" + player.STATS.get(i).getNextLevelString() + ":";
					gv = tempF2.createGlyphVector(frc, s);
					g.drawGlyphVector(gv, 
							screenWidth - 115, 
							50 * (i + 1) + bottomLine);
					g.setColor(Color.green);
					s = player.STATS.get(i).getXPNeededShort();
					gv = tempF2.createGlyphVector(frc, s);
					g.drawGlyphVector(gv, 
							screenWidth - 65, 
							50 * (i + 1) + bottomLine);
					g.setColor(menuTextColor);
					s = "XP";
					gv = tempF2.createGlyphVector(frc, s);
					g.drawGlyphVector(gv, 
							screenWidth - 35, 
							50 * (i + 1) + bottomLine);
					
					active = true;
				
				} else {
					
					if(active) j = 50; else j = 0;
					s = player.STATS.get(i).getLevelString();
					g.drawImage(menu_sidebar_frame, 
							screenWidth - 200 - screenCorrection, 
							screenCorrection + 50 * (i + 1) + j, null);
					g.drawImage(menu_toolbox, 
							screenWidth - 200 + screenCorrection,
							50 * (i + 1) + j + 10, null);
					g.drawImage(player.STATS.get(i).icon, 
							screenWidth - 200 + screenCorrection * 2, 
							50 * (i + 1) + j + screenCorrection + 10, null);
					g.drawImage(menu_sidebar_stat, 
							screenWidth - 100, 
							50 * (i + 1) + j + screenCorrection * 5, null);
					Color oldColor = g.getColor();
					g.setColor(Color.green);
					g.fillRect(screenWidth - 99, 50 * (i + 1) + j + screenCorrection * 6, 
							(int) ((player.STATS.get(i).getXPTotal() / player.STATS.get(i).getXPNeeded()) * 80), 12);
					g.setColor(oldColor);
					
					// Stat level
					gv = tempF2.createGlyphVector(frc, s);
					g.drawGlyphVector(gv, 
							(float) (screenWidth - 130 - (gv.getLogicalBounds().getWidth()) / 2), 
							50 * (i + 1) + j + screenCorrection * 8);
				}
			}
			break;
		case(MENU_TRADE):
			
			break;
		case(MENU_EXIT):
			
			break;
		case(MENU_DIED):
			if(fade < 0.99f) fade += (float) gameLoopTime / 10000;
			g.setColor(new Color(0, 0, 0, fade));
			g.fillRect(0, 0, screenWidth, screenHeight);
			g.setColor(Color.red);
			s = menuLang[23];
			tempF2 = font_med_1.deriveFont(60.0f);
			gv = tempF2.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, 
					(float) ((screenWidth / 2) - gv.getLogicalBounds().getCenterX()), 
					300);
			s = menuLang[24];
			tempF2 = font_med_1.deriveFont(40.0f);
			gv = tempF2.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, 
					(float) ((screenWidth / 2) - gv.getLogicalBounds().getCenterX()), 
					400);
			
			if(pressCount == 1) gameRunning = false;
			break;
		}
	}
}
