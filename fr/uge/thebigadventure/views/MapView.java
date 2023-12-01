package fr.uge.thebigadventure.views;

import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.views.entities.EntityView;

import java.awt.*;
import java.util.HashMap;

public class MapView {

  private static final HashMap<String, Image> images = new HashMap<>();

  public static Image getImage(String path) {
    return images.computeIfAbsent(path, Toolkit.getDefaultToolkit()::getImage);
  }

  public static void drawMap(GameMap gameMap, Graphics2D graphics2D,
                             int cellSize) {
    gameMap.data().forEach((coord, environmentType) -> {
      EntityView.drawEnvironmentTile(graphics2D,
          environmentType.getImagePath(), coord, cellSize);
    });
  }
}
