package fr.uge.thebigadventure.view.entity;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.entity.personage.Player;
import fr.uge.thebigadventure.view.MapView;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.io.IOException;

public record PlayerView(Player player, int cellSize, ScreenInfo screenInfo) {

  private static final EntityView entityView = new EntityView();

  public void showPlayerHealth(Graphics2D graphics2D, Coordinates position) {
    var x = position.x() * cellSize;
    var y = position.y() * cellSize - cellSize / 4;
    graphics2D.setColor(Color.BLACK);
    graphics2D.drawRect(x, y, cellSize, cellSize / 4);
    graphics2D.setColor(new Color(77, 194, 26));
    graphics2D.fillRect(x, y, cellSize * player.health() / player.maxHealth(), cellSize / 4);
  }

  public void showPlayerName(Graphics2D graphics2D, Coordinates position) {
    var x = position.x() * cellSize;
    var y = (position.y() + 1) * cellSize + cellSize / 4;
    Font font = new Font(Font.MONOSPACED, Font.BOLD, cellSize / 4);
    graphics2D.setFont(font);
    graphics2D.setColor(Color.WHITE);
    graphics2D.drawString(player.name(), x, y);
  }

  private void showMainHand(Graphics2D graphics2D,
                            Coordinates playerCenteredPosition,
                            int angle) throws IOException {
    var mainHand = player.inventory().mainHand();
    if (mainHand == null) {
      return;
    }
    var coordinates = playerCenteredPosition.multiply(cellSize).add((double) cellSize / 2, 0);
    switch (player.getDirection()) {
      case NORTH -> coordinates = coordinates.add(-cellSize / 2, -cellSize / 2);
      case SOUTH -> coordinates = coordinates.add(0, cellSize / 2);
      case EAST -> coordinates = coordinates.add(cellSize / 4, 0);
      case WEST -> coordinates = coordinates.add(-cellSize, 0);
    }
    entityView.drawEntityTile(graphics2D, mainHand.skin(),
        coordinates, (int) (cellSize * 0.8), angle);
  }

  public void renderPlayer(Graphics2D graphics2D) throws IOException {
    var playerPositionCentered =
        MapView.coordinatesToPlayerCenteredMapCoordinates(player.position());

    var angle = switch (player.getDirection()) {
      case NORTH -> 270;
      case SOUTH -> 90;
      case EAST -> 0;
      case WEST -> 180;
      case null -> 0;
    };
    entityView.drawEntityTileInMap(graphics2D,
        player.skin(), playerPositionCentered, cellSize, angle);
    showPlayerHealth(graphics2D, playerPositionCentered);
    showPlayerName(graphics2D, playerPositionCentered);
    showMainHand(graphics2D, playerPositionCentered, angle);
  }
}
