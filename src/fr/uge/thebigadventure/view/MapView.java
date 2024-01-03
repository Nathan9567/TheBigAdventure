package fr.uge.thebigadventure.view;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.GameMap;
import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.enums.entity.EntityType;
import fr.uge.thebigadventure.view.entity.EntityView;

import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public record MapView(GameMap gameMap, int nbCellsWidth, int nbCellsHeight,
                      int cellSize) {

  private static final EntityView entityView = new EntityView();
  private static int northWestCornerX = 0;
  private static int northWestCornerY = 0;

  public static Coordinates coordinatesToPlayerCenteredMapCoordinates(Coordinates coordinates) {
    return new Coordinates(coordinates.x() - northWestCornerX,
        coordinates.y() - northWestCornerY);
  }

  private void coordinatesAccordingToPlayer() {
    var playerPosition = gameMap.getPlayer().position();
    northWestCornerX = playerPosition.x() - nbCellsWidth / 2;
    northWestCornerY = playerPosition.y() - nbCellsHeight / 2;
    if (northWestCornerX < 0) {
      northWestCornerX = 0;
    }
    if (northWestCornerY < 0) {
      northWestCornerY = 0;
    }
    if (northWestCornerX + nbCellsWidth > gameMap.size().width()) {
      northWestCornerX = gameMap.size().width() - nbCellsWidth;
    }
    if (northWestCornerY + nbCellsHeight > gameMap.size().height()) {
      northWestCornerY = gameMap.size().height() - nbCellsHeight;
    }
  }

  private boolean isInPlayerCenteredMap(Coordinates coordinates) {
    return coordinates.x() >= northWestCornerX
        && coordinates.x() < northWestCornerX + nbCellsWidth
        && coordinates.y() >= northWestCornerY
        && coordinates.y() < northWestCornerY + nbCellsHeight;
  }

  private void drawPlayerCenteredData(Map<Coordinates,
      EntityType> dataset, Graphics2D graphics2D) throws IOException {
    for (var data : dataset.entrySet()) {
      Coordinates coordinates = data.getKey();
      EntityType entityType = data.getValue();
      if (isInPlayerCenteredMap(coordinates)) {
        entityView.drawEntityTile(graphics2D,
            entityType, coordinatesToPlayerCenteredMapCoordinates(coordinates),
            cellSize);
      }
    }
  }

  private void drawPlayerCenteredElements(Map<Coordinates,
      Entity> element, Graphics2D graphics2D) throws IOException {
    for (var entity : element.values()) {
      Coordinates coordinates = entity.position();
      if (coordinates != null && isInPlayerCenteredMap(coordinates)) {
        entityView.drawEntityTile(graphics2D, entity.skin(),
            coordinatesToPlayerCenteredMapCoordinates(coordinates), cellSize);
      }
    }
  }

  public void clearLastView(Graphics2D graphics2D) {
    Objects.requireNonNull(graphics2D, "You need a graphics2D to clear the view in.");
    graphics2D.clearRect(0, 0, nbCellsWidth * cellSize,
        nbCellsHeight * cellSize);
  }

  public void drawPlayerCenteredMap(Graphics2D graphics2D) throws IOException {
    Objects.requireNonNull(graphics2D, "You need a graphics2D to draw the map in.");
    coordinatesAccordingToPlayer();
    drawPlayerCenteredData(gameMap.data(), graphics2D);
    drawPlayerCenteredElements(gameMap.elements(), graphics2D);
  }

}
