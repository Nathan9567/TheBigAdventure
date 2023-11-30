package fr.uge.thebigadventure.models.entities.other;

import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.enums.entities.SpecialType;

public interface SpecialEntity extends Entity {
  @Override
  SpecialType skin();
}
