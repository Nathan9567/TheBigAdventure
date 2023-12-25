package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.views.MapView;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MapController {

  private final GameMap gameMap;
  private final PlayerController playerController;
  private final List<NPCController> npcControllers;
  private final MapView mapView = new MapView();

  public MapController(GameMap gameMap, PlayerController playerController, List<NPCController> npcControllers) {
    Objects.requireNonNull(gameMap, "Game map cannot be null");
    this.gameMap = gameMap;
    this.playerController = playerController;
    this.npcControllers = npcControllers;
  }

  public void updateView(Graphics2D graphics2D, int cellSize) throws IOException {
    playerController.clearLastView(graphics2D);
    mapView.drawMap(gameMap, graphics2D, cellSize);
    playerController.updateView(graphics2D);
    for (var npcController : npcControllers) {
      npcController.updateView(graphics2D);
    }
  }
}
