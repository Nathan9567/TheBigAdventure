package fr.uge.thebigadventure.view.entity;

import fr.uge.thebigadventure.model.entity.personage.Player;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.view.MapView;

import java.awt.*;
import java.io.IOException;

public class PlayerView {

  private static final EntityView entityView = new EntityView();
  private final Player player;
  private final int cellSize;

  public PlayerView(Player player, int cellSize) {
    this.player = player;
    this.cellSize = cellSize;
  }

  public void showPlayerHealth(Graphics2D graphics2D, Coordinates position) {
    var x = position.x() * cellSize;
    var y = position.y() * cellSize - cellSize / 4;
    Utils.renderHealthBar(graphics2D, x, y, cellSize, cellSize / 4, player.health(), player.maxHealth());
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
    var coordinates = playerCenteredPosition.multiply(cellSize).add(cellSize / 2, 0);
    switch (player.getDirection()) {
      case NORTH -> coordinates = coordinates.add(-cellSize / 2, -cellSize / 2);
      case SOUTH ->
          coordinates = coordinates.add(-cellSize / 4, (int) (cellSize / 1.5));
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
