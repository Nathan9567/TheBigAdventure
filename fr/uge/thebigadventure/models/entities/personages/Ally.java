package fr.uge.thebigadventure.models.entities.personages;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;
import fr.uge.thebigadventure.models.enums.utils.Direction;

import java.util.Objects;

public class Ally implements PersonageInterface {

  private final PersonageType skin;
  private final String name;
  private final String text;
  private Coord position;

  // TODO : Implement trade system
  public Ally(PersonageType skin, String name, Coord position, String text) {
    Objects.requireNonNull(skin);
    this.skin = skin;
    this.name = name;
    this.position = position;
    this.text = text;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public PersonageType skin() {
    return skin;
  }

  @Override
  public Coord Position() {
    return position;
  }

  public void movePosition(Direction direction) {
    position = position.move(direction);
  }

  public String text() {
    return text;
  }
}
