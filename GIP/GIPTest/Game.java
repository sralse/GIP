package GIP.GIPTest;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The main hook of our game. This class with both act as a manager
 * for the display and central mediator for the game logic. 
 * 
 * Display management will consist of a loop that cycles round all
 * entities in the game asking them to move and then drawing them
 * in the appropriate place. With the help of an inner class it
 * will also allow the player to control the main ship.
 * 
 * As a mediator it will be informed when entities within our game
 * detect events (e.g. alient killed, played died) and will take
 * appropriate game actions.
 * 
 * Based on SpaceInvaders 101 tutorial.
 * 
 * @author Lars Carr�
 * 
 * TODO:
 * +Controller support
 * +Player GUI
 * +Entity WalkUpdate() Thread/function
 * +Game Objects
 * +Game Entity
 * +Player sprint
 * +Game Scaling
 * +Clean up main class
 * +Sync uID and ENTITIES
 */
@SuppressWarnings("serial")
public class Game extends Canvas {
	/** The scaling level of the images or zoom */
	public static final int SCALE = 1;
	/** The standard screen width*/
	public static final int screenWidthTiles = Settings.screenWidth / 16;
	/** The standard screen width*/
	public static final int screenHeightTiles = Settings.screenHeight / 16;
	
	/**
	 * Construct our game and set it running.
	 */
	public void initWindow() {
		// create a frame to contain our game
		JFrame frame = new JFrame(Settings.TITLE);
		// get hold the content of the frame and set up the resolution of the game
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(Settings.screenWidth - 10, Settings.screenHeight - 10));
		panel.setLayout(null);
		// setup our canvas size and put it into the content of the frame
		setBounds(0,0,Settings.screenWidth,Settings.screenHeight + 10);
		panel.add(this);
		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		setIgnoreRepaint(true);
		// finally make the window visible
		frame.setIconImage(Settings.dynamics.loadImage(Settings.ICON));
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		// add a listener to respond to the user closing the window. If they
		// do we'd like to exit the game
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// add a key input system (defined below) to our canvas
		// so we can respond to key pressed
		addKeyListener(new Input());
		setFocusable(true);
		// request the focus so key events come to us
		requestFocus();
		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		if(Settings.SETTING_ACCGRAPHICS) {
			createBufferStrategy(2);
		} else {
			createBufferStrategy(1);
		}
		
		Settings.graphicsBuffer = getBufferStrategy();

	}

	
	public void initGame() {
		MapUtils.init();
		Settings.dynamics.init();
		Settings.leftPressed = false;
		Settings.rightPressed = false;
		Settings.upPressed = false;
		Settings.downPressed = false;
		Settings.DEBUG = false;
		Settings.INFO = false;
		Settings.gameRunning = true;
		//firePressed = false;	
	}
	
	
	/**
	 * The main game loop. This loop is running during all game
	 * play as is responsible for the following activities:
	 * <p>
	 * - Working out the speed of the game loop to update moves
	 * - Moving the game entities
	 * - Drawing the screen contents (entities, text)
	 * - Updating game events
	 * - Checking Input
	 * <p>
	 */
	public void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();
		Settings.dynamics.musicStop(Settings.audioLine.AUDIOLINE_MAIN);
		
		// keep looping round til the game ends
		while (Settings.gameRunning) {
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			int delta = (int) (System.currentTimeMillis() - lastLoopTime);
			lastLoopTime = System.currentTimeMillis();
			// Dynamic update, has to hold everything graphical
			Settings.dynamics.getUpdate(Settings.g, delta);
			// Audio
			if(!Settings.audioLine1.isAlive()) {
				Settings.dynamics.musicPlay(Settings.audioTrack.AUDIO_TRACK_GAME_00.value, Settings.audioLine.AUDIOLINE_MAIN);
			}
			// FPS And thread limiter
			try { Thread.yield();} catch (Exception e) {}
		}
		
		Settings.dynamics.musicStop(Settings.audioLine.AUDIOLINE_MAIN);
	}
}