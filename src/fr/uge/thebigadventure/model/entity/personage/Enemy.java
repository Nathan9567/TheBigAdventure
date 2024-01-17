package fr.uge.thebigadventure.model.entity.personage;


import fr.uge.thebigadventure.model.type.entity.InventoryItemType;
import fr.uge.thebigadventure.model.type.entity.PersonageType;
import fr.uge.thebigadventure.model.type.util.Behavior;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.Zone;

import java.util.List;
import java.util.Objects;

/**
 * Class representing an enemy.
 */
public final class Enemy implements NPC {

  private final PersonageType skin;
  private final String name;
  private final Zone zone;
  private final Behavior behavior;
  private final int damage;
  private final List<InventoryItemType> stealableItems;
  private final int maxHealth;
  private int health;
  private Coordinates position;

  /**
   * Constructor of an enemy with a default behavior.
   * The skin of the enemy cannot be null, like the position and the behavior.
   * The health must be positive too.
   *
   * @param skin           the skin of the enemy
   * @param name           the name of the enemy
   * @param position       the position of the enemy
   * @param health         the health of the enemy
   * @param behavior       the behavior of the enemy
   * @param damage         the damages of the enemy
   * @param zone           the zone of the enemy
   * @param stealableItems the list of stealable items of the enemy
   */
  public Enemy(PersonageType skin, String name, Coordinates position,
               int health, Behavior behavior, int damage, Zone zone,
               List<InventoryItemType> stealableItems) {
    Objects.requireNonNull(skin, "Skin cannot be null");
    Objects.requireNonNull(position, "Position cannot be null");
    Objects.requireNonNull(behavior, "Behavior cannot be null");
    if (health <= 0) {
      throw new IllegalArgumentException("Health must be positive");
    }
    this.skin = skin;
    this.name = name;
    this.position = position;
    this.health = health;
    this.maxHealth = health;
    this.behavior = behavior;
    this.damage = damage;
    this.zone = zone;
    this.stealableItems = stealableItems;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public PersonageType skin() {
    return skin;
  }

  @Override
  public Coordinates position() {
    return position;
  }

  @Override
  public void setPosition(Coordinates position) {
    this.position = position;
  }

  /**
   * Returns the health of the enemy.
   *
   * @return the health of the enemy
   */
  public int getHealth() {
    return health;
  }

  /**
   * Sets the health of the enemy.
   * This method is used to set the health of the enemy when he is attacked.
   *
   * @param health the new health of the enemy
   */
  public void setHealth(int health) {
    this.health = health;
  }

  @Override
  public Zone getZone() {
    return zone;
  }

  public Behavior getBehavior() {
    return behavior;
  }

  public int getDamage() {
    return damage;
  }

  /**
   * Returns the list of stealable items of the enemy.
   *
   * @return the list of stealable items of the enemy
   */
  public List<InventoryItemType> getStealableItems() {
    return List.copyOf(stealableItems);
  }

  /**
   * Returns the maximum health of the enemy.
   * This method is used to know the maximum health of the enemy.
   * The maximum health cannot be modified.
   *
   * @return the maximum health of the enemy
   */
  public int maxHealth() {
    return maxHealth;
  }
}
