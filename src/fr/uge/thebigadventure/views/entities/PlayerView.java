package fr.uge.thebigadventure.views.entities;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.models.enums.utils.Direction;

import java.awt.*;
import java.io.IOException;

public record PlayerView(Player player, int cellSize) {

  private static final EntityView entityView = new EntityView();

  private static Coordinates lastPlayerPosition = null;

  public void showPlayerHealth(Graphics2D graphics2D) {
    var x = player.position().x() * cellSize;
    var y = (player.position().y() + 1) * cellSize + cellSize / 2;
    graphics2D.setColor(Color.BLACK);
    graphics2D.drawRect(x, y, cellSize, cellSize / 4);
    graphics2D.setColor(new Color(77, 194, 26));
    graphics2D.fillRect(x, y, cellSize * player.health() / player.maxHealth(), cellSize / 4);
  }

  private void clearBelowTiles(Graphics2D graphics2D) {
    if (lastPlayerPosition != null) {
      var southPosition = lastPlayerPosition.move(Direction.SOUTH);
      entityView.clearTile(graphics2D, southPosition, cellSize);
      entityView.clearTile(graphics2D, southPosition.move(Direction.EAST), cellSize);
      entityView.clearTile(graphics2D, southPosition.move(Direction.WEST), cellSize);
    }
  }

  public void showPlayerName(Graphics2D graphics2D) {
    var x = player.position().x() * cellSize;
    var y = (player.position().y() + 1) * cellSize + cellSize / 4;
    Font font = new Font(Font.MONOSPACED, Font.BOLD, cellSize / 4);
    graphics2D.setFont(font);
    graphics2D.drawString(player.name(), x, y);
  }

  public void clearLastPlayerPosition(Graphics2D graphics2D) {
    if (lastPlayerPosition != null) {
      entityView.clearTile(graphics2D, lastPlayerPosition, cellSize);
      clearBelowTiles(graphics2D);
    }
  }

  public void renderPlayer(Graphics2D graphics2D) throws IOException {
    lastPlayerPosition = player.position();
    entityView.drawEntityTile(graphics2D,
        player.skin(), player.position(), cellSize);
    showPlayerName(graphics2D);
    showPlayerHealth(graphics2D);
  }
}
