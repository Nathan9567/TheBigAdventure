package fr.uge.thebigadventure.model.entity.other;

import fr.uge.thebigadventure.model.type.entity.OtherType;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * Lever is a special entity that can be actioned.
 */
public class Lever implements SpecialEntity {

  private final String name;
  private final Coordinates position;
  private boolean actioned = false;

  /**
   * Create a new Lever with a name and a position.
   * These parameters are nullable.
   *
   * @param name     name of the lever
   * @param position position of the lever
   */
  public Lever(String name, Coordinates position) {
    this.name = name;
    this.position = position;
  }

  /**
   * Action a lever.
   */
  public void action() {
    actioned = !actioned;
  }

  /**
   * Method to know if the lever is actioned.
   *
   * @return true if the lever is actioned
   */
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
