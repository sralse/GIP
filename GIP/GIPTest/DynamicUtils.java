package GIPTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;


/**
 * DynamicUtils provides Layer rendering.
 * 
 * TODO Layer rendering from left-right, top-down
 * */
@SuppressWarnings("unused")
public class DynamicUtils extends Settings {	
//	private Image directionImage = loadImage(tx_player + playerType + playerFacing + imgExt);
	private Image[] playerImages = {null, null, null, null, null};
	private String playerFacingOld = "";
	
	// FPS Counter
	private long nextSecond = System.currentTimeMillis() + 1000;
	private int framesIncurrentSecond = 0;
	private int frameInLastSecond = 0;
	
	// UI Images
	private Image infoBox = scaleImageDetailed(loadImage(uiImageDir + box1_brown), 450, 150);
	private Image healthBlock = scaleImageCubic(loadImage(uiImageDir + health_Block), -3);
	private Image healthBlockL = scaleImageCubic(loadImage(uiImageDir + health_BlockL), -3);
	private Image healthBlockR = scaleImageCubic(loadImage(uiImageDir + health_BlockR), -3);
	private Image healthHolder = scaleImageDetailed(loadImage(uiImageDir + health_Holder), 130, 25);
	
	// User space info
	private int barHolderX = screenWidth - healthHolder.getWidth(null) - screenCorrection * 2 + screenCorrection / 2;
	
	public void init() {
		// TODO UI Images
		localFont = getFont(FONTS.RETRO_2D1.value);
		// Player image
		Entity.init();
		player.setImage(loadImage(tx_player + playerType + player.getFace() + player.getMode() + imgExt));
		//directionImage = defaultPlayerImage;
		for(int i = 0; i < playerImages.length; i++) {
			playerImages[i] = loadImage(tx_player + playerType + playerDirections[i] + playerFace + imgExt);
		}
		if(DEBUG){System.out.println("Player graphics initialised");}
	}

	public void getUpdate(Graphics2D g, int delta) {	
		
		if(escape) {
			gameRunning = false;
			return;
		}
		
		// First we draw our background and anything static
		g = (Graphics2D) graphicsBuffer.getDrawGraphics();	
		//g.setColor(Color.black);
		g.fillRect(0, 0, screenWidth, screenHeight);
		g.drawImage(mapImage, 0, 0, null);
		
		// First we update the player movement
		if(DEBUG){moveSpeed = 200;} else {moveSpeed = 100;}
		player.movementCheck(delta);
		
		// First we ask to remove any unneeded entities
		removeEntities();
		
		// Now we ask to draw any object/house/tree/player/entity
		updateGraphics(g);
		
		// Update GUI
		updateGUI(g);
		
		// Flip graphics buffer
		g.dispose();
		graphicsBuffer.show();
		
	}

	private void removeEntities() {
		// TODO Auto-generated method stub
		
	}

	private void updateGraphics(Graphics2D g) {
		// TODO Player animations
		// This part below will make sure our player faces the right direction.
		if(SETTING_SHADOW) {
			g.setColor(Color.BLACK);
			g.fillOval(player.getX() + 7, player.getY() + 30, 16, 6);
		}
		// Debug player
		if(DEBUG){player.setFace("0");}
		else if (player.getFace().equals("0")){player.setFace("D");}
		
		if(!player.getFace().equals(playerFacingOld)){
			for(int i = 0; i < playerDirections.length; i++) {
				if(playerDirections[i].equals(player.getFace())) {
					player.setImage(playerImages[i]);
				}
			}
		} 
		// If a certaint image does not exist we draw the default image "DOWN"
		if(player.getImage() == null) {
			player.setImage(defaultPlayerImage);
			player.setFace("D");;
		}
		// Set the new player image
		playerFacingOld = player.getFace();
		
		
		// TODO dynamic drawing
		for(int i = 0; i <= screenHeight; i++){
			// Cycle throug all of our objects asking to redraw themselves by X.
			// TODO Fix image heights
			for(int j = 0; j < MapObjects.sum(); j++) {
				if(i == MapObjects.objectY.get(j)) {
					if(MapObjects.objectImage != null) {
						g.drawImage(MapObjects.objectImage.get(j), MapObjects.objectX.get(j), i, null);
					} else {
						System.out.println("No objects initialised");
					}
				}
			}

			// TODO Rework w Rectangle
			// Draw the player
			if(i == player.getY()) {
				g.drawImage(player.getImage(), player.getX(), player.getY(), null);
			}
			
			for(int j = 0; j < ENTITIES.size(); j++) {
				if(ENTITIES.get(j).getY() == i) {
					String s = ENTITIES.get(j).getName();
					Font tempF = font_retro2D2.deriveFont(12.0f);
					GlyphVector gv = tempF.createGlyphVector(frc, s);
					g.setColor(Color.black);
					g.drawGlyphVector(gv, 
							(float) (ENTITIES.get(j).getX() + 16 - gv.getLogicalBounds().getCenterX()), 
							(float) (ENTITIES.get(j).getY() - screenCorrection));
					g.drawImage(ENTITIES.get(j).getImage(), ENTITIES.get(j).getX(), ENTITIES.get(j).getY(), null);
				}
			}
		}
	}

	private void updateGUI(Graphics2D g) {
		//TODO GUI
		// Player name
		String s = player.getName();
		Font tempF = font_retro2D2.deriveFont(16.0f);
		GlyphVector gv = tempF.createGlyphVector(frc, s);
		g.setColor(Color.black);
		g.drawGlyphVector(gv, 
				(float) (player.getX() + 16 - gv.getLogicalBounds().getCenterX()), 
				(float) (player.getY() - screenCorrection));
		// Info box
		g.drawImage(infoBox, 825, 600, null);
		// Health text
		s = menuLang[16];
		gv = localFont.createGlyphVector(frc, s);
		g.drawGlyphVector(gv, (float) (screenWidth - gv.getLogicalBounds().getWidth() - screenCorrection * 3), screenCorrection * 7);
		// Healthbar holder
		g.drawImage(healthHolder, barHolderX, (int) (gv.getLogicalBounds().getHeight() + screenCorrection * 4), null);
		// Health blocks TODO Rework so 1 size fits all (auto center of blocks etc...)
		for(int i = 1; i < ((player.getHealth() + 9 )/ 10); i++) {
			if(player.getHealth() > 30) {
				g.drawImage(healthBlockR, 
						screenWidth - healthBlockR.getWidth(null) - screenCorrection * 3, 
						screenCorrection * 10 + screenCorrection / 2, null);
				g.drawImage(healthBlock,  
						screenWidth - i * healthBlockR.getWidth(null) - screenCorrection * 3, 
						screenCorrection * 10 + screenCorrection / 2, null);
			}
			if(player.getHealth() == 100) {
				g.drawImage(healthBlockL, 
						barHolderX + screenCorrection + screenCorrection / 2, 
						screenCorrection * 10 + screenCorrection / 2, null);
			}
		}
		
		// FPS Counter and X&Y coords
		if (DEBUG || INFO) {
			long currentTime = System.currentTimeMillis();
			if(currentTime > nextSecond ) {
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
			g.drawRect(player.getX(), player.getY(), player.getImage().getWidth(null),  player.getImage().getHeight(null));
			// Object ESP
			for(int i = 0; i < MapObjects.objectImage.size(); i++) {
			g.setColor(Color.red);
			g.drawRect(	MapObjects.objectX.get(i), 
						MapObjects.objectY.get(i), 
						MapObjects.objectImage.get(i).getWidth(null), 
						MapObjects.objectImage.get(i).getHeight(null));
			}
		}
	
	}
	
	public String readFile(String filePath) {
		// Init reader
		BufferedReader read;
		// Init Strings used by the read loop
		String str1, strOut = "";
		// Init url, and read mapCurrent path to URL
		InputStream in = MapUtils.class.getClass().getResourceAsStream(filePath);

		if (in == null) {System.out.println("Can't find ref: " + filePath);}
		// Try reading our file
		try {
			read = new BufferedReader(new InputStreamReader(in));
			while ((str1 = read.readLine()) != null) {
				strOut += str1;
			}
			// Close reader and notify us of our completed action.
			read.close();
			if (DEBUG) {System.out.println("Done loading file: " + filePath);}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return strOut;
	}
	
	/**
	 * This function will load images as a type Image
	 * if the image does not exist it will return null
	 * @return <b>Image</b> The cached image from the string.
	 * @return null If the String is invalid.
	 * @param FilePath String: This is the path where the image is located.
	 * */
	@SuppressWarnings("static-access")
	public Image loadImage(String FilePath) {
		BufferedImage img = null;
		// Look up file
		URL url = Game.class.getClass().getClassLoader().getSystemResource(FilePath);
		if (url == null) {
			System.err.println("Can't find ref: " + FilePath); 
			return null;
		}
		// Try fetching image
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("FAILED! Whilst loading Image: " + url);
		}
//		// create an accelerated image of the right size to store our sprite in
//		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
//		Image image = gc.createCompatibleImage(img.getWidth(),img.getHeight(),Transparency.BITMASK);
//		// draw our source image into the accelerated image
//		img.getGraphics().clearRect(0, 0, img.getWidth(), img.getHeight());
//		img.getGraphics().drawImage(image,0,0,null);
		return img;
	}

	/**
	 * This function can scale any image using a 
	 * cubic interpolation given as the parameter.
	 * @return <b>Image</b> The new and scaled version of the input Image.
	 * @param img Image: This is the Image that needs to be converted.
	 * @param i Integer: The scaling amount.
	 * */
	public Image scaleImageCubic(Image img, int i) {
		int imgWidth = img.getWidth(null);
	    int imgHeight = img.getHeight(null);
	    int newWidth;
	    int newHeight;
	    if(i > 0) {
	    	newWidth = imgWidth * i;
	    	newHeight = imgHeight * i;
	    } else if(i < 0) {
	    	i = -i;
	    	newWidth = imgWidth / i;
	    	newHeight = imgHeight / i;
	    } else {
	    	System.err.println("Invalid scaling!");
	    	return null;
	    }
	    BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = newImage.createGraphics();
	    try {
	        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	        g.drawImage(img, 0, 0, newWidth, newHeight, null);
	    } finally {
	        g.dispose();
	    }
	    return newImage;
	}
	
	/**TODO Other interpolation
	 * This function can scale any image using a 
	 * neighbour interpolation given as the parameter.
	 * @return <b>Image</b> The new and scaled version of the input Image.
	 * @param img Image: This is the Image that needs to be converted.
	 * @param newWidth Integer: The scaling amount for x.
	 * @param newHeight Integer: The scaling amount for y.
	 * */	
	public Image scaleImageDetailed(Image img, int newWidth, int newHeight) {
	    BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = newImage.createGraphics();
	    try {
	        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC );
	        g.drawImage(img, 0, 0, newWidth, newHeight, null);
	    } finally {
	        g.dispose();
	    }
	    return newImage;
	}
	
	//TODO Make desc
	public Image rotateImage(Image img, int degrees) {
		// The required drawing location
		double locationX = img.getWidth(null) / 2;
		double locationY = img.getHeight(null) / 2;
		
		if((locationX % 2 != 0 || locationY % 2 != 0) && DEBUG) {
			System.err.println("Image rotation has quality loss!");
		}

		double rotation = Math.toRadians (degrees);
		AffineTransform transform = new AffineTransform();
	    transform.rotate(rotation, locationX, locationY);
	    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    img = op.filter((BufferedImage) img, null);
		
		return img;
	}
	
	/**
	 * This function can get you any font within the font Dir
	 * @return <b>newFont</b> The font you wanted so badly
	 * @param i The font choice index
	 * */
	public Font getFont(String s) {
		// Make a new exFont instance called font
		InputStream istream = Menu.class.getClass().getResourceAsStream(fontDir + s);
		Font newFont;
		try {
			newFont = Font.createFont(Font.TRUETYPE_FONT, istream);
			istream.close();
		} catch (Exception e) {
			newFont = new Font("Serif", Font.PLAIN, 24);
			e.printStackTrace();
		}
		newFont = newFont.deriveFont(fontSizeMenu);
		if(DEBUG && newFont != null){System.out.println("Font got : " + newFont);}
		return newFont;
	}

	/**TODO Make desc
	 * This function can get you any font within the font Dir
	 * @return <b>newFont</b> The font you wanted so badly
	 * @param i The font choice index
	 * */
	public void musicPlay(String musicFile, audioLine line) {
		// New audio file player
		if(DEBUG) System.out.println("Audio File : " + musicFile);
		audioPlayer = new Audio(musicFile);
		switch(line) {	
		case AUDIOLINE_MAIN:
			audioLine1 = new Thread(audioPlayer, "Audio Thread : 1 - MAIN");
			audioLine1.start();
			break;
		case AUDIOLINE_1:
			audioLine1 = new Thread(audioPlayer, "Audio Thread : " + line.value);
			audioLine1.start();
			break;
		case AUDIOLINE_2:
			audioLine2 = new Thread(audioPlayer, "Audio Thread : " + line.value);
			audioLine2.start();
			break;
		case AUDIOLINE_3:
			audioLine3 = new Thread(audioPlayer, "Audio Thread : " + line.value);
			audioLine3.start();
			break;
		case AUDIOLINE_4:
			audioLine4 = new Thread(audioPlayer, "Audio Thread : " + line.value);
			audioLine4.start();
			break;
		default:
			System.err.println("Audio Line not supported. Playing Audio on Line 4");
			audioLine4 = new Thread(audioPlayer, "Audio Thread : " + line.value);
			audioLine4.start();
			break;
		}
		
	}

	/**TODO Make desc
	 * This function can get you any font within the font Dir
	 * @return <b>newFont</b> The font you wanted so badly
	 * @param i The font choice index
	 * @return 
	 * */
	@SuppressWarnings("deprecation")
	public void musicStop(audioLine line) {
		switch(line) {
		case AUDIOLINE_MAIN:			
			audioLine1.stop();
			break;
		case AUDIOLINE_1:
			audioLine1.stop();
			break;
		case AUDIOLINE_2:
			audioLine2.stop();
			break;
		case AUDIOLINE_3:
			audioLine3.stop();
			break;
		case AUDIOLINE_4:
			audioLine4.stop();
			break;
		default:
			System.err.println("Error stopping audio!");
			break;
		}
		
		if(DEBUG) {System.err.println("Audio Line : " + line.value + " force stopped.");}
		
	}
	
	/**TODO Make desc
	 * This function can get you any font within the font Dir
	 * @return <b>newFont</b> The font you wanted so badly
	 * @param i The font choice index
	 * */
	public void updateMusic(String track, audioLine line) {
		if(!audioLine1.isAlive()) {
			dynamics.musicPlay(track, line);
		}
		//SETTING_VOLUME_db = 100 - 20 * Math.log((SETTING_VOLUME + 1) * 100 - 99);
		SETTINGS[0] = -6 + (20 * Math.log(SETTING_VOLUME/10));
	}
	
	/**TODO Make desc
	 * This function can get you any font within the font Dir
	 * @return <b>newFont</b> The font you wanted so badly
	 * @param i The font choice index
	 * */
	public void updateSettings(boolean b) {
		if(b) {
			SETTING_VOLUME = Double.parseDouble((String.valueOf(SETTINGS[0])));
			SETTING_LANGUAGE = (String) SETTINGS[1];
			SETTING_ANTIALIAS = (boolean) SETTINGS[2];
			SETTING_ACCGRAPHICS = (boolean) SETTINGS[3];
			SETTING_SHADOW = (boolean) SETTINGS[4];
		} else {
			SETTINGS[0] = SETTING_VOLUME;
			SETTINGS[1] = SETTING_LANGUAGE;
			SETTINGS[2] = SETTING_ANTIALIAS;
			SETTINGS[3] = SETTING_ACCGRAPHICS;
			SETTINGS[4] = SETTING_SHADOW;
		}
		
		if(SETTING_VOLUME == 1) {
			SETTING_VOLUME_db = -80.0d;
		} else {
			SETTING_VOLUME_db = -6 + (20 * Math.log(SETTING_VOLUME/10));
		}
		
		if(SETTING_LANGUAGE == LANG[EN]) {
			menuLang = menuEN;
		} else if (SETTING_LANGUAGE == LANG[NL]) {
			menuLang = menuNL;
		} else if (SETTING_LANGUAGE == LANG[EN]){
			menuLang = menuEN;
		} else if (SETTING_LANGUAGE == LANG[FR]) {
			menuLang = menuFR;
		}
		
	}

	/** TODO Make desc
	 * This function can get you any font within the font Dir
	 * @return <b>newFont</b> The font you wanted so badly
	 * @param i The font choice index
	 * */
	public void initGraphics() {
		g = (Graphics2D) graphicsBuffer.getDrawGraphics();
		if(SETTING_ANTIALIAS) {
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		frc = g.getFontRenderContext();
	}
}
