package fr.uge.thebigadventure.views;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.enums.entities.EntityType;
import fr.uge.thebigadventure.views.entities.EntityView;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class MapView {

  private final EntityView entityView = new EntityView();

  private void drawData(Map<Coordinates, EntityType> data, Graphics2D graphics2D,
                        int cellSize) {
    data.forEach((coordinates, entityType) -> {
      try {
        entityView.drawEntityTile(graphics2D,
            entityType, coordinates, cellSize);
      } catch (IOException e) {
        throw new IllegalArgumentException("Cannot load image " + entityType);
      }
    });
  }

  public void resetCell(GameMap gameMap, Graphics2D graphics2D,
                        int cellSize, Coordinates coordinates) throws IOException {
    var dataTile = gameMap.data().get(coordinates);
    var elementTile = gameMap.elements().get(coordinates);
    if (dataTile != null) {
      System.out.println("test");
      entityView.drawEntityTile(graphics2D, dataTile, coordinates, cellSize);
    }
    if (elementTile != null)
      entityView.drawEntityTile(graphics2D, elementTile.skin(), coordinates, cellSize);
  }

  public void drawMap(GameMap gameMap, Graphics2D graphics2D,
                      int cellSize) {
    drawData(gameMap.data(), graphics2D, cellSize);
    drawElements(gameMap.elements(), graphics2D, cellSize);
  }

  private void drawElements(Map<Coordinates, Entity> element, Graphics2D graphics2D,
                            int cellSize) {
    element.values().stream()
        .filter(entity -> entity.position() != null)
        .forEach(entity -> {
          try {
            entityView.drawEntityTile(graphics2D,
                entity.skin(), entity.position(), cellSize);
          } catch (IOException e) {
            throw new IllegalArgumentException("Cannot load image " + entity);
          }
        });
  }
}
