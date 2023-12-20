package fr.uge.thebigadventure.models.entities.personages;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.Zone;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;

import java.util.Objects;

public final class Ally implements NPC {

  private final PersonageType skin;
  private final String name;
  private final String text;
  private final Zone zone;
  private Coordinates position;

  // TODO : Implement trade system
  public Ally(PersonageType skin, String name, Coordinates position, Zone zone,
              String text) {
    Objects.requireNonNull(skin, "Skin cannot be null");
    this.skin = skin;
    this.name = name;
    Objects.requireNonNull(position, "Position cannot be null");
    this.position = position;
    this.zone = zone;
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
  public Coordinates position() {
    return position;
  }

  @Override
  public void setPosition(Coordinates position) {
    this.position = position;
  }

  public String text() {
    return text;
  }

  public Zone getZone() {
    return zone;
  }
}
