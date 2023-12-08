package fr.uge.thebigadventure.models;

import fr.uge.thebigadventure.models.enums.utils.Direction;

import java.util.Objects;

public record Coord(int x, int y) {

  public Coord move(Direction direction) {
    return switch (direction) {
      case NORTH -> new Coord(x, y - 1);
      case SOUTH -> new Coord(x, y + 1);
      case WEST -> new Coord(x - 1, y);
      case EAST -> new Coord(x + 1, y);
    };
  }

  public boolean inBounds(Size size) {
    return 0 <= x && 0 <= y && x < size.width() && y < size.height();
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Coord coord)) {
      return false;
    }
    return coord.x == x && coord.y == y;
  }
}
