package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.GameMap;
import fr.uge.thebigadventure.model.entity.personage.Ally;
import fr.uge.thebigadventure.model.entity.personage.Enemy;
import fr.uge.thebigadventure.model.entity.personage.NPC;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.view.entity.NPCView;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class NPCController {

  private static final Random random = new Random();
  private final NPCView npcView;
  private final NPC npc;
  private int currentDialogPosition = -1;
  private long lastTimeMove = 0;
  private long lastSpeakTime = 0;
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
   * @param gameMap        the game map
   * @param isDryRunActive true if the dry run is active, false otherwise
   * @return true if the NPC has been updated, false otherwise
   */
  public boolean update(GameMap gameMap, boolean isDryRunActive) {
    return switch (npc) {
      case Ally ignored ->
          updateAllySpeak() || (!isDryRunActive && updateNPCMovement(gameMap));
      default -> !isDryRunActive && updateNPCMovement(gameMap);
    };
  }

  private boolean updateAllySpeak() {
    long delay = 450;
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastSpeakTime <= delay) {
      return false;
    }
    lastSpeakTime = currentTime;
    if (currentDialogPosition != -1) {
      currentDialogPosition++;
    }
    return true;
  }

  private boolean updateNPCMovement(GameMap gameMap) {
    long delay = 1000;
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastTimeMove <= delay) {
      return false;
    }
    lastTimeMove = currentTime;
    randomMove(gameMap);
    action(gameMap);
    return true;
  }

  private void action(GameMap gameMap) {
    if (npc.isEnemy()) {
      var player = gameMap.getPlayer();
      if (player.position().isAdjacent(npc.position())) {
        player.setCurrentHealth(player.health() - ((Enemy) npc).getDamage());
      }
    }
  }

  private boolean isValidMove(Direction direction, GameMap gameMap) {
    var newPosition = npc.position().move(direction);
    if (newPosition.notInBounds(gameMap.size()) || npc.getZone() == null ||
        !npc.getZone().contains(newPosition) ||
        gameMap.getPlayer().position().equals(newPosition) || gameMap.getNpcs().stream()
        .anyMatch(npc -> npc.position().equals(newPosition))) {
      return false;
    }
    var entityType = gameMap.data().get(newPosition);
    return entityType == null || !entityType.isObstacle();
  }

  private Direction randomDirection() {
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

  private Direction playerDependentDirection(GameMap gameMap, boolean follow) {
    var randomMove = random.nextInt(5);
    var playerPosition = gameMap.getPlayer().position();
    var enemyPosition = npc.position();
    var isAtWest = follow ^ (enemyPosition.x() < playerPosition.x());
    var isAtNorth = follow ^ (enemyPosition.y() < playerPosition.y());
    return switch (randomMove) {
      case 0, 1 -> isAtWest ? Direction.WEST : Direction.EAST;
      case 2, 3 -> isAtNorth ? Direction.NORTH : Direction.SOUTH;
      default -> null;
    };
  }

  private void randomMove(GameMap gameMap) {
    var direction = switch (npc) {
      case Enemy enemy -> switch (enemy.getBehavior()) {
        case STROLL -> randomDirection();
        case SHY -> playerDependentDirection(gameMap, false);
        case AGGRESSIVE -> playerDependentDirection(gameMap, true);
      };
      default -> randomDirection();
    };
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
   * Start the dialog with the NPC
   */
  public void startDialog() {
    currentDialogPosition = 0;
  }

  /**
   * Render the NPC
   *
   * @param graphics2D the graphics
   * @throws IOException if the image cannot be loaded
   */
  public void updateView(Graphics2D graphics2D) throws IOException {
    if (npcView.renderNPC(graphics2D, currentDialogPosition)) {
      currentDialogPosition = -1;
    }
  }
}
