package fr.uge.thebigadventure.views;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.entities.personages.Personage;
import fr.uge.thebigadventure.models.enums.entities.EntityType;
import fr.uge.thebigadventure.views.entities.EntityView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    drawData(gameMap.data(), graphics2D, cellSize, bkgdColor);
    drawElements(gameMap.elements(), graphics2D, cellSize);
    drawPersonages(gameMap.personages(), graphics2D, cellSize);
  }

  private static void drawPersonages(List<Personage> personages, Graphics2D graphics2D, int cellSize) {
    personages.forEach(personage -> {
      try {
        EntityView.drawEntityTile(graphics2D,
                personage.skin(), personage.position(), cellSize);
      } catch (IOException e) {
        throw new IllegalArgumentException("Cannot load image " + personage);
      }
    });
  }

  private static void drawData(Map<Coord, EntityType> data, Graphics2D graphics2D,
                               int cellSize, Color bkgdColor) {
    data.forEach((coord, entityType) -> {
      try {
        EntityView.drawEntityTile(graphics2D,
                entityType, coord, cellSize, bkgdColor);
      } catch (IOException e) {
        throw new IllegalArgumentException("Cannot load image " + entityType);
      }
    });
  }

  private static void drawElements(Map<Coord, Entity> element, Graphics2D graphics2D,
                                   int cellSize) {
    element.values().stream()
            .filter(entity -> entity.position() != null)
            .forEach(entity -> {
              try {
                EntityView.drawEntityTile(graphics2D,
                        entity.skin(), entity.position(), cellSize);
              } catch (IOException e) {
                throw new IllegalArgumentException("Cannot load image " + entity);
              }
            });
  }
}
