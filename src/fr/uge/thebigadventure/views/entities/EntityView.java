package fr.uge.thebigadventure.views.entities;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.enums.entities.*;
import fr.uge.thebigadventure.views.MapView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;

public class EntityView {

  private static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight,
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = resizedImage.createGraphics();

    // Scale the image to the new width and height.
    double scaleX = (double) newWidth / originalImage.getWidth();
    double scaleY = (double) newHeight / originalImage.getHeight();
    g2d.scale(scaleX, scaleY);

    // Draw the scaled image.
    g2d.drawImage(originalImage, 0, 0, null);
    g2d.dispose();

    return resizedImage;
  }

  public static void drawEntityTile(Graphics2D graphics2D,
                                    EntityType skin, Coord coord, int tileSize,
                                    Color color) throws IOException {
    var imagePath = getImagePath(skin);
    var image = MapView.getImage(imagePath);
    var resizedImage = resizeImage(image, tileSize, tileSize);
    graphics2D.drawImage(resizedImage, coord.x() * tileSize,
        coord.y() * tileSize, color, null);
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
