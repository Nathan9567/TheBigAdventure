package fr.uge.thebigadventure.model.entity.personage;

import fr.uge.thebigadventure.model.entity.inventory.Inventory;
import fr.uge.thebigadventure.model.type.entity.PersonageType;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.model.type.util.PlayerSkinType;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class representing a player.
 */
public final class Player implements Personage {

  private final PersonageType skin;
  private final String name;
  private final Inventory inventory = new Inventory();
  private final int maxHealth;
  private int currentHealth;
  private Coordinates position;
  private Direction direction;

  /**
   * Constructor of a player.
   * Skin must be a valid {@link PlayerSkinType} and not null.
   * Position must be not null too.
   * Health must be greater than 0.
   *
   * @param skin     The skin of the player.
   * @param name     The name of the player.
   * @param position The position of the player.
   * @param health   The health of the player.
   */
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

  /**
   * Return the max health of the player.
   * The max health is the health of the player when he is full life.
   * The max health cannot be modified.
   *
   * @return The max health of the player.
   */
  public int maxHealth() {
    return maxHealth;
  }

  public String name() {
    return name;
  }

  public PersonageType skin() {
    return skin;
  }

  /**
   * Return the direction of the player.
   * The direction is the direction where the player is looking.
   * The direction can be null if the player is not looking in a direction (
   * when he is initializing for example).
   *
   * @return The direction of the player.
   */
  public Direction getDirection() {
    return direction;
  }

  /**
   * Set the direction of the player.
   * The direction is the direction where the player is looking.
   * The direction can be null if the player is not looking in a direction (
   * when he is initializing for example).
   *
   * @param direction The direction of the player.
   */
  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  /**
   * Return the inventory of the player. The inventory is a {@link Inventory} object.
   *
   * @return The inventory of the player.
   */
  public Inventory inventory() {
    return inventory;
  }

  /**
   * Get the current health of the player.
   *
   * @return The current health of the player.
   */
  public int health() {
    return currentHealth;
  }

  /**
   * Set the current health of the player.
   * The current health can be smaller than 0 or greater than max health.
   * Be careful when you use this method.
   *
   * @param currentHealth The current health of the player.
   */
  public void setCurrentHealth(int currentHealth) {
    this.currentHealth = currentHealth;
  }
}
