package fr.uge.thebigadventure.model.entity.other;

import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.type.entity.OtherType;

public interface SpecialEntity extends Entity {
  @Override
  OtherType skin();
}
