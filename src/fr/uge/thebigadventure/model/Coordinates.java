package fr.uge.thebigadventure.model;

import java.util.Objects;

import fr.uge.thebigadventure.model.enums.util.Direction;

public record Coordinates(int x, int y) {

  public Coordinates move(Direction direction) {
    return switch (direction) {
      case NORTH -> new Coordinates(x, y - 1);
      case SOUTH -> new Coordinates(x, y + 1);
      case WEST -> new Coordinates(x - 1, y);
      case EAST -> new Coordinates(x + 1, y);
    };
  }

  public boolean notInBounds(Size size) {
    return 0 > x || 0 > y || x >= size.width() || y >= size.height();
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
}
