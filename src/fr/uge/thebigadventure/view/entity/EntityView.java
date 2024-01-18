package fr.uge.thebigadventure.view.entity;

import fr.uge.thebigadventure.model.type.entity.*;
import fr.uge.thebigadventure.model.utils.Coordinates;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

/**
 * This class is used to draw an entity tile on a graphics2D.
 */
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
   * In this method, the coordinates are not relative to the map. They are
   * the coordinates of the top left corner of the tile.
   * If you need the one relative to the map, use drawEntityTileInMap.
   *
   * @param graphics2D    the graphics2D to draw on
   * @param skin          the skin of the entity
   * @param coordinates   the coordinates of the entity
   *                      (the coordinates of the top left corner of the tile)
   * @param tileSize      the size of the tile
   * @param rotationAngle the angle of rotation of the entity
   * @throws IOException if the image cannot be loaded
   * @see #drawEntityTileInMap(Graphics2D, EntityType, Coordinates, int, int)
   * @see #drawEntityTileInMap(Graphics2D, EntityType, Coordinates, int)
   */
  public void drawEntityTile(Graphics2D graphics2D, EntityType skin,
                             Coordinates coordinates, int tileSize,
                             int rotationAngle) throws IOException {
    Objects.requireNonNull(graphics2D, "Graphics2D cannot be null");
    Objects.requireNonNull(skin, "Skin cannot be null");
    Objects.requireNonNull(coordinates, "Coordinates cannot be null");
    var imagePath = getImagePath(skin);
    var image = getImage(imagePath);
    var modifiedImage = resizeImage(image, tileSize, tileSize);
    if (rotationAngle != 0) {
      if (rotationAngle / 90 % 2 == 0)
        modifiedImage = verticalFlipImage(modifiedImage);
      modifiedImage = rotateImage(modifiedImage, rotationAngle);
    }
    graphics2D.drawImage(modifiedImage, coordinates.x(), coordinates.y(), null);
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
  public void drawEntityTileInMap(Graphics2D graphics2D, EntityType skin,
                                  Coordinates coordinates, int tileSize,
                                  int rotationAngle) throws IOException {
    drawEntityTile(graphics2D, skin, coordinates.multiply(tileSize), tileSize, rotationAngle);
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
  public void drawEntityTileInMap(Graphics2D graphics2D,
                                  EntityType skin, Coordinates coordinates,
                                  int tileSize) throws IOException {
    drawEntityTileInMap(graphics2D, skin, coordinates, tileSize, 0);
  }

  private String getImagePath(EntityType skin) {
    var folder = switch (skin) {
      case BiomeType ignored -> "biomes";
      case ObstacleType ignored -> "obstacles";
      case DecorationType ignored -> "decorations";
      case EffectType ignored -> "effects";
      case InventoryItemRawType ignored -> "items";
      case OtherType ignored -> "others";
      case PersonageType ignored -> "personages";
      case TransportType ignored -> "transports";
      case FoodType ignored -> "foods";
    };
    return "/img/" + folder + "/" + skin.name().toLowerCase(Locale.ROOT) +
        ".png";
  }

  private BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
    Objects.requireNonNull(originalImage, "Original image cannot be null");
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

  private BufferedImage verticalFlipImage(BufferedImage originalImage) {
    int w = originalImage.getWidth();
    int h = originalImage.getHeight();
    BufferedImage flippedImage = new BufferedImage(w, h, originalImage.getType());
    Graphics2D g2d = flippedImage.createGraphics();
    g2d.drawImage(originalImage, 0, h, w, -h, null);
    g2d.dispose();
    return flippedImage;
  }

  /**
   * Draw the health bar of an entity.
   * The health bar is drawn on the given graphics2D at the given coordinates.
   * The health bar is sized to the given width and height.
   *
   * @param graphics2D the graphics2D to draw on
   * @param x          the x coordinate of the health bar
   * @param y          the y coordinate of the health bar
   * @param width      the width of the health bar
   * @param height     the height of the health bar
   * @param health     the current health of the entity
   * @param maxHealth  the maximum health of the entity
   */
  public void renderHealthBar(Graphics2D graphics2D, int x, int y, int width, int height, int health, int maxHealth) {
    graphics2D.setColor(new Color(77, 194, 26));
    graphics2D.fillRect(x, y, width * health / maxHealth, height);
    graphics2D.setColor(Color.BLACK);
    graphics2D.drawRect(x, y, width, height);
  }
}
