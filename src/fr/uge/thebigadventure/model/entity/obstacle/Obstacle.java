package fr.uge.thebigadventure.model.entity.obstacle;

import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.type.entity.ObstacleType;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.ElementRef;

import java.util.Objects;

/**
 * Obstacle is an entity that can be unlocked by an item or not.
 * If it can be unlocked, it has an itemToUnlock.
 * Here, {@link ObstacleType skin} and {@link Coordinates position} are required.
 *
 * @param skin         the skin of the obstacle
 * @param name         the name of the obstacle
 * @param position     the position of the obstacle
 * @param itemToUnlock the item to unlock the obstacle and pass through it
 */
public record Obstacle(ObstacleType skin, String name, Coordinates position,
                       ElementRef itemToUnlock) implements Entity {

  public Obstacle {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(position);
  }
}
