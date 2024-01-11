package fr.uge.thebigadventure.model;

import fr.uge.thebigadventure.model.type.util.Direction;

import java.util.Objects;

public record Coordinates(int x, int y) {

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

  public boolean notInBounds(Size size) {
    return 0 > x || 0 > y || x >= size.width() || y >= size.height();
  }

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

  public Coordinates add(int x1, int x2) {
    return new Coordinates(x + x1, y + x2);
  }
}
