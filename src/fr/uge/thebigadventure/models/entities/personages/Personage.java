package fr.uge.thebigadventure.models.entities.personages;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;

public interface Personage extends Entity {
  @Override
  PersonageType skin();

  @Override
  Coordinates position();

  void setPosition(Coordinates position);

}
