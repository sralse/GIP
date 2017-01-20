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
		player = new EntityPlayer(10, 1, 100, "PLAYER", null);
		initVillagers();
	}

	public void initVillagers() {
		
		List<String> entNPC = new ArrayList<String>();
		
		entNPC = Arrays
				.asList(uFiles.readFileString("/" + lvlDir + entDir + mapName + "villager_" + mapID + lvlExt).split(splitSymbol));
		for (int i = 0; i < (entNPC.size() / entDataBlockSize); i++) {
			Entity npc = new EntityVillager(Integer.parseInt(entNPC.get(i * entDataBlockSize)),
					Integer.parseInt(entNPC.get(i * entDataBlockSize + 1)),
					Integer.parseInt(entNPC.get(i * entDataBlockSize + 2)), entNPC.get(i * entDataBlockSize + 3),
					ENTITY_NPC.valueOf(entNPC.get(i * entDataBlockSize + 4)).value);
			ENTITIES.add(npc);
		}
	}
	
	
	public void update() {
		if (messaged) {
			msgTimer2 = System.currentTimeMillis();
			if (msgTimer2 - msgTimer1 > msgTime) {
				messaged = false;
				msgEntity.setInteraction(false);
				return;
			} else {
				message(msgEntity, msgMSG);
			}
		}

		for (int i = 0; i < ENTITIES.size(); i++) {
			ENTITIES.get(i).doLogic();
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
					message(msgEntity, msgMSG);
				}
			}
		}
	}

	public void message(Entity entity, String s) {
		

	}

	public String getMessage() {
		return messages.get((int) ((messages.size() - 1) / (Math.round(Math.random() * 10) + 1)));
	}
}
