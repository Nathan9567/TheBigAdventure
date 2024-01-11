package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.GameMap;
import fr.uge.thebigadventure.model.entity.personage.NPC;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.view.entity.NPCView;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class NPCController {

  private final NPCView npcView;
  private final NPC npc;
  private final boolean isDialogOpen = false;
  private long lastTime = 0;
  private boolean isDead = false;

  public NPCController(NPC npc, NPCView npcView) {
    this.npc = npc;
    this.npcView = npcView;
  }

  /**
   * Kill the NPC
   */
  public void kill() {
    isDead = true;
  }

  /**
   * Check if the NPC is alive
   *
   * @return true if the NPC is alive, false otherwise
   */
  public boolean isAlive() {
    return !isDead;
  }

  /**
   * Get the NPC position
   *
   * @return the NPC position
   */
  public Coordinates getNpcPosition() {
    return npc.position();
  }

  /**
   * Update the NPC position
   *
   * @param gameMap the game map
   * @return true if the NPC has been updated, false otherwise
   */
  public boolean update(GameMap gameMap) {
    long delay = 1000;
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastTime <= delay) {
      return false;
    }
    lastTime = currentTime;
    randomMove(gameMap);
    return true;
  }

  private boolean isValidMove(Direction direction, GameMap gameMap) {
    var newPosition = npc.position().move(direction);
    if (newPosition.notInBounds(gameMap.size()) || npc.getZone() == null ||
        !npc.getZone().contains(newPosition) ||
        gameMap.getPlayer().position().equals(newPosition)) {
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

  /**
   * Render the NPC
   *
   * @param graphics2D the graphics
   * @throws IOException if the image cannot be loaded
   */
  public void updateView(Graphics2D graphics2D) throws IOException {
    npcView.renderNPC(graphics2D);
  }
}
