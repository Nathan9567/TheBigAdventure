package fr.uge.thebigadventure.view.entity;

import javax.imageio.ImageIO;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.type.entity.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class EntityView {

  private static final HashMap<String, BufferedImage> images = new HashMap<>();

  private BufferedImage getImage(String path) throws IOException {
    BufferedImage image = images.get(path);
    if (image != null) {
      return image;
    }
    try (var input = EntityView.class.getResourceAsStream(path)) {
      if (input == null) {
        throw new IOException("Cannot load resource " + path);
      }
      image = ImageIO.read(input);
    }
    images.put(path, image);
    return image;
  }

  /**
   * Draw an entity tile on the given graphics2D at the given coordinates.
   * The tile is resized to the given tile size and
   * rotated by the given rotation angle.
   *
   * @param graphics2D    the graphics2D to draw on
   * @param skin          the skin of the entity
   * @param coordinates   the coordinates of the entity relative to the map
   * @param tileSize      the size of the tile
   * @param rotationAngle the angle of rotation of the entity
   * @throws IOException if the image cannot be loaded
   */
  public void drawEntityTile(Graphics2D graphics2D, EntityType skin,
                             Coordinates coordinates, int tileSize,
                             int rotationAngle) throws IOException {
    Objects.requireNonNull(graphics2D, "Graphics2D cannot be null");
    Objects.requireNonNull(skin, "Skin cannot be null");
    Objects.requireNonNull(coordinates, "Coordinates cannot be null");
    if (tileSize <= 0) {
      throw new IllegalArgumentException("Tile size must be positive");
    }
    if (rotationAngle % 90 != 0) {
      throw new IllegalArgumentException("Rotation angle must be a multiple of 90");
    }
    var imagePath = getImagePath(skin);
    var image = getImage(imagePath);
    var modifiedImage = resizeImage(image, tileSize, tileSize);
    if (rotationAngle != 0) {
      modifiedImage = rotateImage(modifiedImage, rotationAngle);
    }
    graphics2D.drawImage(modifiedImage, coordinates.x() * tileSize,
        coordinates.y() * tileSize, null);
  }

  /**
   * Draw an entity tile on the given graphics2D at the given coordinates.
   * The tile is resized to the given tile size.
   * The coordinates are multiplied by the tile size to get the position
   * of the tile on the graphics2D.
   * If not set, the rotation angle is set to 0.
   * The rotation angle must be a multiple of 90.
   * All of these parameters cannot be null.
   *
   * @param graphics2D  the graphics2D to draw on
   * @param skin        the skin of the entity
   * @param coordinates the coordinates of the entity relative to the map
   * @param tileSize    the size of the tile
   * @throws IOException if the image cannot be loaded
   */
  public void drawEntityTile(Graphics2D graphics2D,
                             EntityType skin, Coordinates coordinates, int tileSize) throws IOException {
    drawEntityTile(graphics2D, skin, coordinates, tileSize, 0);
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

  private BufferedImage rotateImage(BufferedImage originalImage, int angle) {
    int w = originalImage.getWidth();
    int h = originalImage.getHeight();
    BufferedImage rotatedImage = new BufferedImage(w, h, originalImage.getType());
    Graphics2D g2d = rotatedImage.createGraphics();
    g2d.rotate(Math.toRadians(angle), w / 2.0, h / 2.0);
    g2d.drawImage(originalImage, 0, 0, null);
    g2d.dispose();
    return rotatedImage;
  }

}