package fr.uge.thebigadventure.models.enums.entities;

public enum OtherType implements EntityType {
  BUCKET, FIRE, LEVER, WIND;

  @Override
  public String folder() {
    return "others";
  }
}
