package fr.uge.thebigadventure.model.entity.other;

import fr.uge.thebigadventure.model.type.entity.OtherType;
import fr.uge.thebigadventure.model.utils.Coordinates;

public class Lever implements SpecialEntity {

  private final String name;
  private final Coordinates position;
  private boolean actioned = false;

  public Lever(String name, Coordinates position) {
    this.name = name;
    this.position = position;
  }

  public void action() {
    actioned = !actioned;
  }

  public boolean isActioned() {
    return actioned;
  }

  @Override
  public Coordinates position() {
    return position;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public OtherType skin() {
    return OtherType.LEVER;
  }
}
