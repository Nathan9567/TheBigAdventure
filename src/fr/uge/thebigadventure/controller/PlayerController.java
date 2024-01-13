package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.GameMap;
import fr.uge.thebigadventure.model.entity.obstacle.Obstacle;
import fr.uge.thebigadventure.model.entity.personage.Player;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.view.InventoryView;
import fr.uge.thebigadventure.view.entity.PlayerView;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class PlayerController {

  private final Player player;
  private final PlayerView playerView;
  private final GameMap map;

  private final InventoryController inventoryController;

  public PlayerController(Player player, PlayerView playerView, GameMap gameMap) {
    this.player = player;
    this.playerView = playerView;
    this.map = gameMap;
    InventoryView inventoryView = new InventoryView(player.inventory(),
        playerView.cellSize(), playerView.screenInfo());
    this.inventoryController = new InventoryController(player.inventory(), inventoryView);
  }

  private boolean isAllowToMove(Direction direction) {
    var newPosition = player.position().move(direction);
    if (newPosition.notInBounds(map.size())) {
      return false;
    }
    var entityTypeData = map.data().get(newPosition);
    var entityElement = map.elements().get(newPosition);
    var personage = map.getNpcs().stream()
        .filter(npc -> npc.position().equals(newPosition))
        .anyMatch(npc -> npc.skin().isObstacle());
    if (entityTypeData != null && entityTypeData.isObstacle()) {
      return false;
    }
    if (personage) {
      return false;
    }
    if (entityElement != null && entityElement.skin().isObstacle()) {
      return entityElement instanceof Obstacle obstacle && obstacle.itemToUnlock() != null && obstacle.itemToUnlock().looksLike(player.inventory().mainHand());
    }
    return true;
  }

  public void movePlayer(Direction direction) {
    Objects.requireNonNull(direction, "You need a direction to move the player");
    if (player.getDirection() != direction)
      player.setDirection(direction);
    if (!isAllowToMove(direction)) {
      return;
    }
    player.setPosition(player.position().move(direction));
  }

  public void updateView(Graphics2D graphics2D) throws IOException {
    playerView.renderPlayer(graphics2D);
  }

  public void renderInventory(Graphics2D graphics2D) throws IOException {
    inventoryController.updateView(graphics2D);
  }

  public InventoryController getInventoryController() {
    return inventoryController;
  }

  public void eatMainHand() {
    if (player.health() < player.maxHealth()) {
      var givenHealth = inventoryController.eatMainHand();
      player.setCurrentHealth(Math.min(player.health() + givenHealth, player.health()));
    }
  }
}
