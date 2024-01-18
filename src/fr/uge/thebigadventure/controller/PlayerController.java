package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.GameMap;
import fr.uge.thebigadventure.model.entity.obstacle.Obstacle;
import fr.uge.thebigadventure.model.entity.personage.Player;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.view.InventoryView;
import fr.uge.thebigadventure.view.entity.PlayerView;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * The player controller is the controller of the player.
 * It is responsible for the player's movements and the player's inventory.
 */
public class PlayerController {

  private final Player player;
  private final PlayerView playerView;
  private final GameMap map;
  private final InventoryController inventoryController;
  private long lastTime = System.currentTimeMillis();

  /**
   * Create a new player controller.
   * It needs a player, a game map, a cell size and a screen info.
   *
   * @param player     the player
   * @param gameMap    the game map
   * @param cellSize   the cell size of the game
   * @param screenInfo the screen info of the context
   */
  public PlayerController(Player player, GameMap gameMap, int cellSize, ScreenInfo screenInfo) {
    this.player = player;
    this.playerView = new PlayerView(player, cellSize);
    this.map = gameMap;
    InventoryView inventoryView = new InventoryView(player.inventory(), cellSize, screenInfo);
    this.inventoryController = new InventoryController(player.inventory(), inventoryView);
  }

  private boolean isAllowToMove(Direction direction) {
    var newPosition = player.position().move(direction);
    if (newPosition.notInBounds(map.size())) {
      return false;
    }
    var entityTypeData = map.data().get(newPosition);
    var entityElement = map.entities().get(newPosition);
    var personage = map.getNpcs().stream()
        .anyMatch(npc -> npc.position().equals(newPosition) && npc.skin().isObstacle());
    if ((entityTypeData != null && entityTypeData.isObstacle()) || personage) {
      return false;
    }
    if (entityElement instanceof Obstacle obstacle) {
      return obstacle.itemToUnlock() != null &&
          obstacle.itemToUnlock().looksLike(player.inventory().mainHand());
    }
    return true;
  }

  /**
   * Move the player in the direction given in parameter.
   * If the player can't move in this direction, it does nothing.
   * We have been added a cool down of 100ms between each movement.
   *
   * @param direction the direction to move the player
   */
  public void movePlayer(Direction direction) {
    Objects.requireNonNull(direction, "You need a direction to move the player");
    var currentTime = System.currentTimeMillis();
    if (currentTime - lastTime < 100) {
      return;
    }
    if (player.getDirection() != direction)
      player.setDirection(direction);
    if (!isAllowToMove(direction)) {
      return;
    }
    lastTime = currentTime;
    player.setPosition(player.position().move(direction));
  }

  /**
   * Update the player view. It needs a graphics 2D to render the player.
   *
   * @param graphics2D the graphics 2D
   * @throws IOException if the player view can't be rendered
   */
  public void updateView(Graphics2D graphics2D) throws IOException {
    playerView.renderPlayer(graphics2D);
  }

  /**
   * Render the player inventory. It needs a graphics 2D to render the inventory.
   *
   * @param graphics2D the graphics 2D
   * @throws IOException if the inventory can't be rendered
   */
  public void renderInventory(Graphics2D graphics2D) throws IOException {
    inventoryController.updateView(graphics2D);
  }

  /**
   * Check if the player inventory is open.
   *
   * @return true if the player inventory is open, false otherwise
   */
  public boolean isInventoryOpen() {
    return inventoryController.isInventoryOpen();
  }

  /**
   * Toggle the player inventory.
   */
  public void toggleInventory() {
    inventoryController.toggleInventory();
  }

  /**
   * Update the inventory controller.
   * It needs a keyboard controller to handle the inventory control.
   *
   * @param keyboardController the keyboard controller
   */
  public void updateInventoryController(KeyboardController keyboardController) {
    keyboardController.handleInventoryControl(inventoryController);
  }

  /**
   * Eat the main hand of the player.
   * If the player is full life, it does nothing and if the player is not,
   * it will eat the main hand and give the food supply of the main hand
   * to the player modulating by the max health of the player.
   */
  public void eatMainHand() {
    if (player.health() < player.maxHealth()) {
      var givenHealth = inventoryController.eatMainHand();
      givenHealth = Math.min(player.health() + givenHealth, player.maxHealth());
      player.setCurrentHealth(givenHealth);
    }
  }

  /**
   * Get the player inventory controller.
   *
   * @return The player inventory controller.
   */
  public InventoryController getInventoryController() {
    return inventoryController;
  }
}
