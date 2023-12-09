package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.personages.NPC;
import fr.uge.thebigadventure.models.enums.utils.Direction;
import fr.uge.thebigadventure.views.entities.NPCView;

import java.util.Random;

public class NPCController {

  private final long delay = 1000;
  private final NPCView npcView;
  private final NPC npc;
  private long lastTime = 0;

  public NPCController(NPC npc, NPCView npcView) {
    this.npc = npc;
    this.npcView = npcView;
  }

  public boolean update(GameMap gameMap) {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastTime <= delay) {
      return false;
    }
    System.out.println("NPC update");
    lastTime = currentTime;
    move(gameMap);
    return true;
  }

  private boolean isValidMove(Direction direction, GameMap gameMap) {
    var newPosition = npc.position().move(direction);
    if (!newPosition.inBounds(gameMap.size()) ||
            !npc.getZone().contains(newPosition)) {
      return false;
    }
    var entityType = gameMap.data().get(newPosition);
    return entityType == null || !entityType.isObstacle();
  }

  private void move(GameMap gameMap) {
    Random random = new Random();
    var direction = switch (random.nextInt(4)) {
      case 0 -> Direction.NORTH;
      case 1 -> Direction.SOUTH;
      case 2 -> Direction.EAST;
      case 3 -> Direction.WEST;
      default -> throw new IllegalArgumentException("Unexpected value: " + random.nextInt());
    };
    if (!isValidMove(direction, gameMap)) {
      move(gameMap);
      return;
    }
    npc.setPosition(npc.position().move(direction));
  }

}
