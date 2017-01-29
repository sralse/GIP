package GIP.GIPTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.GlyphVector;
import java.io.InputStream;

public class UtilsGraphics extends Settings {

	public void init() {
		// TODO UI Images
		localFont = getFont(FONTS.MEDIEVAL.value);

	}

	public void getUpdate(Graphics2D g) {

		if (escape) {
			gameRunning = false;
			return;
		}

		// First we draw our background and anything static
		g = (Graphics2D) graphicsBuffer.getDrawGraphics();
		// g.setColor(Color.black);
		g.fillRect(0, 0, screenWidth, screenHeight);
		g.drawImage(mapImage, 0, 0, null);
		
		uEntity.update();

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
		// dynamic drawing
		// This part below will make sure our player faces the right direction.
		if (SETTING_SHADOW) {
			g.setColor(shadow);
			for(Entity i : ENTITIES) {
				g.fillOval((int) i.x + i.imgH / 4, (int) i.y + i.imgW - 4, i.imgW / 2, 6);
			}
		}
		
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

			// Draw all entities
			for (int j = 1; j < ENTITIES.size(); j++) {
				if (i == (int) ENTITIES.get(j).y + ENTITIES.get(j).IMAGE.getHeight(null)) {
					g.drawImage(ENTITIES.get(j).IMAGE, 
							(int) ENTITIES.get(j).x, 
							(int) ENTITIES.get(j).y, null);
					// Entity name
					String s = ENTITIES.get(j).NAME;
					Font tempF = font_med_1.deriveFont(12.0f);
					GlyphVector gv = tempF.createGlyphVector(frc, s);
					g.setColor(Color.yellow);
					g.drawGlyphVector(gv, 
							(float) (ENTITIES.get(j).getCenterX() - gv.getLogicalBounds().getCenterX()),
							(float) (ENTITIES.get(j).y - screenCorrection * 2));
				}
			}
			
			g.setColor(Color.white);
			// Draw the player
			if (i == ((int)player.y + player.IMAGE.getHeight(null))) {
				g.drawImage(player.IMAGE, 
						(int) player.x, 
						(int) player.y, null);
				// Player name
				String s = player.NAME;
				Font tempF = font_med_1.deriveFont(14.0f);
				GlyphVector gv = tempF.createGlyphVector(frc, s);
				
				g.drawGlyphVector(gv, 
						(float) (player.x + 16 - gv.getLogicalBounds().getCenterX()),
						(float) (player.y - screenCorrection));
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
