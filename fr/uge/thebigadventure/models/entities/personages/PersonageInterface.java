package fr.uge.thebigadventure.models.entities.personages;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;

public interface PersonageInterface extends Entity {
  @Override
  PersonageType skin();

  Coord getPosition();

  void setPosition(Coord position);

}
