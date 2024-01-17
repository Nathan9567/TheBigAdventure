package fr.uge.thebigadventure.model.entity.other;

import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.type.entity.OtherType;

/**
 * SpecialEntity is an interface that represents the entities that are special.
 * They are special because they do special things.
 */
public interface SpecialEntity extends Entity {
  @Override
  OtherType skin();
}
