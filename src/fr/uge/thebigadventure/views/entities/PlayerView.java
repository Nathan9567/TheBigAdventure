package fr.uge.thebigadventure.views.entities;

import fr.uge.thebigadventure.models.entities.personages.Player;

import java.awt.*;

public record PlayerView(Player player, int cellSize) {

  private static final EntityView entityView = new EntityView();

  public void showPlayerHealth(Graphics2D graphics2D) {
  }

  public void showPlayer(Graphics2D graphics2D) throws Exception {
    entityView.drawEntityTile(graphics2D,
        player.skin(), player.position(), cellSize);
  }
}
