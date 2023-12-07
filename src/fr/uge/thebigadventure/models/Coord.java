package fr.uge.thebigadventure.models;

import fr.uge.thebigadventure.models.enums.utils.Direction;

public record Coord(int x, int y) {
  public Coord {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Invalid coord : " + x + ", " + y);
    }
  }

  public Coord move(Direction direction) {
    return switch (direction) {
      case NORTH -> new Coord(x, y - 1);
      case SOUTH -> new Coord(x, y + 1);
      case WEST -> new Coord(x - 1, y);
      case EAST -> new Coord(x + 1, y);
    };
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Coord coord)) {
      return false;
    }
    return coord.x == x && coord.y == y;
  }
}
