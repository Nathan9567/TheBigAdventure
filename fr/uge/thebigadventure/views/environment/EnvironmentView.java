package fr.uge.thebigadventure.views.environment;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.views.MapView;

import java.awt.*;

public class EnvironmentView {

  public static void drawEnvironmentTile(Graphics2D graphics2D,
                                         String environmentTilePath,
                                         Coord coord, int tileSize) {
    Image image = MapView.getImage(environmentTilePath);
    graphics2D.drawImage(image, coord.x() * tileSize, coord.y() * tileSize, tileSize,
        tileSize,
        null);
  }

}
