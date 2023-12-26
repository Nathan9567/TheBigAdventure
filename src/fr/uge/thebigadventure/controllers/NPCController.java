package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.personages.NPC;
import fr.uge.thebigadventure.models.enums.utils.Direction;
import fr.uge.thebigadventure.views.entities.NPCView;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class NPCController {

  private final NPCView npcView;
  private final NPC npc;
  private long lastTime = 0;

  public NPCController(NPC npc, NPCView npcView) {
    this.npc = npc;
    this.npcView = npcView;
  }

  public void update(GameMap gameMap) {
    long delay = 1000;
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastTime <= delay) {
      return;
    }
    lastTime = currentTime;
    randomMove(gameMap);
  }

  private boolean isValidMove(Direction direction, GameMap gameMap) {
    var newPosition = npc.position().move(direction);
    if (newPosition.notInBounds(gameMap.size()) ||
        !npc.getZone().contains(newPosition)) {
      return false;
    }
    var entityType = gameMap.data().get(newPosition);
    return entityType == null || !entityType.isObstacle();
  }

  private Direction randomDirection() {
    var random = new Random();
    var randomInt = random.nextInt(5);
    return switch (randomInt) {
      case 0 -> Direction.NORTH;
      case 1 -> Direction.SOUTH;
      case 2 -> Direction.EAST;
      case 3 -> Direction.WEST;
      case 4 -> null;
      default ->
          throw new IllegalStateException("Unexpected value: " + randomInt);
    };
  }

  private void randomMove(GameMap gameMap) {
    var direction = randomDirection();
    if (direction == null) {
      return;
    }
    if (!isValidMove(direction, gameMap)) {
      randomMove(gameMap);
      return;
    }
    npc.setPosition(npc.position().move(direction));
  }

  public void updateView(Graphics2D graphics2D) throws IOException {
    npcView.renderNPC(graphics2D);
  }
}
