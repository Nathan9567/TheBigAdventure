package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.GameMap;
import fr.uge.thebigadventure.model.entity.inventory.InventoryItem;
import fr.uge.thebigadventure.model.entity.inventory.weapon.WeaponInterface;
import fr.uge.thebigadventure.model.entity.personage.Ally;
import fr.uge.thebigadventure.model.entity.personage.Enemy;
import fr.uge.thebigadventure.model.entity.personage.NPC;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.view.MapView;
import fr.uge.thebigadventure.view.entity.NPCView;
import fr.uge.thebigadventure.view.entity.PlayerView;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MapController {

  private static final int NB_TILES_WIDTH = 30;

  private final PlayerController playerController;
  private final List<NPCController> npcControllers;
  private final MapView mapView;
  private final int cellSize;
  private final GameMap gameMap;

  public MapController(GameMap gameMap, ScreenInfo screenInfo) {
    Objects.requireNonNull(gameMap, "Game map cannot be null");
    Objects.requireNonNull(screenInfo, "Screen info cannot be null");
    this.gameMap = gameMap;
    this.cellSize = (int) (screenInfo.getWidth() / NB_TILES_WIDTH);
    int nbTilesHeight = (int) (screenInfo.getHeight() / cellSize);
    this.playerController = new PlayerController(gameMap.getPlayer(),
        new PlayerView(gameMap.getPlayer(), cellSize, screenInfo), gameMap);
    this.npcControllers = gameMap.getNpcs().stream().map(npc ->
        new NPCController(npc, new NPCView(npc, cellSize))).toList();
    this.mapView = new MapView(gameMap, NB_TILES_WIDTH, nbTilesHeight, cellSize);
  }

  public boolean updateNpcControllers() {
    var updated = false;
    for (var npcController : npcControllers) {
      if (npcController.isAlive() && npcController.update(gameMap))
        updated = true;
    }
    return updated;
  }

  public boolean isInventoryOpen() {
    return playerController.getInventoryController().isInventoryOpen();
  }

  private void pickupItem() {
    var playerPosition = gameMap.getPlayer().position();
    var element = gameMap.elements().get(playerPosition);
    if (element instanceof InventoryItem inventoryItem) {
      if (gameMap.getPlayer().inventory().addItem(inventoryItem))
        gameMap.elements().remove(playerPosition);
    }
  }

  private NPC getNPCInFront() {
    var targetPosition = gameMap.getPlayer().position().move(gameMap.getPlayer().getDirection());
    return gameMap.getNpcs().stream()
        .filter(npc -> npc.position().equals(targetPosition))
        .findFirst()
        .orElse(null);
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
  }

  public void action() {
    switch (getNPCInFront()) {
      case Enemy enemy -> actionOnEnemy(enemy);
      case Ally ally -> actionOnAlly(ally);
      case null, default -> {
      }
    }
  }

  public void movePlayer(Direction direction) {
    playerController.movePlayer(direction);
  }

  public void toggleInventory() {
    playerController.getInventoryController().toggleInventory();
  }

  public void updateMapController(KeyboardController keyboardController) {
    keyboardController.handleMapControl(this);
    pickupItem();
  }

  public void updateInventoryController(KeyboardController keyboardController) {
    keyboardController.handleInventoryControl(playerController.getInventoryController());
  }

  public void updateView(Graphics2D graphics2D) throws IOException {
    mapView.clearLastView(graphics2D);
    mapView.drawPlayerCenteredMap(graphics2D);
    playerController.updateView(graphics2D);
    for (var npcController : npcControllers) {
      if (npcController.isAlive()) {
        npcController.updateView(graphics2D);
      }
    }
    if (playerController.getInventoryController().isInventoryOpen()) {
      playerController.renderInventory(graphics2D);
    }
  }
}
