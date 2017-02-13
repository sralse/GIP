package GIP.GIPTest;

public class UtilsCursorLogic extends Settings{

	public int maxInventoryCursorSize;
	public boolean hasSelected;
	public Item selectedItem;
	public int ogItemSlot;

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
		if(spacePressed) {
			if(!hasSelected) {
				hasSelected = true;
				selectedItem = player.ITEMS.get(inventoryCursor);
				if(selectedItem == null) hasSelected = false;
				player.removeItem(inventoryCursor);
				ogItemSlot = inventoryCursor;
			} else {
				hasSelected = false;
				player.switchItems(selectedItem, ogItemSlot, inventoryCursor);
				selectedItem = null;
			}
			spacePressed = false;
		}
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
			if(inventoryCursor > 50) {
				if(leftPressed) inventoryCursor = 50;
				if(upPressed) inventoryCursor -= 1;
				if(downPressed) inventoryCursor += 1;
				if(inventoryCursor < 51 && !leftPressed) inventoryCursor = maxInventoryCursorSize;
				if(inventoryCursor > maxInventoryCursorSize) inventoryCursor = 51;
				return;
			}
			if(inventoryCursor == 9) {
				if(rightPressed) inventoryCursor += 1;
				if(downPressed) inventoryCursor += 7;
			} else {
				if(leftPressed && !hasSelected) {
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
				if(leftPressed && hasSelected && inventoryCursor > 9) inventoryCursor -= 1;
				if(rightPressed && !hasSelected) {
					if(inventoryCursor == 15) {
						inventoryCursor = 51;
						return;
					} else if(inventoryCursor == 22) {
						inventoryCursor = 51;
						return;
					} else if(inventoryCursor == 29) {
						inventoryCursor = 51;
						return;
					} else if(inventoryCursor == 36) {
						inventoryCursor = 51;
						return;
					} else if(inventoryCursor == 43) {
						inventoryCursor = 51;
						return;
					} else {inventoryCursor += 1;}
				}
				if(rightPressed && hasSelected && inventoryCursor < 50) inventoryCursor += 1;
				if(upPressed && inventoryCursor - 7 >= 9) inventoryCursor -= 7;
				if(downPressed && inventoryCursor + 7 <= 50) inventoryCursor += 7;
			}
		}
		if(hasSelected) selectedItem.invPOS = inventoryCursor;
	}
}
