package fr.uge.thebigadventure.views.entities;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.enums.entities.*;
import fr.uge.thebigadventure.views.MapView;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;

public class EntityView {

  public static void drawEntityTile(Graphics2D graphics2D,
                                    EntityType skin, Coord coord, int tileSize,
                                    Color color) throws IOException {
    Image image = MapView.getImage(getImagePath(skin));
    graphics2D.drawImage(image, coord.x() * tileSize,
            coord.y() * tileSize, tileSize, tileSize, color, null);
  }

  public static void drawEntityTile(Graphics2D graphics2D,
                                    EntityType skin, Coord coord, int tileSize)
          throws IOException {
    drawEntityTile(graphics2D, skin, coord, tileSize, null);
  }

  public static void clearTile(Graphics2D graphics2D, Coord coord, int tileSize) {
    graphics2D.clearRect(coord.x() * tileSize, coord.y() * tileSize,
            tileSize, tileSize);
  }

  private static String getImagePath(EntityType skin) {
    var folder = switch (skin) {
      case BiomeType ignored -> "biomes";
      case ObstacleType ignored -> "obstacles";
      case DecorationType ignored -> "decorations";
      case EffectType ignored -> "effects";
      case InventoryItemType ignored -> "items";
      case OtherType ignored -> "others";
      case PersonageType ignored -> "personages";
      case TransportType ignored -> "transports";
    };
    return "/img/" + folder + "/" + skin.name().toLowerCase(Locale.ROOT) +
            ".png";
  }

}
