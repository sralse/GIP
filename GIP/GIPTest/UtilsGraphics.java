package GIP.GIPTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.GlyphVector;
import java.io.InputStream;

public class UtilsGraphics extends Settings {
	// private Image directionImage = loadImage(tx_player + playerType +
	// playerFacing + imgExt);


	public void init() {
		// TODO UI Images
		localFont = getFont(FONTS.RETRO_2D1.value);

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
			// TODO Fix image heights
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

			// TODO Rework w Rectangle
			// Draw the player
			if (i == (player.getY() + player.getImage().getHeight(null))) {
				g.drawImage(player.getImage(), player.getX(), player.getY(), null);
				// Player name
				String s = player.getName();
				Font tempF = font_retro2D2.deriveFont(16.0f);
				GlyphVector gv = tempF.createGlyphVector(frc, s);
				g.setColor(Color.black);
				g.drawGlyphVector(gv, (float) (player.getX() + 16 - gv.getLogicalBounds().getCenterX()),
						(float) (player.getY() - screenCorrection));
			}

			// Draw all entities
			for (int j = 0; j < ENTITIES.size(); j++) {
				if (ENTITIES.get(j).getY() == i) {
					// Entity name
					String s = ENTITIES.get(j).getName();
					Font tempF = font_retro2D2.deriveFont(12.0f);
					GlyphVector gv = tempF.createGlyphVector(frc, s);
					g.setColor(Color.black);
					g.drawGlyphVector(gv, (float) (ENTITIES.get(j).getX() + 16 - gv.getLogicalBounds().getCenterX()),
							(float) (ENTITIES.get(j).getY() - screenCorrection));
					g.drawImage(ENTITIES.get(j).getImage(), ENTITIES.get(j).getX(), ENTITIES.get(j).getY(), null);
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
