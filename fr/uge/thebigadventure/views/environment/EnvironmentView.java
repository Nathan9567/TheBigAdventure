package fr.uge.thebigadventure.views.environment;

import fr.uge.thebigadventure.models.Coord;

import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

public class EnvironmentView {
  private static final HashMap<String, Image> images = new HashMap<>();

  private static Image getImage(String path) {
    return images.computeIfAbsent(path, Toolkit.getDefaultToolkit()::getImage);
  }

  public static void drawEnvironmentTile(Graphics2D graphics2D,
                                         String environmentTilePath,
                                         Coord coord, int tileSize) {
    Image image = getImage(environmentTilePath);
    Objects.requireNonNull(image);
    graphics2D.drawImage(image, coord.x() * tileSize, coord.y() * tileSize, tileSize,
        tileSize,
        null);
  }

}
