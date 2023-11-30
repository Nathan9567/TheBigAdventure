package fr.uge.thebigadventure.models.entities.personages;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;

import java.util.Objects;

public class Personage implements PersonageInterface {

  private final PersonageType skin;
  private final String name;

  public Personage(PersonageType skin, String name) {
    Objects.requireNonNull(skin);
    this.skin = skin;
    this.name = name;
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public PersonageType skin() {
    return null;
  }

  @Override
  public Coord Position() {
    return null;
  }
}
