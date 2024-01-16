package fr.uge.thebigadventure.model.entity.personage;


import fr.uge.thebigadventure.model.type.entity.InventoryItemType;
import fr.uge.thebigadventure.model.type.entity.PersonageType;
import fr.uge.thebigadventure.model.type.util.Behavior;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.Zone;

import java.util.List;
import java.util.Objects;

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

  public int getHealth() {
    return health;
  }

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

  public List<InventoryItemType> getStealableItems() {
    return stealableItems;
  }

  public int maxHealth() {
    return maxHealth;
  }
}
