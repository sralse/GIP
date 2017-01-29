package GIP.GIPTest;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.*;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class Settings {
	// Global instances
	public static Graphics2D g;
	public static EntityPlayer player;
	public static Game game = new Game();
	public static StringWriter errors = new StringWriter();
	public static UtilsID uID = new UtilsID();
	public static UtilsFiles uFiles = new UtilsFiles();
	public static UtilsAudio uAudio = new UtilsAudio();
	public static UtilsImages uImages = new UtilsImages();
	public static UtilsEntity uEntity = new UtilsEntity();
	public static UtilsObjects uObjects = new UtilsObjects();
	public static UtilsGraphics uGraph = new UtilsGraphics();
	public static UtilsGUI uGUI = new UtilsGUI();
	public static UtilsItem uItems = new UtilsItem();
	public static Random randGen = new Random();

	// Global Game options
	public static boolean DEBUG = true;
	public static boolean INFO = false;
	public static boolean gameRunning = true;
	public static boolean SETTING_SHADOW = true;
	public static boolean SETTING_ANTIALIAS = true;
	public static boolean SETTING_ACCGRAPHICS = true;
	public static boolean waitingForKeyPress = false;
	public static int SETTING_BUFFERS = 2;
	public static int pressCount;
	public static int moveSpeedTiles = 4;
	public static int tileWidth = 16;
	public static int playerSpeed = moveSpeedTiles * 16;
	public static int gameLoopTime;
	public static final int screenWidth = 1280;
	public static final int screenHeight = 720;
	public static final int screenCorrection = 4;
	public static final int screenOffset = 150;
	public static final String TITLE = "Neamless Aura - DEMO";
	public static final String ICON = "NA.png";
	public static BufferStrategy graphicsBuffer;
	public static FontRenderContext frc = null;
	public static final Color shadow = new Color(0, 0, 0, 0.5f);

	// Global UI settings
	public static double SCALE_BUTTON = 1.5d;
	public static final String uiImageDir = "gui/";
	public static final String bg00 = "BG5.png";
	public static final String buttonNormalLong = "grey_button03.png";
	public static final String buttonNormalLongSelected = "blue_button00.png";
	public static final String arrowL_grey = "arrowSilver_left.png";
	public static final String arrowR_grey = "arrowSilver_right.png";
	public static final String arrowL_blue = "arrowBlue_left.png";
	public static final String arrowR_blue = "arrowBlue_right.png";
	public static final String sliderLine_h = "sliderLine_horizontal.png";
	public static final String sliderEnd = "sliderDot.png";
	public static final String sliderD_blue = "sliderBlue_down.png";
	public static final String sliderD_grey = "sliderGrey_down.png";
	public static final String health_Block = "barGreen_horizontalMid2.png";
	public static final String health_BlockMO = "barOrange_horizontalMid2.png";
	public static final String health_BlockMR = "barRed_horizontalMid2.png";
	public static final String health_BlockL = "barGreen_horizontalLeft2.png";
	public static final String health_BlockR = "barGreen_horizontalRight2.png";
	public static final String health_BlockLO = "barOrange_horizontalLeft2.png";
	public static final String health_BlockRO = "barOrange_horizontalRight2.png";
	public static final String health_BlockLR = "barOrange_horizontalLeft2.png";
	public static final String health_BlockRR = "barOrange_horizontalRight2.png";
	public static final String health_CircleL = "barGreen_horizontalLeft.png";
	public static final String health_CircleR = "barGreen_horizontalRight.png";
	public static final String health_CircleM = "barGreen_horizontalMid.png";
	public static final String health_CircleLR = "barRed_horizontalLeft.png";
	public static final String health_CircleRR = "barRed_horizontalRight.png";
	public static final String health_CircleMR = "barRed_horizontalMid.png";
	public static final String health_Holder = "barHolder_00.png";
	public static final String box1_brown = "boxBrown_01.png";
	public static final String gui_itemBar = "ItemBar.png";
	public static Image bg0 = uFiles.loadImage(uiImageDir + bg00);

	// Global Language settings
	public static final int EN = 0;
	public static final int NL = 1;
	public static final int FR = 2;
	public static final String[] LANG = { "ENGLISH", "NEDERLANDS", "FRANÇAIS" };
	public static final String[] menuEN = { "START", "OPTIONS", "EXIT", "CONTINUE", "LANGUAGE", "MODE", "ANTI ALIAS",
			"GRAPHICS ACCELERATION", "SHADOWS", "ENABLED", "DISABLED", "YES", "NO", "VOLUME", "error", "SAVE",
			"HEALTH" };
	public static final String[] menuNL = { "START", "OPTIES", "EXIT", "VERDER SPELEN", "TAAL", "MODUS", "ANTI ALIAS",
			"GRAFISCHE ACCELERATIE", "SCHADUWEN", "AAN", "UIT", "JA", "NEE", "VOLUME", "error", "OPSLAAN", "LEVENS" };
	public static final String[] menuFR = { "START", "OPTIONS", "FERMER", "CONTINUER", "LANGUE", "MODE", "ANTI ALIAS",
			"GRAPHICS ACCELERATION", "OMBRES", "ENABLED", "DISABLED", "OUI", "NON", "VOLUME", "error", "SAUVER",
			"HEALTH" };
	public static String SETTING_LANGUAGE = LANG[EN];
	public static String[] menuLang;

	// Global Font settings
	public static Font localFont = null;
	public static float fontSizeMenu = 28.0f;
	public static final String fontDir = "/fonts/";

	public static enum FONTS {
		NEWS_1("Enchanted Land.otf"),
		SPELL_1("HopferHornbook.ttf"),
		RETRO_2D1("Cubic2D.ttf"), 
		RETRO_2D2("Cubic2D2.ttf"),
		MEDIEVAL("IMMORTAL.ttf"),
		MEDIEVAL2("BigElla.ttf");
		public String value;

		private FONTS(String value) {
			this.value = value;
		}
	}
	// Fonts!
	public static Font font_med_1 = uGraph.getFont(FONTS.MEDIEVAL.value);
	public static Font font_2D_2 = uGraph.getFont(FONTS.RETRO_2D2.value);
	
	// Global keys pressed
	public static boolean leftPressed = false;
	public static boolean rightPressed = false;
	public static boolean upPressed = false;
	public static boolean downPressed = false;
	public static boolean spacePressed = false;
	public static boolean escape = false;

	// Global map settings
	public static int mapLines;
	public static int mapColumns;
	public static char[] charArrayMap = {};
	public static final String lvlDir = "level/";
	public static final String objectDir = lvlDir + "objects/";
	public static final String imgDir = lvlDir + "tiles/";
	public static final String mapDir = "/" + lvlDir + "maps/";
	public static final String mapName = "map_";
	public static final String lvlExt = ".txt";
	public static final String splitSymbol = ",";
	public static int mapID = 1;
	public static String mapCurrent = mapDir + mapName + mapID + lvlExt;
	public static List<String> mapArray = new ArrayList<String>();
	public static List<String> mapBounds = new ArrayList<String>();
	public static List<String> mapForbidden = Arrays
			.asList(new String[] { "4", "5", "n", "m", "o", "h", "i", "j", "k", "l" });

	// Global entity
	public static Image mapImage;
	public static Image defaultPlayerImage = null;
	public static int entDataBlockSize = 5;
	public static int aDefInt = 300;
	public static final String mapDirObjects = "_objects";
	public static final String mapDirTiles = "_tiles";
	public static final String entDir = "entity/";
	public static final String playerImgDir = entDir + "player/";
	public static final String animalImgDir = entDir + "animal/";
	public static final String monsterImgDir = entDir + "monster/";
	public static final String villagerImgDir = entDir + "villager/";
	public static final String imgExt = ".png";
	public static int playerType = 0;
	public static int playerFace = 0;
	public static final String tx_player = playerImgDir + "player_";
	public static final String tx_villager = villagerImgDir + "npc_";
	public static final String tx_monster = monsterImgDir + "monster_";
	public static final String tx_animal = animalImgDir + "animal_";
	public static final Image debug = uFiles.loadImage(entDir + "debug" + imgExt);

	// Global Item
	public final static String itemDir = lvlDir + "items/";
	
	// Message box settings
	public static boolean messaged = false;
	public static Entity msgEntity;
	public static long msgTimer1;
	public static long msgTimer2;
	public static int msgTime = 5000;
	public static String msgMSG;
	public static String msgVillagerPath = "/level/locals/" + SETTING_LANGUAGE + "/npc_msg.txt";
	public static final int msgBoxX = 825;
	public static final int msgBoxY = 600;
	public static final int msgBoxImgSpace = 20;
	public static List<String> messages = new ArrayList<String>();

	// Global music settings
	public static boolean hasAudio = true;
	public static Audio audioPlayer = null;
	public static double SETTING_VOLUME = 5;
	public static double SETTING_VOLUME_db = 0.0d;
	public static String audioDir = "/music/";
	public static String soundsDir = "sounds/";
	public static Thread audioLine1;
	public static Thread audioLine2;
	public static Thread audioLine3;
	public static Thread audioLine4;

	public static enum audioTrack {
		AUDIO_TRACK_MENU("Gloom.wav"), AUDIO_TRACK_GAME_00("Forest.wav"), AUDIO_TRACK_GAME_01(
				"Hero.wav"), AUDIO_TRACK_GAME_02("King.wav");
		public String value;

		private audioTrack(String value) {
			this.value = value;
		}
	}

	public static enum audioEffect {
		AUDIO_EFFECT_BUTTON("Button.wav"), AUDIO_EFFECT_BUTTON_CLICK("ButtonClick2.wav");
		public String value;

		private audioEffect(String value) {
			this.value = soundsDir + value;
		}
	}

	public static enum audioLine {
		AUDIOLINE_MAIN(0), AUDIOLINE_1(0), AUDIOLINE_2(1), AUDIOLINE_3(2), AUDIOLINE_4(3);
		public int value;

		private audioLine(int value) {
			this.value = value;
		}
	}

	public static final int AUDIOLINE_MAIN = 0;

	// Global Entities lists
	public static ArrayList<Entity> ENTITIES = new ArrayList<Entity>();
	//public static List<String> uTypes = Arrays
	//		.asList(new String[] { "VILLAGER", "SMITH", "CLERCK", "FARMER", "QUEST MASTER", "PRIEST" });

	public static enum ENTITY_VILLAGER {
		HERO_MALE(0), HERO_FEMALE(1), KNIGHT_MALE(2), KNIGHT_FEMALE(3), VILLAGER_FEMALE(4), VILLAGER_MALE(
				5), QUEST_MASTER(6), PROFESSOR(7), PIRATE(8);
		public int value;

		private ENTITY_VILLAGER(int value) {
			this.value = value;
		}
	}

	public static enum ENTITY_MONSTER {
		IMP_SMALL(0), RAT_BIG(1), SKELETON_NORMAL(2);
		public int value;

		private ENTITY_MONSTER(int value) {
			this.value = value;
		}
	}

	public static enum ENTITY_ANIMAL {
		CROW(0);
		public int value;

		private ENTITY_ANIMAL(int value) {
			this.value = value;
		}
	}

	/** Collection of settings. */
	public static Object[] SETTINGS = new Object[] { SETTING_VOLUME, SETTING_LANGUAGE, SETTING_ANTIALIAS,
			SETTING_ACCGRAPHICS, SETTING_SHADOW };
	
	public void updateSettings(boolean b) {
		if (b) {
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

		if (SETTING_VOLUME == 1) {
			SETTING_VOLUME_db = -80.0d;
		} else {
			SETTING_VOLUME_db = -6 + (20 * Math.log(SETTING_VOLUME / 10));
		}

		if (SETTING_LANGUAGE == LANG[EN]) {
			menuLang = menuEN;
		} else if (SETTING_LANGUAGE == LANG[NL]) {
			menuLang = menuNL;
		} else if (SETTING_LANGUAGE == LANG[EN]) {
			menuLang = menuEN;
		} else if (SETTING_LANGUAGE == LANG[FR]) {
			menuLang = menuFR;
		}

	}
}
