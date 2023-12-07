package fr.uge.thebigadventure.views.entities;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.views.MapView;

import java.awt.*;
import java.io.IOException;

public class EntityView {

  public static void drawEntityTile(Graphics2D graphics2D,
                                    String entityPath,
                                    Coord coord, int tileSize, Color color) throws IOException {
    Image image = MapView.getImage(entityPath);
    graphics2D.drawImage(image, coord.x() * tileSize,
        coord.y() * tileSize, tileSize, tileSize, color, null);
  }

}
