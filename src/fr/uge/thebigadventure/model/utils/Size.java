package fr.uge.thebigadventure.model.utils;

/**
 * Size of an area.
 *
 * @param width  Width of the area.
 *               Must be strictly positive.
 * @param height Height of the area.
 *               Must be strictly positive.
 */
public record Size(int width, int height) {
  public Size {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid size");
    }
  }
}
