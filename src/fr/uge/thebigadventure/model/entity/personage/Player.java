package fr.uge.thebigadventure.model.entity.personage;

import fr.uge.thebigadventure.model.entity.inventory.Inventory;
import fr.uge.thebigadventure.model.type.entity.PersonageType;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.model.type.util.PlayerSkinType;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.Arrays;
import java.util.Objects;

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
    Objects.requireNonNull(position, "Position cannot be null");
    if (health < 1) {
      throw new IllegalArgumentException("Health must be greater than 0");
    }
    this.skin = skin;
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
