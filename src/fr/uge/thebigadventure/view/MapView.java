package fr.uge.thebigadventure.view;

import fr.uge.thebigadventure.model.GameMap;
import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.type.entity.EntityType;
import fr.uge.thebigadventure.model.utils.Coordinates;
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

  private static void coordinatesAccordingToPlayer(GameMap gameMap, int nbCellsWidth,
                                                   int nbCellsHeight) {
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
        && coordinates.x() < northWestCornerX + (nbCellsWidth + 1)
        && coordinates.y() >= northWestCornerY
        && coordinates.y() < northWestCornerY + (nbCellsHeight + 1);
  }

  private void drawPlayerCenteredData(Map<Coordinates,
      EntityType> dataset, Graphics2D graphics2D) throws IOException {
    for (var data : dataset.entrySet()) {
      Coordinates coordinates = data.getKey();
      EntityType entityType = data.getValue();
      if (isInPlayerCenteredMap(coordinates)) {
        entityView.drawEntityTileInMap(graphics2D,
            entityType, coordinatesToPlayerCenteredMapCoordinates(coordinates),
            cellSize);
      }
    }
  }

  private void drawPlayerCenteredEntities(Map<Coordinates,
      Entity> element, Graphics2D graphics2D) throws IOException {
    for (var entity : element.values()) {
      Coordinates coordinates = entity.position();
      if (coordinates != null && isInPlayerCenteredMap(coordinates)) {
        entityView.drawEntityTileInMap(graphics2D, entity.skin(),
            coordinatesToPlayerCenteredMapCoordinates(coordinates), cellSize);
      }
    }
  }

  public void clearLastView(Graphics2D graphics2D) {
    Objects.requireNonNull(graphics2D, "You need a graphics2D to clear the view in.");
    graphics2D.clearRect(0, 0, nbCellsWidth * cellSize, (nbCellsHeight + 1) * cellSize);
  }

  public void drawCenteredMap(Graphics2D graphics2D) throws IOException {
    Objects.requireNonNull(graphics2D, "You need a graphics2D to draw the map in.");
    coordinatesAccordingToPlayer(gameMap, nbCellsWidth, nbCellsHeight);
    drawPlayerCenteredData(gameMap.data(), graphics2D);
    drawPlayerCenteredEntities(gameMap.entities(), graphics2D);
  }

  public void drawGameOver(Graphics2D graphics2D) {
    Objects.requireNonNull(graphics2D, "You need a graphics2D to draw the map in.");
    // Draw centered text in the middle of the screen
    String gameOverText = "GAME OVER";
    graphics2D.setFont(new Font("TimesRoman", Font.PLAIN, 50));
    FontMetrics fontMetrics = graphics2D.getFontMetrics();
    fontMetrics.stringWidth(gameOverText);
    graphics2D.setColor(Color.RED);
    graphics2D.drawString("GAME OVER",
        nbCellsWidth * cellSize / 2 - fontMetrics.stringWidth(gameOverText) / 2,
        (nbCellsHeight + 1) * cellSize / 2);
  }

}
