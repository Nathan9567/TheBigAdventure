package fr.uge.thebigadventure.views.entities;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.views.MapView;

import java.awt.*;
import java.io.IOException;

public record PlayerView(Player player, int cellSize) {

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
    graphics2D.drawString(player.name(), x, y);
  }

  public void renderPlayer(Graphics2D graphics2D) throws IOException {
    var playerPositionCentered =
        MapView.coordinatesToPlayerCenteredMapCoordinates(player.position());
    entityView.drawEntityTile(graphics2D,
        player.skin(), playerPositionCentered, cellSize);
    showPlayerHealth(graphics2D, playerPositionCentered);
    showPlayerName(graphics2D, playerPositionCentered);
  }
}
