package fr.uge.thebigadventure.models.entities.obstacles;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.entities.inventory.InventoryItem;
import fr.uge.thebigadventure.models.enums.entities.ObstacleType;

import java.util.Objects;

public record Obstacle(ObstacleType skin, String name, Coordinates position,
                       InventoryItem itemToUnlock) implements Entity {

  public Obstacle {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(position);
  }
}
