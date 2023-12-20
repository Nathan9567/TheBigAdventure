package fr.uge.thebigadventure.views;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.entities.personages.Personage;
import fr.uge.thebigadventure.models.enums.entities.EntityType;
import fr.uge.thebigadventure.views.entities.EntityView;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MapView {

  private final EntityView entityView = new EntityView();

  private void drawData(Map<Coordinates, EntityType> data, Graphics2D graphics2D,
                        int cellSize, Color bkgdColor) {
    data.forEach((coordinates, entityType) -> {
      try {
        entityView.drawEntityTile(graphics2D,
            entityType, coordinates, cellSize, bkgdColor);
      } catch (IOException e) {
        throw new IllegalArgumentException("Cannot load image " + entityType);
      }
    });
  }

  public void drawMap(GameMap gameMap, Graphics2D graphics2D,
                      int cellSize, Color bkgdColor) {
    drawData(gameMap.data(), graphics2D, cellSize, bkgdColor);
    drawElements(gameMap.elements(), graphics2D, cellSize);
    drawPersonages(gameMap.personages(), graphics2D, cellSize);
  }

  private void drawPersonages(List<Personage> personages, Graphics2D graphics2D, int cellSize) {
    personages.forEach(personage -> {
      try {
        entityView.drawEntityTile(graphics2D,
            personage.skin(), personage.position(), cellSize);
      } catch (IOException e) {
        throw new IllegalArgumentException("Cannot load image " + personage);
      }
    });
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
