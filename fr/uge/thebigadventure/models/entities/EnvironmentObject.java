package fr.uge.thebigadventure.models.entities;

import fr.uge.thebigadventure.models.enums.entities.EntityType;

public class EnvironmentObject {
  protected final char encoding;
  private final EntityType environmentType;

  public EnvironmentObject(EntityType environmentType, char encoding) {
    this.environmentType = environmentType;
    this.encoding = encoding;
  }

  public EntityType getEnvironmentType() {
    return environmentType;
  }

  public char getEncoding() {
    return encoding;
  }
}
