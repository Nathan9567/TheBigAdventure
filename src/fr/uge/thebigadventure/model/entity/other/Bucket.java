package fr.uge.thebigadventure.model.entity.other;

import fr.uge.thebigadventure.model.type.entity.OtherType;
import fr.uge.thebigadventure.model.utils.Coordinates;

public class Bucket implements SpecialEntity {

  private final String name;
  private final Coordinates position;
  private boolean filled = false;

  public Bucket(String name, Coordinates position) {
    this.name = name;
    this.position = position;
  }

  @Override
  public String name() {
    return name;
  }

  public boolean isFilled() {
    return filled;
  }

  public void fill() {
    filled = true;
  }

  public void empty() {
    filled = false;
  }

  @Override
  public Coordinates position() {
    return position;
  }

  @Override
  public OtherType skin() {
    return OtherType.BUCKET;
  }
}
