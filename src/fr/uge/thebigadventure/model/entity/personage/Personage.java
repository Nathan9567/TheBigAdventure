package fr.uge.thebigadventure.model.entity.personage;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.type.entity.PersonageType;

public sealed interface Personage extends Entity permits NPC, Player {
  @Override
  PersonageType skin();

  @Override
  Coordinates position();

  void setPosition(Coordinates position);

}
