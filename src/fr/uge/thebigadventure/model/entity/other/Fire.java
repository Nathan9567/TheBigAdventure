package fr.uge.thebigadventure.model.entity.other;

import fr.uge.thebigadventure.model.type.entity.OtherType;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * Fire is a special entity that can be found in the game.
 *
 * @param name     name of the fire
 * @param position position of the fire
 */
public record Fire(String name, Coordinates position) implements SpecialEntity {

  @Override
  public OtherType skin() {
    return OtherType.FIRE;
  }
}
