package fr.uge.thebigadventure.views.entities;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.models.enums.entities.EntityType;

import java.awt.*;

public class PlayerView {


  public static void renderPlayer(Graphics2D graphics2D, Player player, int cellSize) throws Exception {
    EntityView.drawEntityTile(graphics2D,
            player.skin(), player.position(), cellSize);
  }

  public static void clearPlayer(Graphics2D graphics2D, Coord lastPosition, int cellSize) {
    EntityView.clearTile(graphics2D, lastPosition, cellSize);
  }

  public static void restoreLastTileState(Graphics2D graphics2D, EntityType tile,
                                          Coord lastPosition, int cellSize) throws Exception {
    EntityView.drawEntityTile(graphics2D, tile, lastPosition, cellSize);
  }

}
