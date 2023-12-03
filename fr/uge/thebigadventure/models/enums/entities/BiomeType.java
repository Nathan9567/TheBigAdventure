package fr.uge.thebigadventure.models.enums.entities;

public enum BiomeType implements EntityType {
  ICE, LAVA, WATER;

  @Override
  public String folder() {
    return "biomes";
  }
}
