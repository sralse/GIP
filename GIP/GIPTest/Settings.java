package GIPTest;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.*;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Settings {
	// Global instances
	public static Game game = new Game();
	public static EntityPlayer player;
	public static StringWriter errors = new StringWriter();
	public static DynamicUtils dynamics = new DynamicUtils();
	public static Graphics2D g;
	
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
	public static int moveSpeedTiles = 6;
	public static int moveSpeed = moveSpeedTiles * 16;
	public static final int screenWidth = 1280;
	public static final int screenHeight = 720;
	public static final int screenCorrection = 4;
	public static final int screenOffset = 150;
	public static final String TITLE = "Neamless Aura - DEMO";
	public static BufferStrategy graphicsBuffer;
	public static FontRenderContext frc = null;
	
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
	public static final String health_BlockL = "barGreen_horizontalLeft2.png";
	public static final String health_BlockR = "barGreen_horizontalRight2.png";
	public static final String health_Holder = "barHolder_00.png";
	public static final String box1_brown = "boxBrown_01.png";
	public static Image bg0 =  dynamics.loadImage(uiImageDir + bg00);

	// Global Language settings
	public static final int EN = 0;
	public static final int NL = 1;
	public static final int FR = 2;
	public static final String[] LANG = {"ENGLISH","NEDERLANDS","FRANÇAIS"};
	public static final String[] menuEN = {
			"START","OPTIONS","EXIT","CONTINUE","LANGUAGE",
			"MODE","ANTI ALIAS","GRAPHICS ACCELERATION","SHADOWS","ENABLED",
			"DISABLED","YES","NO","VOLUME","error",
			"SAVE","HEALTH"};
	public static final String[] menuNL = {
			"START","OPTIES","EXIT","VERDER SPELEN","TAAL",
			"MODUS","ANTI ALIAS","GRAFISCHE ACCELERATIE","SCHADUWEN","AAN",
			"UIT","JA","NEE","VOLUME","error",
			"OPSLAAN","LEVENS"};
	public static final String[] menuFR = {
			"START", "OPTIONS", "EXIT", "CONTINUER", "LANGUE",
			"MODE", "ANTI ALIAS", "GRAPHICS ACCELERATION", "OMBRES","ENABLED", 
			"DISABLED","OUI", "NON","VOLUME","error",
			"SAUVER","HEALTH"};
	public static String SETTING_LANGUAGE = LANG[EN];
	public static String[] menuLang;
	
	// Global Font settings
	public static Font localFont = null;
	public static float fontSizeMenu = 28.0f;
	public static final String fontDir = "/fonts/";
	public static enum FONTS {
		FUTURE_NORMAL("KenneyFuture00b.ttf"),
		FUTURE_SMALL("KenneyFuture00s.ttf"),
		NORMAL_NORMAL("Nevis.ttf"),
		RETRO_3D1("Cubic3D.ttf"),
		RETRO_3D2("Fipps3D.ttf"),
		RETRO_2D1("Cubic2D.ttf"),
		RETRO_2D2("Cubic2D2.ttf");
		public String value;
		private FONTS(String value) {
			this.value = value;
		}
	}
	public static Font font_future_normal = dynamics.getFont(FONTS.FUTURE_NORMAL.value);
	public static Font font_retro3D1 = dynamics.getFont(FONTS.RETRO_3D1.value);
	public static Font font_retro3D2 = dynamics.getFont(FONTS.RETRO_3D2.value);
	public static Font font_retro2D1 = dynamics.getFont(FONTS.RETRO_2D1.value);
	public static Font font_retro2D2 = dynamics.getFont(FONTS.RETRO_2D2.value);
	
	// Global keys pressed
	public static boolean leftPressed = false;
	public static boolean rightPressed = false;
	public static boolean upPressed = false;
	public static boolean downPressed = false;
	public static boolean select = false;
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
	public static List<String> mapForbidden = Arrays.asList(new String[]{"4","5","n","m","o","h","i","j","k","l"});
	
	// Global entity
	public static Image mapImage;
	public static Image defaultPlayerImage = null;
	public static final String mapDirObjects = "_objects";
	public static final String mapDirTiles = "_tiles";
	public static final String entDir = "entity/";
	public static final String playerImgDir = entDir + "player/";
	public static final String animalImgDir = entDir + "animal/";
	public static final String monsterImgDir = entDir + "monster/";
	public static final String npcImgDir = entDir + "npc/";
	public static final String imgExt = ".png";
	public static int playerType = 0;
	public static int playerFace = 0;
	public static final String tx_player = playerImgDir + "player_";
	public static final String tx_npc = npcImgDir + "npc_";
	public static final String tx_monster = monsterImgDir + "monster_";
	public static final String tx_animal = animalImgDir + "animal_";
	public final static String[] playerDirections = {"U", "R", "L", "D", "0"};
	
	// Global music settings
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
		AUDIO_TRACK_MENU("Gloom.wav"),
		AUDIO_TRACK_GAME_00("Forest.wav"),
		AUDIO_TRACK_GAME_01("Hero.wav"),
		AUDIO_TRACK_GAME_02("King.wav");
		public String value;
		private audioTrack(String value) {
			this.value = value;
		}
	}
	public static enum audioEffect {
		AUDIO_EFFECT_BUTTON("Button.wav"),
		AUDIO_EFFECT_BUTTON_CLICK("ButtonClick2.wav");
		public String value;
		private audioEffect(String value) {
			this.value = soundsDir + value;
		}
	}
	public static enum audioLine {
		AUDIOLINE_MAIN(0),
		AUDIOLINE_1(0),
		AUDIOLINE_2(1),
		AUDIOLINE_3(2),
		AUDIOLINE_4(3);
		public int value;
		private audioLine(int value) {
			this.value = value;
		}
	}
	public static final int AUDIOLINE_MAIN = 0;
	
	// Global Entities lists
	public static List<Entity> ENTITIES = new ArrayList<Entity>();
	public static List<String> entAnimals = new ArrayList<String>();
	public static List<Integer> uIDList = new ArrayList<Integer>();
	public static List<String> uNameList = new ArrayList<String>();
	public static List<String> uTypes = Arrays.asList(new String[]{"VILLAGER","SMITH","CLERCK","FARMER","QUEST MASTER","PRIEST"});
	public static enum ENTITY_NPC{
		HERO_MALE(0),
		HERO_FEMALE(1),
		KNIGHT_MALE(2),
		KNIGHT_FEMALE(3),
		QUEST_MASTER(4),
		VILLAGER_FEMALE(5),
		VILLAGER_MALE(6),
		PROFESSOR(7),
		PIRATE(8);
		public int value;
		private ENTITY_NPC(int value) {
			this.value = value;
		}
	}
	public static enum ENTITY_MONSTER {
		IMP_SMALL(0), 
		RAT_BIG(1);
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
	public static Object[] SETTINGS = new Object[]{
			SETTING_VOLUME, SETTING_LANGUAGE ,SETTING_ANTIALIAS, SETTING_ACCGRAPHICS, SETTING_SHADOW
			};
}
