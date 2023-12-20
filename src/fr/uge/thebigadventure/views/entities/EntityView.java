package fr.uge.thebigadventure.views.entities;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.enums.entities.*;
import fr.uge.thebigadventure.views.MapView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class EntityView {

  private static final HashMap<String, BufferedImage> images = new HashMap<>();

  private BufferedImage getImage(String path) throws IOException {
    BufferedImage image = images.get(path);
    if (image != null) {
      return image;
    }
    try (var input = MapView.class.getResourceAsStream(path)) {
      if (input == null) {
        throw new IOException("Cannot load resource " + path);
      }
      image = ImageIO.read(input);
    }
    images.put(path, image);
    return image;
  }

  public void drawEntityTile(Graphics2D graphics2D,
                             EntityType skin, Coordinates coordinates, int tileSize,
                             Color color) throws IOException {
    var imagePath = getImagePath(skin);
    var image = getImage(imagePath);
    var resizedImage = resizeImage(image, tileSize, tileSize);
    graphics2D.drawImage(resizedImage, coordinates.x() * tileSize,
        coordinates.y() * tileSize, color, null);
  }

  public void drawEntityTile(Graphics2D graphics2D,
                             EntityType skin, Coordinates coordinates, int tileSize)
      throws IOException {
    drawEntityTile(graphics2D, skin, coordinates, tileSize, null);
  }

  public void clearTile(Graphics2D graphics2D, Coordinates coordinates, int tileSize) {
    graphics2D.clearRect(coordinates.x() * tileSize, coordinates.y() * tileSize,
        tileSize, tileSize);
  }

  private String getImagePath(EntityType skin) {
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

  private BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
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

}
