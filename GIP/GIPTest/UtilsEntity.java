package GIP.GIPTest;

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
		initMonsters();
		System.out.println(ENTITIES);
	}

	public void initPlayer() {
		player = new EntityPlayer(10, 1, 100, "PLAYER", null);

		player.animationWalk(tx_player, aDefInt);
		if (DEBUG) System.out.println("Player graphics initialised");
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
					Double.parseDouble(ent.get(i * entDataBlockSize + 2)),
					ent.get(i * entDataBlockSize + 3),
					ENTITY_VILLAGER.valueOf(ent.get(i * entDataBlockSize + 4)).value);
			uID.addEntity(npc);
		}
		
	}

	public void initMonsters() {
		List<String> ent = new ArrayList<String>();
		ent = Arrays.asList(uFiles.readFileString("/" + lvlDir + entDir + mapName + "monster_" + mapID + lvlExt).split(splitSymbol));
		for (int i = 0; i < (ent.size() / entDataBlockSize); i++) {
			// Init enum as non const
			int e = ENTITY_MONSTER.valueOf(ent.get(i * entDataBlockSize + 3)).value;
			switch(e) {
			case 0:
				Entity monster = new EntityImp(
						Integer.parseInt(ent.get(i * entDataBlockSize)),
						Integer.parseInt(ent.get(i * entDataBlockSize + 1)),
						Double.parseDouble(ent.get(i * entDataBlockSize + 2)), 
						e);
				uID.addEntity(monster);
				break;
			case 1:
				monster = new EntityRat(
						Integer.parseInt(ent.get(i * entDataBlockSize)),
						Integer.parseInt(ent.get(i * entDataBlockSize + 1)),
						Double.parseDouble(ent.get(i * entDataBlockSize + 2)), 
						e);
				uID.addEntity(monster);
				break;
			case 2:
				monster = new EntitySkeleton(
						Integer.parseInt(ent.get(i * entDataBlockSize)),
						Integer.parseInt(ent.get(i * entDataBlockSize + 1)),
						Double.parseDouble(ent.get(i * entDataBlockSize + 2)), 
						e);
				uID.addEntity(monster);
				break;			

			}

		}

	}

	public void update() {

		updatePlayer();

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
		for (int i = ENTITIES.size() - 1; i >= 0; i--) {
			String type = ENTITIES.get(i).TYPE;
			if(type == "villager") {
				updateVillager(i);
			} else if (type == "monster") {
				updateMonsters(i);
			}
		}

		// Check for a message
		for (int i = 1; i < ENTITIES.size(); i++) {
			if (spacePressed && !messaged && player.getAttackTimer() > 500) {
				msgEntity = ENTITIES.get(i);
				if (ENTITIES.get(i).entityRectangle.intersects(player.entityRectangle) && msgEntity.canInteract) {
					messaged = true;
					spacePressed = false;
					msgEntity.setInteraction(true);
					msgTimer1 = System.currentTimeMillis();
					msgMSG = getMessage();
					uGUI.entityMessage(msgEntity, msgMSG);
					player.resetAttackTimer();
				}
			}
		}
	}

	public String getMessage() {
		return messages.get(randGen.nextInt(messages.size()));
	}

	private void updatePlayer() {
		player.updateAttackTimer();
		if(!player.inAttack) {
			player.animationWalk(tx_player, aDefInt);
		} else {
			player.animationWalk(tx_player + "a", aDefInt);
		}

		if (DEBUG) player.face = "0";
		player.movementCheck(gameLoopTime);

		if(spacePressed) {
			for(int i = 1; i < ENTITIES.size(); i++) {
				if(!ENTITIES.get(i).canInteract 
						&& player.canAttack()
						&& ENTITIES.get(i).HEALTH > 0 
						&& player.entityRectangle.intersects(ENTITIES.get(i).entityRectangle)) {
					ENTITIES.get(i).HEALTH -= player.DMG;
					ENTITIES.get(i).dmgTaken = -1;
					ENTITIES.get(i).inAttack = true;
					player.inAttack = true;
					player.resetAttackTimer();
				}
			}
		}
		
		player.updateHealthTimer();
		
		if(player.getAttackTimer() >= 5000 
				&& player.HEALTH < player.maxHealth 
				&& player.healthCounter >= 2000 
				&& !player.inAttack) {
			player.HEALTH += 0.5d;
			player.healthCounter = 0;
		}

	}

	private void updateVillager(int i) {
		((EntityVillager)ENTITIES.get(i)).doLogic();
	}

	private void updateMonsters(int i) {
		int subType = ENTITIES.get(i).SUBTYPE;

		switch(subType) {
		case 0:
			((EntityImp)ENTITIES.get(i)).doLogic();
			break;
		case 1:
			((EntityRat)ENTITIES.get(i)).doLogic();
			break;
		case 2:
			((EntitySkeleton)ENTITIES.get(i)).doLogic();
			break;
		case 3:
			//((Other)ENTITIES.get(i)).doLogic();
			break;
		}
	}
}
