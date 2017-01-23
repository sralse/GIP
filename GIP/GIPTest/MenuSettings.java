package GIP.GIPTest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.GlyphVector;
import java.util.Arrays;

class MenuSettings extends Settings {
	// Other
	private static boolean gameLoop = true;
	private static int sliderHeigth = 4;
	private static int sliderWidth = 450;
	// Time limiter for smooth scrolling
	private static long time = System.currentTimeMillis();
	private static long timeNew;
	// Images
	private static Image button = uFiles.loadImage(uiImageDir + buttonNormalLong);
	private static Image buttonSelected = uFiles.loadImage(uiImageDir + buttonNormalLongSelected);
	private static Image arrowLeft = uFiles.loadImage(uiImageDir + arrowL_grey);
	private static Image arrowRight = uFiles.loadImage(uiImageDir + arrowR_grey);
	private static Image arrowLeftSelected = uFiles.loadImage(uiImageDir + arrowL_blue);
	private static Image arrowRightSelected = uFiles.loadImage(uiImageDir + arrowR_blue);
	private static Image sliderLine = uImages.scaleImageDetailed(uFiles.loadImage(uiImageDir + sliderLine_h), sliderWidth,
			sliderHeigth);
	private static Image sliderDot = uFiles.loadImage(uiImageDir + sliderEnd);
	private static Image sliderActive = uFiles.loadImage(uiImageDir + sliderD_blue);
	private static Image sliderInactive = uFiles.loadImage(uiImageDir + sliderD_grey);
	// Layout vars
	private static int choice = 0;
	private static int oldChoice = choice;
	private static double cycle = 0;
	private static double btnWidth = button.getWidth(null);
	private static int textColumnLeft = 100;
	private static int textSpacingX = 100;
	private static int textSpacing = 75;
	private static int textColumnRight = 650;
	private static int arrowPos1 = textColumnRight;
	private static int arrowPos2 = 1100;
	private static int btnCenter = (int) ((screenWidth / 2) - (btnWidth / 2));
	private static int textColumnRightCenter = textColumnRight + (arrowPos2 - arrowPos1) / 2;

	public static void start() {
		init();
		while (gameLoop) {
			updateChoice();
			updateGraphics();
			uAudio.updateMusic(audioTrack.AUDIO_TRACK_MENU.value, audioLine.AUDIOLINE_MAIN);
		}
	}

	private static void init() {
		// Init and settings for graphical context
		gameLoop = true;
		choice = 0;
		uGraph.initGraphics();
	}

	private static void updateGraphics() {
		// TODO Auto-generated method stub
		// Background
		g = (Graphics2D) graphicsBuffer.getDrawGraphics();
		g.drawImage(bg0, 0, 0, null);

		// Buttons
		for (int i = 0; i < 5; i++) {
			String s = null;
			if (i == choice) {
				g.setColor(Color.white);
				if (i != 5 && i != 0) {
					g.drawImage(arrowLeftSelected, arrowPos1, textSpacingX + textSpacing * i, null);
					g.drawImage(arrowRightSelected, arrowPos2, textSpacingX + textSpacing * i, null);
				}
				if (i == 0)
					s = menuLang[13];
				if (i == 1)
					s = menuLang[4];
				if (i == 2)
					s = menuLang[6];
				if (i == 3)
					s = menuLang[7];
				if (i == 4)
					s = menuLang[8];
				GlyphVector gv = localFont.createGlyphVector(frc, s);
				g.drawGlyphVector(gv, textColumnLeft,
						(float) (gv.getLogicalBounds().getHeight() / 2 + textSpacingX + textSpacing * i));
			} else {
				g.setColor(new Color(135, 135, 135));
				if (i != 5 && i != 0) {
					g.drawImage(arrowLeft, arrowPos1, textSpacingX + textSpacing * i, null);
					g.drawImage(arrowRight, arrowPos2, textSpacingX + textSpacing * i, null);
				}
				if (i == 0)
					s = menuLang[13];
				if (i == 1)
					s = menuLang[4];
				if (i == 2)
					s = menuLang[6];
				if (i == 3)
					s = menuLang[7];
				if (i == 4)
					s = menuLang[8];
				GlyphVector gv = localFont.createGlyphVector(frc, s);
				g.drawGlyphVector(gv, textColumnLeft,
						(float) (gv.getLogicalBounds().getHeight() / 2 + textSpacingX + textSpacing * i));
			}
		}

		// Slider
		g.drawImage(sliderLine, arrowPos1 + screenCorrection, textSpacingX + screenCorrection, null);
		g.drawImage(sliderDot, arrowPos1 + screenCorrection, textSpacingX, null);
		g.drawImage(sliderDot, arrowPos2 + screenCorrection, textSpacingX, null);
		int sliderVolume = (int) ((sliderWidth / 9) * (SETTING_VOLUME - 1));
		if (choice == 0) {
			g.drawImage(sliderActive, arrowPos1 - screenCorrection + sliderVolume,
					textSpacingX - sliderActive.getHeight(null) + screenCorrection, null);
		} else {
			g.drawImage(sliderInactive, arrowPos1 - screenCorrection + sliderVolume,
					textSpacingX - sliderActive.getHeight(null) + screenCorrection, null);
		}

		// Status text
		for (int i = 1; i < 5; i++) {
			boolean b;
			String s = "";
			if (i != choice)
				g.setColor(new Color(135, 135, 135));
			else
				g.setColor(Color.white);
			if (i == 1) {
				s = (String) SETTINGS[i];
				GlyphVector gv = localFont.createGlyphVector(frc, s);
				g.drawGlyphVector(gv, (float) (textColumnRightCenter - gv.getLogicalBounds().getCenterX()),
						(float) (gv.getLogicalBounds().getHeight() / 2 + textSpacingX + textSpacing * i));
			} else {
				b = (boolean) SETTINGS[i];
				if (b)
					s = menuLang[9];
				else
					s = menuLang[10];
				GlyphVector gv = localFont.createGlyphVector(frc, s);
				g.drawGlyphVector(gv, (float) (textColumnRightCenter - gv.getLogicalBounds().getCenterX()),
						(float) (gv.getLogicalBounds().getHeight() / 2 + textSpacingX + textSpacing * i));
			}
		}

		// TODO Button text pos fix
		String s = menuLang[15];
		if (choice == 5) {
			g.setColor(Color.white);
			g.drawImage(buttonSelected, btnCenter, screenHeight - screenOffset, null);
			GlyphVector gv = localFont.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, (float) (screenWidth / 2 - (float) gv.getVisualBounds().getCenterX()),
					(float) (screenHeight - screenOffset + gv.getLogicalBounds().getHeight()));
		} else {
			g.setColor(new Color(135, 135, 135));
			g.drawImage(button, btnCenter, screenHeight - screenOffset, null);
			GlyphVector gv = localFont.createGlyphVector(frc, s);
			g.drawGlyphVector(gv, (float) (screenWidth / 2 - (float) gv.getVisualBounds().getCenterX()),
					(float) (screenHeight - screenOffset + gv.getLogicalBounds().getHeight()));
		}

		if (DEBUG)
			g.drawString("CHOICE : " + choice, 10, 40);
		if (DEBUG)
			g.drawString("CYCLE : " + cycle, 10, 60);
		if (DEBUG)
			g.drawString("VOLUME : " + SETTING_VOLUME_db, 10, 80);

		// Flip graphics buffer
		g.dispose();
		graphicsBuffer.show();
	}

	private static void updateChoice() {
		// Timing fucntion for menu selection
		uGraph.updateSettings(false);

		timeNew = System.currentTimeMillis();
		if (timeNew - time > 20) {

			if (spacePressed && choice == 5) {
				spacePressed = false;
				uAudio.musicPlay(audioEffect.AUDIO_EFFECT_BUTTON_CLICK.value, audioLine.AUDIOLINE_4);
				uGraph.updateSettings(true);
				gameLoop = false;
				return;
			}

			if (upPressed) {
				oldChoice = choice;
				choice--;
				cycle = 0;
				upPressed = false;
				uAudio.musicPlay(audioEffect.AUDIO_EFFECT_BUTTON.value, audioLine.AUDIOLINE_4);
			}
			if (downPressed) {
				oldChoice = choice;
				choice++;
				cycle = 0;
				downPressed = false;
				uAudio.musicPlay(audioEffect.AUDIO_EFFECT_BUTTON.value, audioLine.AUDIOLINE_4);
			}
			if (leftPressed) {
				cycle--;
			}
			if (rightPressed) {
				cycle++;
			}

			if ((rightPressed || leftPressed) && choice != 5) {
				oldChoice = choice;
				rightPressed = false;
				leftPressed = false;
				uAudio.musicPlay(audioEffect.AUDIO_EFFECT_BUTTON.value, audioLine.AUDIOLINE_4);
			}

			time = System.currentTimeMillis();
		}

		if (choice > 5) {
			choice = 0;
		}
		if (choice < 0) {
			choice = 5;
		}

		// Volume
		if (choice == 0) {
			if (cycle == 0) {
				cycle = SETTING_VOLUME;
			}
			if (cycle > 10) {
				cycle = 10;
			}
			if (cycle < 1) {
				cycle = 1;
			}
			SETTINGS[choice] = (int) cycle;

			// Language
		} else if (choice == 1) {
			if (cycle < 0)
				cycle = 2;
			if (cycle > 2)
				cycle = 0;
			if (choice != oldChoice) {
				cycle = Arrays.asList(LANG).indexOf(SETTINGS[choice]);
			} else {
				SETTINGS[choice] = LANG[(int) cycle];
			}

			// Booleans
		} else if (choice > 1 && choice != 5) {
			if (cycle > 1) {
				cycle = 0;
				SETTINGS[choice] = true;
			}
			if (cycle < 0) {
				cycle = 1;
				SETTINGS[choice] = false;
			}
			if (choice != oldChoice) {
				if ((boolean) SETTINGS[choice] == true)
					cycle = 0;
				else
					cycle = 1;
			} else {
				if (cycle == 0)
					SETTINGS[choice] = true;
				else
					SETTINGS[choice] = false;
			}
			// if((boolean) (SETTINGS[choice])) cycle = 0;
			// else cycle = 1;

			// Confirm button
		} else if (choice == 5) {
			cycle = 0;
		}

		uGraph.updateSettings(true);

	}

}
