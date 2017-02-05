package GIP.GIPTest;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A class to handle keyboard input from the user. The class handles both
 * dynamic input during game play, i.e. left/right and shoot, and more static
 * type input (i.e. press any key to continue)
 * 
 * This has been implemented as an inner class more through habbit then anything
 * else. Its perfectly normal to implement this as seperate class if slight less
 * convienient.
 * 
 * @author Kevin Glass
 */
public class Input implements KeyListener {

	/**
	 * The number of key presses we've had while waiting for an "any key" press
	 */
	// int pressCount = 1;
	/**
	 * Notification from AWT that a key has been pressed. Note that a key being
	 * pressed is equal to being pushed down but *NOT* released. Thats where
	 * keyTyped() comes in.
	 *
	 * @param e
	 *            The details of the key that was pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// if we're waiting for an "any key" typed then we don't
		// want to do anything with just a "press"
		if (Settings.waitingForKeyPress) {
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			Settings.leftPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			Settings.rightPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			Settings.upPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			Settings.downPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			if (Settings.DEBUG) {
				Settings.DEBUG = false;
			} else {
				Settings.DEBUG = true;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_I) {
			if (Settings.INFO) {
				Settings.INFO = false;
			} else {
				Settings.INFO = true;
			}
		}
		if ((e.getKeyCode() == KeyEvent.VK_SPACE) || (e.getKeyCode() == KeyEvent.VK_ENTER)) {
			Settings.spacePressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Settings.escape = true;
		}
		if (Settings.DEBUG && Settings.gameRunning) {
			if (Settings.player.HEALTH > 0) {
				if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
					Settings.player.HEALTH -= 1;
				}
			}
			if (Settings.player.HEALTH < 100) {
				if (e.getKeyCode() == KeyEvent.VK_ADD) {
					Settings.player.HEALTH += 1;
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_C) {
			Settings.slot1C = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_V) {
			Settings.slot2V = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_B) {
			Settings.slot3B = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_N) {
			Settings.slot4N = true;
		}
		return;

	}

	/**
	 * Notification from AWT that a key has been released.
	 *
	 * @param e
	 *            The details of the key that was released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// if we're waiting for an "any key" typed then we don't
		// want to do anything with just a "released"
		if (Settings.waitingForKeyPress) {
			return;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			Settings.leftPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			Settings.rightPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			Settings.upPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			Settings.downPressed = false;
		}
		if ((e.getKeyCode() == KeyEvent.VK_SPACE) || (e.getKeyCode() == KeyEvent.VK_ENTER)) {
			Settings.spacePressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Settings.escape = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_C) {
			Settings.slot1C = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_V) {
			Settings.slot2V = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_B) {
			Settings.slot3B = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_N) {
			Settings.slot4N = false;
		}
		return;

	}

	/**
	 * Notification from AWT that a key has been typed. Note that typing a key
	 * means to both press and then release it.
	 *
	 * @param e
	 *            The details of the key that was typed.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// if we're waiting for a "any key" type then
		// check if we've recieved any recently. We may
		// have had a keyType() event from the user releasing
		// the shoot or move keys, hence the use of the "pressCount"
		// counter.
		if (Settings.waitingForKeyPress) {

			if (Settings.pressCount == 1) {
				// since we've now recieved our key typed
				// event we can mark it as such and start
				// our new game
				Settings.waitingForKeyPress = false;
				// Game.startGame();
				Settings.pressCount = 0;
			} else {
				Settings.pressCount++;
			}
		}

		// if we hit escape, then quit the game
		// if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
		// System.exit(0);
		// }

		return;

	}
	
	public static void init() {
		Settings.leftPressed = false;
		Settings.rightPressed = false;
		Settings.upPressed = false;
		Settings.downPressed = false;
		Settings.slot1C = false;
		Settings.slot2V = false;
		Settings.slot3B = false;
		Settings.slot4N = false;
		Settings.spacePressed = false;
	}
}
