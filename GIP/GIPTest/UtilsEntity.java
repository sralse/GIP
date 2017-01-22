package GIP.GIPTest;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilsEntity extends Settings {
	
	public void init() {
		// init entity msg
		msgVillagerPath = "/level/locals/" + SETTING_LANGUAGE + "/villager_msg.txt";
		messages = uFiles.readFileArray(msgVillagerPath);
		// init entities
		initPlayer();
		initVillagers();
	}
	
	public void initPlayer() {
		player = new EntityPlayer(10, 1, 100, "PLAYER", null);
		
		player.animationWalk(tx_player);
		if (DEBUG) {
			System.out.println("Player graphics initialised");
		}
	}

	public void initVillagers() {
		// Load Villager's messages
		msgVillagerPath = "/level/locals/" + SETTING_LANGUAGE + "/villager_msg.txt";
		messages = uFiles.readFileArray(msgVillagerPath);
		// Load all Villagers from mapData
		List<String> entNPC = new ArrayList<String>();
		entNPC = Arrays.asList(uFiles.readFileString("/" + lvlDir + entDir + mapName + "villager_" + mapID + lvlExt).split(splitSymbol));
		for (int i = 0; i < (entNPC.size() / entDataBlockSize); i++) {
			Entity npc = new EntityVillager(Integer.parseInt(entNPC.get(i * entDataBlockSize)),
					Integer.parseInt(entNPC.get(i * entDataBlockSize + 1)),
					Integer.parseInt(entNPC.get(i * entDataBlockSize + 2)), entNPC.get(i * entDataBlockSize + 3),
					ENTITY_NPC.valueOf(entNPC.get(i * entDataBlockSize + 4)).value);
			ENTITIES.add(npc);
		}
	}
	
	
	public void update(long delta) {
		
		updatePlayer(delta);
		
		if (messaged) {
			msgTimer2 = System.currentTimeMillis();
			if (msgTimer2 - msgTimer1 > msgTime) {
				messaged = false;
				msgEntity.setInteraction(false);
				return;
			} else {
				uGUI.entityMessage(msgEntity, msgMSG);
			}
		}

		for (int i = 0; i < ENTITIES.size(); i++) {
			// Call submethods doLogic (if any)
			switch(ENTITIES.get(i).TYPE) {
			case 1:
				updateVillagers(i);
			case 2:
				updateOther(i);
			}
			
			if (select && !messaged) {
				int ecc = ENTITIES.get(i).getImage().getWidth(null);
				int pcc = player.getImage().getWidth(null);
				double eX1 = ENTITIES.get(i).getX();
				double eY1 = ENTITIES.get(i).getY();
				double pX1 = player.getX();
				double pY1 = player.getY();
				Rectangle entityRectangle = new Rectangle();
				entityRectangle.setBounds((int) eX1, (int) eY1, ecc, ecc);
				Rectangle playerRectangle = new Rectangle();
				playerRectangle.setBounds((int) pX1, (int) pY1, pcc, pcc);
				if (entityRectangle.intersects(playerRectangle)) {
					messaged = true;
					select = false;
					msgEntity = ENTITIES.get(i);
					msgEntity.setInteraction(true);
					msgTimer1 = System.currentTimeMillis();
					msgMSG = getMessage();
					uGUI.entityMessage(msgEntity, msgMSG);
				}
			}
		}
	}

	public String getMessage() {
		return messages.get(randGen.nextInt(messages.size()));
	}

	private void updatePlayer(long delta) {

		player.animationWalk(tx_player);
		if (DEBUG) player.face = "0";
		player.movementCheck(delta);
		
//		if (!player.face.equals(playerFacingOld) || player.mode != player.oldMode) {
//			for (int i = 0; i < playerDirections.length; i++) {
//				if (playerDirections[i].equals(player.face)) {
//					player.setImage(playerImages[i]);
//				}
//			}
//		}
//		// If a certaint image does not exist we draw the default image "DOWN"
//		
//		// Set the new player image
//		playerFacingOld = player.face;
	}

	private void updateVillagers(int i) {
		((EntityVillager)ENTITIES.get(i)).doLogic();
	}
	

	private void updateOther(int i) {
		
	}
}
