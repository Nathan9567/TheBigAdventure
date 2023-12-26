package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.inventory.InventoryItem;
import fr.uge.thebigadventure.views.MapView;
import fr.uge.thebigadventure.views.entities.NPCView;
import fr.uge.thebigadventure.views.entities.PlayerView;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MapController {

  private static final int NB_TILES_WIDTH = 45;

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
    int nbTilesHeight = (int) (screenInfo.getHeight() / cellSize) + 1;
    this.playerController = new PlayerController(gameMap.getPlayer(),
        new PlayerView(gameMap.getPlayer(), cellSize, screenInfo), gameMap);
    this.npcControllers = gameMap.getNpcs().stream().map(npc ->
        new NPCController(npc, new NPCView(npc, cellSize))).toList();
    this.mapView = new MapView(gameMap, NB_TILES_WIDTH, nbTilesHeight, cellSize);
  }

  public boolean updateNpcControllers() {
    var updated = false;
    for (var npcController : npcControllers) {
      if (npcController.update(gameMap))
        updated = true;
    }
    return updated;
  }

  private void pickupItem() {
    var playerPosition = gameMap.getPlayer().position();
    var element = gameMap.elements().get(playerPosition);
    if (element instanceof InventoryItem inventoryItem) {
      if (gameMap.getPlayer().inventory().addItem(inventoryItem))
        gameMap.elements().remove(playerPosition);
    }
  }

  public void updatePlayerController(KeyboardController keyboardController) {
    keyboardController.handlePlayerControl(playerController);
    pickupItem();
  }

  public void updateView(Graphics2D graphics2D) throws IOException {
    mapView.clearLastView(graphics2D);
    mapView.drawPlayerCenteredMap(graphics2D);
    playerController.updateView(graphics2D);
    for (var npcController : npcControllers) {
      npcController.updateView(graphics2D);
    }
    if (playerController.isInventoryOpen()) {
      playerController.renderInventory(graphics2D);
    }
  }
}
