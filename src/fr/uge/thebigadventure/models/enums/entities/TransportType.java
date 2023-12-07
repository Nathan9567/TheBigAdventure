package fr.uge.thebigadventure.models.enums.entities;

public enum TransportType implements EntityType {
  PLANE, ROCKET, UFO,
  CAR,
  CART, TRAIN,
  BOAT;

  @Override
  public String folder() {
    return "transports";
  }
}
