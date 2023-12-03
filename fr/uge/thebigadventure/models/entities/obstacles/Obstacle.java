package fr.uge.thebigadventure.models.entities.obstacles;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.entities.inventory.InventoryItem;
import fr.uge.thebigadventure.models.enums.entities.ObstacleType;

import java.util.Objects;

public record Obstacle(ObstacleType skin, String name, Coord position,
                       InventoryItem itemToUnlock) {

  public Obstacle {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(position);
  }
}
