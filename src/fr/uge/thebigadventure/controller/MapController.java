package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.GameMap;
import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.entity.inventory.Box;
import fr.uge.thebigadventure.model.entity.inventory.InventoryItem;
import fr.uge.thebigadventure.model.entity.inventory.weapon.WeaponInterface;
import fr.uge.thebigadventure.model.entity.personage.Ally;
import fr.uge.thebigadventure.model.entity.personage.Enemy;
import fr.uge.thebigadventure.model.entity.personage.NPC;
import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.type.entity.ObstacleType;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.view.MapView;
import fr.uge.thebigadventure.view.TradeView;
import fr.uge.thebigadventure.view.entity.NPCView;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MapController {

  private static final int NB_TILES_WIDTH = 30;
  private static TradeController tradeController = null;
  private final int nbTilesHeight;
  private final PlayerController playerController;
  private final List<NPCController> npcControllers;
  private final MapView mapView;
  private final int cellSize;
  private final GameMap gameMap;
  private final boolean dryRunActive;
  private boolean currentlySpeaking;

  public MapController(GameMap gameMap, ScreenInfo screenInfo, boolean dryRun) {
    Objects.requireNonNull(gameMap, "Game map cannot be null");
    Objects.requireNonNull(screenInfo, "Screen info cannot be null");
    this.gameMap = gameMap;
    this.cellSize = (int) (screenInfo.getWidth() / NB_TILES_WIDTH);
    nbTilesHeight = (int) (screenInfo.getHeight() / cellSize);
    this.playerController = new PlayerController(gameMap.getPlayer(), gameMap, cellSize, screenInfo);
    this.npcControllers = gameMap.getNpcs().stream().map(npc ->
        new NPCController(npc, new NPCView(npc, cellSize))).toList();
    this.mapView = new MapView(gameMap, NB_TILES_WIDTH, nbTilesHeight, cellSize);
    this.dryRunActive = dryRun;
  }

  public boolean updateNpcControllers() {
    var updated = false;
    for (var npcController : npcControllers) {
      if (npcController.isAlive() && npcController.update(gameMap, dryRunActive))
        updated = true;
    }
    return updated;
  }

  private void pickupItem() {
    var playerPosition = gameMap.getPlayer().position();
    var element = gameMap.entities().get(playerPosition);
    if (element instanceof InventoryItem inventoryItem) {
      if (gameMap.getPlayer().inventory().addItem(inventoryItem))
        gameMap.removeElement(playerPosition);
    }
  }

  private Coordinates playerTargetPosition() {
    var direction = gameMap.getPlayer().getDirection();
    if (direction == null) {
      return null;
    }
    return gameMap.getPlayer().position().move(direction);
  }

  private NPC getNPCInFront() {
    var targetPosition = playerTargetPosition();
    if (targetPosition == null) {
      return null;
    }
    return gameMap.getNpcs().stream()
        .filter(npc -> npc.position().equals(targetPosition))
        .findFirst()
        .orElse(null);
  }

  private Entity getElementInFront() {
    var targetPosition = playerTargetPosition();
    if (targetPosition == null) {
      return null;
    }
    return gameMap.entities().get(targetPosition);
  }

  private void actionOnEnemy(Enemy enemy) {
    var mainHand = gameMap.getPlayer().inventory().mainHand();
    if (mainHand == null || !mainHand.isWeapon()) {
      return;
    }
    var weapon = (WeaponInterface) gameMap.getPlayer().inventory().mainHand();
    if (weapon == null) {
      return;
    }
    enemy.setHealth(enemy.getHealth() - weapon.damage());
    if (enemy.getHealth() <= 0) {
      npcControllers.stream().filter(npcController ->
              npcController.getNpcPosition().equals(enemy.position()))
          .findFirst().ifPresent(NPCController::kill);
      gameMap.removeNpc(enemy);
    }
  }

  private void actionOnAlly(Ally ally) {
    if (ally.text() != null) {
      npcControllers.stream().filter(npcController ->
              npcController.getNpcPosition().equals(ally.position()))
          .findFirst()
          .ifPresent(NPCController::startDialog);
    }
    currentlySpeaking = true;
    if (ally.getTradeTable() == null) {
      return;
    }
    tradeController = new TradeController(ally.getTradeTable(),
        new TradeView(List.copyOf(ally.getTradeTable()), cellSize, nbTilesHeight),
        playerController.getInventoryController(), gameMap.coldEntities());
    tradeController.toggleTradeInventory();
  }

  public void action() {
    var npc = getNPCInFront();
    if (npc != null) {
      switch (npc) {
        case Enemy enemy -> actionOnEnemy(enemy);
        case Ally ally -> actionOnAlly(ally);
      }
      return;
    }
    var element = getElementInFront();
    if (element != null && element.skin() == ObstacleType.TREE
        && gameMap.getPlayer().inventory().mainHand().skin() == InventoryItemRawType.SWORD) {
      gameMap.putElement(playerTargetPosition(), new Box(null, playerTargetPosition(), null));
    }
    playerController.eatMainHand();
  }

  public void movePlayer(Direction direction) {
    playerController.movePlayer(direction);
    currentlySpeaking = false;
  }

  public void toggleInventory() {
    playerController.toggleInventory();
    currentlySpeaking = false;
  }

  private void updateInventoryController(KeyboardController keyboardController) {
    playerController.updateInventoryController(keyboardController);
  }

  private void updateTradeController(KeyboardController keyboardController) {
    if (keyboardController.handleTradeControl(tradeController))
      currentlySpeaking = false;
  }

  public void updateMapController(KeyboardController keyboardController) {
    if (tradeController != null && tradeController.isTradeOpen()) {
      updateTradeController(keyboardController);
      return;
    }
    if (playerController.isInventoryOpen()) {
      updateInventoryController(keyboardController);
      return;
    }
    keyboardController.handleMapControl(this);
    pickupItem();
  }

  public void updateView(Graphics2D graphics2D) throws IOException {
    mapView.clearLastView(graphics2D);
    mapView.drawCenteredMap(graphics2D);
    playerController.updateView(graphics2D);
    for (var npcController : npcControllers) {
      if (npcController.isAlive()) {
        npcController.updateView(graphics2D, currentlySpeaking);
        if (!currentlySpeaking)
          npcController.cancelDialog();
      }
    }
    if (playerController.isInventoryOpen()) {
      playerController.renderInventory(graphics2D);
    }
    if (tradeController != null && tradeController.isTradeOpen()) {
      tradeController.render(graphics2D);
    }
  }

  /**
   * Check if the player is dead
   *
   * @return true if the player is dead, false otherwise
   */
  public boolean isPlayerDead() {
    return gameMap.getPlayer().health() <= 0;
  }

  public void renderDeathMessage(Graphics2D graphics2D) {
    mapView.drawGameOver(graphics2D);
  }
}
