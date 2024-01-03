package fr.uge.thebigadventure.model.entity.personage;

import java.util.Arrays;
import java.util.Objects;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.entity.inventory.Inventory;
import fr.uge.thebigadventure.model.enums.entity.PersonageType;
import fr.uge.thebigadventure.model.enums.util.Direction;
import fr.uge.thebigadventure.model.enums.util.PlayerSkinType;

public final class Player implements Personage {

  private final PersonageType skin;
  private final String name;
  private final Inventory inventory = new Inventory();
  private final int maxHealth;
  private int currentHealth;
  private Coordinates position;
  private Direction direction;

  public Player(PersonageType skin, String name, Coordinates position, int health) {
    Objects.requireNonNull(skin, "Skin cannot be null");
    boolean isPlayerSkin = Arrays.stream(PlayerSkinType.values())
        .anyMatch(playerSkinType -> playerSkinType.name().equals(skin.name()));
    if (!isPlayerSkin) {
      throw new IllegalArgumentException("Skin must be a valid PlayerSkinType");
    }
    this.skin = skin;
    Objects.requireNonNull(position, "Position cannot be null");
    this.name = name;
    this.position = position;
    this.maxHealth = health;
    this.currentHealth = health;
    this.direction = null;
  }

  @Override
  public Coordinates position() {
    return position;
  }

  public void setPosition(Coordinates position) {
    this.position = position;
  }

  public int maxHealth() {
    return maxHealth;
  }

  public String name() {
    return name;
  }

  public PersonageType skin() {
    return skin;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Inventory inventory() {
    return inventory;
  }

  public int health() {
    return currentHealth;
  }

  public void setCurrentHealth(int currentHealth) {
    this.currentHealth = currentHealth;
  }
}
