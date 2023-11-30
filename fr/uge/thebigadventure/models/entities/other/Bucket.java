package fr.uge.thebigadventure.models.entities.other;

import fr.uge.thebigadventure.models.enums.entities.SpecialType;

public class Bucket implements SpecialEntity {

  private final String name;
  private boolean filled = false;

  public Bucket(String name) {
    this.name = name;
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
  public SpecialType skin() {
    return SpecialType.BUCKET;
  }
}
