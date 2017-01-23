package GIP.GIPTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.GlyphVector;
import java.io.InputStream;

public class UtilsGraphics extends Settings {

	private Image healthCircleMG = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_CircleM), -3);
	private Image healthCircleLG = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_CircleL), -3);
	private Image healthCircleRG = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_CircleR), -3);
	private Image healthCircleMR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_CircleMR), -3);
	private Image healthCircleLR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_CircleLR), -3);
	private Image healthCircleRR = uImages.scaleImageCubic(uFiles.loadImage(uiImageDir + health_CircleRR), -3);

	public void init() {
		// TODO UI Images
		localFont = getFont(FONTS.MEDIEVAL.value);

	}

	public void getUpdate(Graphics2D g, int delta) {

		if (escape) {
			gameRunning = false;
			return;
		}

		// First we draw our background and anything static
		g = (Graphics2D) graphicsBuffer.getDrawGraphics();
		// g.setColor(Color.black);
		g.fillRect(0, 0, screenWidth, screenHeight);
		g.drawImage(mapImage, 0, 0, null);
		
		uEntity.update(delta);

		// Now we ask to draw any object/house/tree/player/entity
		updateGraphics(g);

		// Update GUI
		uGUI.update(g);

		// Flip graphics buffer
		g.dispose();
		graphicsBuffer.show();

		// Poll gamepad
		// new JInput();
	}

	private void updateGraphics(Graphics2D g) {
		// TODO Player animations
		// This part below will make sure our player faces the right direction.
		if (SETTING_SHADOW) {
			g.setColor(Color.BLACK);
			g.fillOval(player.getX() + 7, player.getY() + 30, 16, 6);
		}

		// TODO dynamic drawing
		for (int i = 0; i <= screenHeight; i++) {
			// Cycle throug all of our objects asking to redraw themselves by X.
			for (int j = 0; j < uObjects.sum(); j++) {
				if (i == (uObjects.objectY.get(j) + uObjects.objectImage.get(j).getHeight(null))) {
					if (uObjects.objectImage != null) {
						g.drawImage(
								uObjects.objectImage.get(j), 
								uObjects.objectX.get(j), 
								uObjects.objectY.get(j), 
							null);
					} else {
						System.out.println("No objects initialised");
					}
				}
			}

			g.setColor(Color.white);
			// Draw the player
			if (i == (player.getY() + player.getImage().getHeight(null))) {
				g.drawImage(player.getImage(), player.getX(), player.getY(), null);
				// Player name
				String s = player.getName();
				Font tempF = font_med_1.deriveFont(14.0f);
				GlyphVector gv = tempF.createGlyphVector(frc, s);
				
				g.drawGlyphVector(gv, (float) (player.getX() + 16 - gv.getLogicalBounds().getCenterX()),
						(float) (player.getY() - screenCorrection));
			}

			// Draw all entities
			for (int j = 1; j < ENTITIES.size(); j++) {
				if (i == ENTITIES.get(j).getY() + ENTITIES.get(j).getImage().getHeight(null)) {
					g.drawImage(ENTITIES.get(j).getImage(), ENTITIES.get(j).getX(), ENTITIES.get(j).getY(), null);
					// Entity name
					String s = ENTITIES.get(j).getName();
					Font tempF = font_med_1.deriveFont(12.0f);
					GlyphVector gv = tempF.createGlyphVector(frc, s);
					g.setColor(Color.black);
					g.drawGlyphVector(gv, (float) (ENTITIES.get(j).getCenterX() - gv.getLogicalBounds().getCenterX()),
							(float) (ENTITIES.get(j).getY() - screenCorrection * 2));
					
					// TODO Entity health bg
					int maxH = ENTITIES.get(j).maxHealth;
					int cH = ENTITIES.get(j).HEALTH;
					if(maxH > 0) {
						// Red Health holder
						g.drawImage(healthCircleLR, 
								ENTITIES.get(j).getX() - healthCircleLR.getWidth(null) * 3 + 1, 
								ENTITIES.get(j).getY() - screenCorrection * 2 + 2, null);
						g.drawImage(healthCircleRR,
								ENTITIES.get(j).getX() + healthCircleLR.getWidth(null) * 7 + 1, 
								ENTITIES.get(j).getY() - screenCorrection * 2 + 2, null);
						for(int k = 1; k < 9; k++) {
							g.drawImage(healthCircleMR, 
									ENTITIES.get(j).getX() - healthCircleLR.getWidth(null) * 3 + healthCircleLR.getWidth(null) * k + 1, 
									ENTITIES.get(j).getY() - screenCorrection * 2 + 2, null);
						}
						
						// Begin of health
						g.drawImage(healthCircleRG,
								ENTITIES.get(j).getX() + healthCircleLR.getWidth(null) * 7 + 1, 
								ENTITIES.get(j).getY() - screenCorrection * 2 + 2, null);
						// Entity health
						int cycles;
						int factor;
						int beginH = ENTITIES.get(j).getX() - healthCircleLR.getWidth(null) * 3 + 1;
						if(maxH / 10 < 8) {
							cycles = maxH;
							factor = 1;
						} else {
							cycles = maxH / 10;
							factor = cycles;
						}
						for(int k = 1; k < cycles - 1; k++) {
							// Blocks of health
							if(cH > k * factor && healthCircleLR.getWidth(null) * 3 + healthCircleLR.getWidth(null) * k + 1 < beginH) {
							g.drawImage(healthCircleMG, 
									ENTITIES.get(j).getX() + healthCircleLR.getWidth(null) * 6 - healthCircleLR.getWidth(null) * k + 1, 
									ENTITIES.get(j).getY() - screenCorrection * 2 + 2, null);
							}
							if(cH/maxH >= 1) {
								
								// Full health
								g.drawImage(healthCircleLG,
										beginH, 
										ENTITIES.get(j).getY() - screenCorrection * 2 + 2, null);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This function can get you any font within the font Dir
	 * 
	 * @return <b>newFont</b> The font you wanted so badly
	 * @param i
	 *            The font choice index
	 */
	public Font getFont(String s) {
		// Make a new exFont instance called font
		InputStream istream = MenuMain.class.getClass().getResourceAsStream(fontDir + s);
		Font newFont;
		try {
			newFont = Font.createFont(Font.TRUETYPE_FONT, istream);
			istream.close();
		} catch (Exception e) {
			newFont = new Font("Serif", Font.PLAIN, 24);
			e.printStackTrace();
		}
		newFont = newFont.deriveFont(fontSizeMenu);
		if (DEBUG && newFont != null) {
			System.out.println("Font got : " + newFont);
		}
		return newFont;
	}

	/**
	 * TODO Make desc This function can get you any font within the font Dir
	 * 
	 * @return <b>newFont</b> The font you wanted so badly
	 * @param i
	 *            The font choice index
	 */
	public void initGraphics() {
		g = (Graphics2D) graphicsBuffer.getDrawGraphics();
		if (SETTING_ANTIALIAS) {
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		frc = g.getFontRenderContext();
	}
}
