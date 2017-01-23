package GIP.GIPTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilsEntity extends Settings {
	
	/** Time Since Last Attack */
	private static int TSLA;
	
	public void init() {
		// init entity msg
		msgVillagerPath = "/level/locals/" + SETTING_LANGUAGE + "/villager_msg.txt";
		messages = uFiles.readFileArray(msgVillagerPath);
		// init entities
		initPlayer();
		initVillagers();
		initImps();
		System.out.println(ENTITIES);
	}
	
	public void initPlayer() {
		player = new EntityPlayer(10, 1, 100, "PLAYER", null);
		
		player.animationWalk(tx_player, aDefInt);
		if (DEBUG) {
			System.out.println("Player graphics initialised");
		}
		((UtilsID) uID).addPlayer(player);
	}

	public void initVillagers() {
		// Load Villager's messages
		msgVillagerPath = "/level/locals/" + SETTING_LANGUAGE + "/villager_msg.txt";
		messages = uFiles.readFileArray(msgVillagerPath);
		// Load all Villagers from mapData
		List<String> ent = new ArrayList<String>();
		ent = Arrays.asList(uFiles.readFileString("/" + lvlDir + entDir + mapName + "villager_" + mapID + lvlExt).split(splitSymbol));
		for (int i = 0; i < (ent.size() / entDataBlockSize); i++) {
			Entity npc = new EntityVillager(
					Integer.parseInt(ent.get(i * entDataBlockSize)),
					Integer.parseInt(ent.get(i * entDataBlockSize + 1)),
					Integer.parseInt(ent.get(i * entDataBlockSize + 2)), 
					ent.get(i * entDataBlockSize + 3),
					ENTITY_VILLAGER.valueOf(ent.get(i * entDataBlockSize + 4)).value);
			uID.addEntity(npc);
		}
	}
	
	public void initImps() {
		List<String> ent = new ArrayList<String>();
		ent = Arrays.asList(uFiles.readFileString("/" + lvlDir + entDir + mapName + "monster_" + mapID + lvlExt).split(splitSymbol));
		for (int i = 0; i < (ent.size() / entDataBlockSize); i++) {
			Entity monster = new EntityImp(
					Integer.parseInt(ent.get(i * entDataBlockSize)),
					Integer.parseInt(ent.get(i * entDataBlockSize + 1)),
					Integer.parseInt(ent.get(i * entDataBlockSize + 2)), 
					ENTITY_MONSTER.valueOf(ent.get(i * entDataBlockSize + 3)).value);
			uID.addEntity(monster);
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

		// Update Logic
		for (int i = 0; i < ENTITIES.size(); i++) {
			int type = ENTITIES.get(i).TYPE;
			if(type == 1) {
				updateVillager(i);
			} else if (type == 2) {
				updateImps(i);
			}
		}

		// Check for a message
		for (int i = 1; i < ENTITIES.size(); i++) {
			if (spacePressed && !messaged && TSLA > 500) {
				msgEntity = ENTITIES.get(i);
				if (ENTITIES.get(i).entityRectangle.intersects(player.entityRectangle) && msgEntity.canInteract) {
					messaged = true;
					spacePressed = false;
					msgEntity.setInteraction(true);
					msgTimer1 = System.currentTimeMillis();
					msgMSG = getMessage();
					uGUI.entityMessage(msgEntity, msgMSG);
					TSLA = 0;
				}
			}
		}
	}

	public String getMessage() {
		return messages.get(randGen.nextInt(messages.size()));
	}

	private void updatePlayer(long delta) {
		TSLA += delta;
		player.animationWalk(tx_player, aDefInt);
		if (DEBUG) player.face = "0";
		player.movementCheck(delta);
		
		if(spacePressed) {
			for(int i = 1; i < ENTITIES.size(); i++) {
				if(!ENTITIES.get(i).canInteract 
						&& TSLA > 250
						&& ENTITIES.get(i).getHealth() > 0 
						&& player.entityRectangle.intersects(ENTITIES.get(i).entityRectangle)) {
					ENTITIES.get(i).HEALTH -= 1;
					TSLA = 0;
				}
			}
		}
		
	}

	private void updateVillager(int i) {
		((EntityVillager)ENTITIES.get(i)).doLogic();
	}
	
	private void updateImps(int i) {
		((EntityImp)ENTITIES.get(i)).doLogic();
	}
}
