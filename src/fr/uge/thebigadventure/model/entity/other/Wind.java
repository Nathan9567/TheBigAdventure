package fr.uge.thebigadventure.model.entity.other;

import fr.uge.thebigadventure.model.type.entity.OtherType;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.model.utils.Zone;

/**
 * Wind is a special entity that can be found in the game.
 * It is used to move some entities.
 *
 * @param zone The zone where the wind is.
 * @param flow The direction of the wind.
 */
public record Wind(Zone zone, Direction flow) implements SpecialEntity {

  @Override
  public String name() {
    return null;
  }

  @Override
  public OtherType skin() {
    return OtherType.WIND;
  }
}
