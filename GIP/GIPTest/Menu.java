package GIP.GIPTest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.GlyphVector;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class Menu extends Settings {
	/** Private number defining the current selection */
	private static int choice = 0;
	// Private time
	private static long time = System.currentTimeMillis();
	private static long timeNew;
	// Private button properties TEST.
	private static Image button = dynamics.loadImage(uiImageDir + buttonNormalLong);
	private static Image buttonSelected = dynamics.loadImage(uiImageDir + buttonNormalLongSelected);
	private static double btnWidth = button.getWidth(null) * SCALE_BUTTON;
	private static int btnSpacing = 100;
	private static int btnCenter = (int) ((screenWidth / 2) - (btnWidth / 2));
	
	public static void main(String argv[]) {
		// init our game menu
		try {
			game.initWindow();
			init();
			if(DEBUG) System.out.println(LANG.length + " languages detected.");
			boolean gameLoop = false;
			while(!gameLoop) {
				updateChoice();
				updateGraphics();
				dynamics.updateMusic(audioTrack.AUDIO_TRACK_MENU.value, audioLine.AUDIOLINE_MAIN);
			}
		// Error catch
		} catch(Exception e) {
			// Error log here
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			String error = errors.toString();
			JOptionPane.showMessageDialog(null, "Stacktrace:\n" + 
												 error + 
												"\nError:\n" + 
												e.getLocalizedMessage() + 
												"\n" + e + 
												"\nPress 'ok' to close.", 
												"An error occured", 
												JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	private static void updateGraphics() {
		// Background
		g = (Graphics2D) graphicsBuffer.getDrawGraphics();
		g.drawImage(bg0, 0, 0, null);
		
		// Button drawing
		for(int i = 0; i < 3; i++) {
			if(i != choice){
				g.drawImage(button, btnCenter, (btnSpacing / 2) + btnSpacing * i + screenOffset, null);
			}
		}
		
		// Selected button drawing
		g.drawImage(buttonSelected, btnCenter, (btnSpacing / 2) + btnSpacing * choice + screenOffset, null);
		
		// Text drawing
			for(int i = 0; i < 3; i++) {
				if(i == choice) {
					g.setColor(Color.white);
				} else {
					g.setColor(new Color (135,135,135));
				}
				String s = menuLang[i];
				GlyphVector gv = localFont.createGlyphVector(frc, s);
				g.drawGlyphVector(	gv, 
									(float) (screenWidth / 2 - (float) gv.getVisualBounds().getCenterX()), 
									(float) (btnSpacing * i + btnSpacing - (float) gv.getVisualBounds().getHeight()) + screenOffset);
			
		}
		
		// Flip graphics buffer
		g.dispose();
		graphicsBuffer.show();
	}

	private static void updateChoice() {
		// Timing fucntion for menu selection
		timeNew = System.currentTimeMillis();
		if(timeNew - time > 20) {

			if(select) {
				select = false;
				dynamics.musicPlay(audioEffect.AUDIO_EFFECT_BUTTON_CLICK.value, audioLine.AUDIOLINE_4);
				switch(choice) {
				case 0:
					startGame();
					break;
				case 1:
					MenuSettings.start();
					break;
				case 2:
					System.exit(0);
					break;
				default:
					System.out.println("No valid button press");
					break;
				}
				localFont = dynamics.getFont(FONTS.FUTURE_NORMAL.value);
				select = false;
				choice = 0;
			}

			if(upPressed) {
				choice--; 
				upPressed = false;
				dynamics.musicPlay(audioEffect.AUDIO_EFFECT_BUTTON.value, audioLine.AUDIOLINE_4);
			}
			if(downPressed) {
				choice++;
				downPressed = false;
				dynamics.musicPlay(audioEffect.AUDIO_EFFECT_BUTTON.value, audioLine.AUDIOLINE_4);
			}

			time = System.currentTimeMillis();
		}

		if(choice > 2) {choice = 0;}
		if(choice < 0) {choice = 2;}

	}
	
	private static void init() {
		// Init and settings for graphical context		
		dynamics.initGraphics();
		// Set our font
		localFont = dynamics.getFont(FONTS.FUTURE_NORMAL.value);
		// Scale our buttons
		if(SCALE_BUTTON != 1) {
			button = dynamics.scaleImageDetailed(button, (int) btnWidth, button.getHeight(null));
			buttonSelected = dynamics.scaleImageDetailed(buttonSelected, (int) btnWidth, buttonSelected.getHeight(null));
		}
		// New button sizes
		btnWidth = button.getWidth(null);
		btnCenter = (int) ((screenWidth / 2) - (btnWidth / 2));
		// Music settings
		SETTING_VOLUME_db = -6 + (20 * Math.log(SETTING_VOLUME/10));
		dynamics.musicPlay(audioTrack.AUDIO_TRACK_MENU.value, audioLine.AUDIOLINE_MAIN);
		// Setup language
		if(SETTING_LANGUAGE == LANG[EN]) {
			menuLang = menuEN;
		} else if (SETTING_LANGUAGE ==LANG[NL]) {
			menuLang = menuNL;
		} else {
			menuLang = menuEN;
		}

		DEBUG = false;
	}

	public static void startGame() {
		gameRunning = true;
		dynamics.musicStop(audioLine.AUDIOLINE_MAIN);
		game.initGame();
		game.gameLoop();

	}
}
