package fr.uge.thebigadventure.models.enums.entities;

public enum EffectType implements EntityType {
  BUBBLE, DUST;

  @Override
  public String folder() {
    return "effects";
  }
}
