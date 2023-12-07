package fr.uge.thebigadventure.views;

import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.views.entities.EntityView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class MapView {

  private static final HashMap<String, BufferedImage> images = new HashMap<>();

  public static BufferedImage getImage(String path) throws IOException {
    BufferedImage image;
    try (var input = MapView.class.getResourceAsStream(path)) {
      if (input == null) {
        throw new IOException("Cannot load resource " + path);
      }
      image = ImageIO.read(input);
    }
    return images.computeIfAbsent(path, p -> image);
  }

  public static void drawMap(GameMap gameMap, Graphics2D graphics2D,
                             int cellSize, Color bkgdColor) {
    gameMap.data().forEach((coord, entityType) -> {
      try {
        EntityView.drawEntityTile(graphics2D,
            entityType.getImagePath(), coord, cellSize, bkgdColor);
      } catch (IOException e) {
        throw new IllegalArgumentException("Cannot load image " + entityType);
      }
    });
  }
}
