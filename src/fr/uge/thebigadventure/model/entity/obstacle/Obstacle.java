package fr.uge.thebigadventure.model.entity.obstacle;

import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.ElementRef;
import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.type.entity.ObstacleType;

import java.util.Objects;

public record Obstacle(ObstacleType skin, String name, Coordinates position,
                       ElementRef itemToUnlock) implements Entity {

  public Obstacle {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(position);
  }
}
