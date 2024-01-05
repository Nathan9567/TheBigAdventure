package fr.uge.thebigadventure.model.entity.obstacle;

import java.util.Objects;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.entity.inventory.InventoryItem;
import fr.uge.thebigadventure.model.type.entity.ObstacleType;

public record Obstacle(ObstacleType skin, String name, Coordinates position,
                       InventoryItem itemToUnlock) implements Entity {

  public Obstacle {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(position);
  }
}
