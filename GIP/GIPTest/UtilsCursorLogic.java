package GIP.GIPTest;

public class UtilsCursorLogic extends Settings{

	public void update() {
		switch(MENU_TYPE) {
		case MENU_INVENTORY:
			updateInventoryCursor();
			break;
		case MENU_TRADE:
			// TODO
			break;
		case MENU_EXIT:
			// TODO
			break;
		}
		// Reset keys
		downPressed = false;
		upPressed = false;
		rightPressed = false;
		leftPressed = false;
	}

	private void updateInventoryCursor() {
		if(inventoryCursor < 9) {
			if(inventoryCursor == 0) {
				if(leftPressed) inventoryCursor = 1;
				if(rightPressed) inventoryCursor = 5;
			} else if (inventoryCursor == 1) {
				if(rightPressed) inventoryCursor = 0;
				if(upPressed) inventoryCursor = 3;
				if(downPressed) inventoryCursor += 1;
			} else if (inventoryCursor == 2) {
				if(rightPressed) inventoryCursor = 6;
				if(upPressed) inventoryCursor -= 1;
				if(downPressed) inventoryCursor += 1;
			} else if (inventoryCursor == 3) {
				if(rightPressed) inventoryCursor = 7;
				if(upPressed) inventoryCursor -= 1;
				if(downPressed) inventoryCursor = 1;
			} else if (inventoryCursor == 4) {
				if(rightPressed) inventoryCursor = 23;
				if(upPressed) inventoryCursor = 7;
				if(downPressed) inventoryCursor += 1;
			} else if (inventoryCursor == 5) {
				if(leftPressed) inventoryCursor = 0;
				if(rightPressed) inventoryCursor = 8;
				if(upPressed) inventoryCursor -= 1;
				if(downPressed) inventoryCursor += 1;
			} else if (inventoryCursor == 6) {
				if(leftPressed) inventoryCursor = 2;
				if(rightPressed) inventoryCursor = 37;
				if(upPressed) inventoryCursor -= 1;
				if(downPressed) inventoryCursor += 1;
			} else if (inventoryCursor == 7) {
				if(leftPressed) inventoryCursor = 3;
				if(rightPressed) inventoryCursor = 44;
				if(upPressed) inventoryCursor -= 1;
				if(downPressed) inventoryCursor = 4;
			} else if (inventoryCursor == 8) {
				if(leftPressed) inventoryCursor = 5;
				if(rightPressed) inventoryCursor = 30;
			}
		} else {
			if(inventoryCursor == 9) {
				if(rightPressed) inventoryCursor += 1;
				if(downPressed) inventoryCursor += 7;
			} else if (inventoryCursor == 50) {
				if(leftPressed) inventoryCursor -= 1;
				if(upPressed) inventoryCursor -= 7;
			} else {
				if(leftPressed) {
					if(inventoryCursor == 23) {
						inventoryCursor = 4;
						return;
					} else if(inventoryCursor == 30) {
						inventoryCursor = 8;
						return;
					} else if(inventoryCursor == 37) {
						inventoryCursor = 6;
						return;
					} else if(inventoryCursor == 44) {
						inventoryCursor = 7;
						return;
					} else {inventoryCursor -= 1;}
				}
				if(rightPressed) inventoryCursor += 1;
				if(upPressed && inventoryCursor - 7 >= 9) inventoryCursor -= 7;
				if(downPressed && inventoryCursor + 7 <= 50) inventoryCursor += 7;
			}
		}

	}

}
