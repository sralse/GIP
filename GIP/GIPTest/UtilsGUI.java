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
	private Image healthHolder = uImages.scaleImageDetailed(uFiles.loadImage(uiImageDir + health_Holder), 130, 25);

	// User space info
	private int barHolderX = screenWidth - healthHolder.getWidth(null) - screenCorrection * 2 + screenCorrection / 2;
	
	private String s;
	private GlyphVector gv;
	
	public void entityMessage(int id, String msg) {

		// Message box
		g.drawImage(msgBox, msgBoxX, msgBoxY, null);
		g.drawImage(uID.getEntity(id).getImage(), msgBoxX + msgBoxImgSpace, msgBoxY + msgBoxImgSpace, null);
		Font tempF = font_retro2D2.deriveFont(16.0f);

		StringTokenizer tok = new StringTokenizer(s, " ");
		StringBuilder output = new StringBuilder(s.length());
		int lineLen = 0;
		while (tok.hasMoreTokens()) {
			String word = tok.nextToken() + " ";

			if (lineLen + word.length() > msgBoxImgSpace * 2) {
				output.append("::");
				lineLen = 0;
			}
			output.append(word);
			lineLen += word.length();
		}
		String[] msgTempString = (output.toString()).split("::");
		
		for (int i = 0; i < msgTempString.length; i++) {
			String s = msgTempString[i];
			gv = tempF.createGlyphVector(frc, s);
			g.setColor(Color.black);
			g.drawGlyphVector(gv, msgBoxX + msgBoxImgSpace * 3,
					msgBoxY + msgBoxImgSpace * 2 + (msgBoxImgSpace / 2) * i + screenCorrection);
		}
	}

	public void update(Graphics2D g) {
		// Healthbar holder
		g.drawImage(healthHolder, barHolderX, (int) (gv.getLogicalBounds().getHeight() + screenCorrection * 4), null);
		// Health blocks TODO Rework so 1 size fits all (auto center of blocks
		// etc...)
		for (int i = 1; i < ((player.getHealth() + 9) / 10); i++) {
			if (player.getHealth() > 30) {
				g.drawImage(healthBlockR, screenWidth - healthBlockR.getWidth(null) - screenCorrection * 3,
						screenCorrection * 10 + screenCorrection / 2, null);
				g.drawImage(healthBlock, screenWidth - i * healthBlockR.getWidth(null) - screenCorrection * 3,
						screenCorrection * 10 + screenCorrection / 2, null);
			}
			if (player.getHealth() >= 100) {
				g.drawImage(healthBlockL, barHolderX + screenCorrection + screenCorrection / 2,
						screenCorrection * 10 + screenCorrection / 2, null);
			}
		}

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
			g.drawString("XPos : " + player.getX(), 10, screenHeight - 45);
			g.drawString("YPos : " + player.getY(), 10, screenHeight - 30);
			g.drawString("Facing : " + player.getFace(), 10, screenHeight - 15);
			// Column 2
			g.drawString("Direction X : " + player.getHorizontalMovement(), 100, screenHeight - 30);
			g.drawString("Direction Y : " + player.getVerticalMovement(), 100, screenHeight - 15);
			// Player ESP
			g.setColor(Color.red);
			g.drawRect(player.getX(), player.getY(), player.getImage().getWidth(null),
					player.getImage().getHeight(null));
			// Object ESP
			for (int i = 0; i < uObjects.objectImage.size(); i++) {
				g.setColor(Color.red);
				g.drawRect(uObjects.objectX.get(i), uObjects.objectY.get(i),
						uObjects.objectImage.get(i).getWidth(null), uObjects.objectImage.get(i).getHeight(null));
			}
		}

		// Health text
		s = menuLang[16];
		gv = localFont.createGlyphVector(frc, s);
		g.drawGlyphVector(gv, (float) (screenWidth - gv.getLogicalBounds().getWidth() - screenCorrection * 3),
				screenCorrection * 7);
	}
}
