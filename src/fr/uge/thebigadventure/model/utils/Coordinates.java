package fr.uge.thebigadventure.model.utils;

import fr.uge.thebigadventure.model.type.util.Direction;

import java.util.Objects;

/**
 * Coordinates in a GameMap.
 *
 * @param x the x coordinate
 *          (0 is the left side of the map, increasing to the right)
 * @param y the y coordinate
 *          (0 is the top of the map, increasing to the bottom)
 */
public record Coordinates(int x, int y) {
  /**
   * Return the adjacent coordinates in the given direction.
   *
   * @param direction the direction to move
   * @return the new coordinates
   */
  public Coordinates move(Direction direction) {
    return switch (direction) {
      case NORTH -> new Coordinates(x, y - 1);
      case SOUTH -> new Coordinates(x, y + 1);
      case WEST -> new Coordinates(x - 1, y);
      case EAST -> new Coordinates(x + 1, y);
    };
  }

  /**
   * Multiply the coordinates by a multiplier.
   *
   * @param multiplier the multiplier to apply
   * @return the new coordinates
   */
  public Coordinates multiply(int multiplier) {
    return new Coordinates(x * multiplier, y * multiplier);
  }

  /**
   * Check if the coordinates are in bounds of the given size.
   *
   * @param size the size to check
   * @return true if the coordinates are not in bounds
   */
  public boolean notInBounds(Size size) {
    return 0 > x || 0 > y || x >= size.width() || y >= size.height();
  }

  /**
   * Check if the coordinates are adjacent to the given coordinates.
   *
   * @param other the coordinates to check
   * @return true if the coordinates are adjacent
   */
  public boolean isAdjacent(Coordinates other) {
    return (other.x() + 1 == x() && other.y() == y()
        || other.x() - 1 == x() && other.y() == y()
        || other.x() == x() && other.y() + 1 == y()
        || other.x() == x() && other.y() - 1 == y());
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Coordinates coordinates)) {
      return false;
    }
    return coordinates.x == x && coordinates.y == y;
  }

  /**
   * Add the given coordinates to the current coordinates.
   *
   * @param x1 the x to add
   * @param x2 the y to add
   * @return the new coordinates
   */
  public Coordinates add(int x1, int x2) {
    return new Coordinates(x + x1, y + x2);
  }
}
