package fr.uge.thebigadventure.views;

import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.views.environment.EnvironmentView;

import java.awt.*;

public class MapView {

  public static void drawMap(GameMap gameMap, Graphics2D graphics2D,
                             int cellSize) {
    gameMap.data().forEach((coord, environmentType) -> {
      EnvironmentView.drawEnvironmentTile(graphics2D,
          environmentType.getImagePath(), coord, cellSize);
    });
  }
}
