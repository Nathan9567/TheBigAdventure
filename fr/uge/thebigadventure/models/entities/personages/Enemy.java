package fr.uge.thebigadventure.models.entities.personages;


import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.Zone;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;
import fr.uge.thebigadventure.models.enums.utils.Behavior;
import fr.uge.thebigadventure.models.enums.utils.Kind;

import java.util.List;
import java.util.Objects;

public class Enemy implements Personage {

  private final PersonageType skin;
  private final String name;
  private final Kind kind;
  private final Zone zone;
  private final Behavior behavior;
  private final int damage;
  private final List<InventoryItemType> stealableItems;
  private int health;
  private Coord position;

  public Enemy(PersonageType skin, String name, Coord position, Kind kind,
               int health, Behavior behavior, int damage, Zone zone,
               List<InventoryItemType> stealableItems) {
    Objects.requireNonNull(skin, "Skin cannot be null");
    Objects.requireNonNull(position, "Position cannot be null");
    Objects.requireNonNull(kind, "Kind cannot be null");
    Objects.requireNonNull(behavior, "Behavior cannot be null");
    if (health <= 0) {
      throw new IllegalArgumentException("Health must be positive");
    }
    this.skin = skin;
    this.name = name;
    this.position = position;
    this.kind = kind;
    this.health = health;
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
  public Coord getPosition() {
    return position;
  }

  public void setPosition(Coord position) {
    this.position = position;
  }

  public Kind getKind() {
    return kind;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

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
}
