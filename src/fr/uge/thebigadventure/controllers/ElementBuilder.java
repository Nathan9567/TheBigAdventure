package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.Zone;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.entities.inventory.*;
import fr.uge.thebigadventure.models.entities.inventory.weapons.Bolt;
import fr.uge.thebigadventure.models.entities.inventory.weapons.Shovel;
import fr.uge.thebigadventure.models.entities.inventory.weapons.Stick;
import fr.uge.thebigadventure.models.entities.inventory.weapons.Sword;
import fr.uge.thebigadventure.models.entities.obstacles.Obstacle;
import fr.uge.thebigadventure.models.entities.personages.*;
import fr.uge.thebigadventure.models.enums.entities.EntityType;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;
import fr.uge.thebigadventure.models.enums.entities.ObstacleType;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;
import fr.uge.thebigadventure.models.enums.utils.Behavior;
import fr.uge.thebigadventure.models.enums.utils.Direction;
import fr.uge.thebigadventure.models.enums.utils.Kind;

import static fr.uge.thebigadventure.models.enums.entities.InventoryItemType.*;

import java.util.Objects;

public class ElementBuilder {
  private String name = null;
  private EntityType skin = null;
  private boolean player = false;
  private Coordinates position = null;
  private int health = 0;
  private Kind kind = null;
  private Zone zone = null;
  private Behavior behavior = null;
  private int damage = 0;
  private String text = null;
  //  private List<String> steal;
//  private List<Trade> trade;
  // private Something locked;
  private Direction flow = null;
  private boolean phantomized = false;
  private String teleport = null;

  public ElementBuilder() {
  }

  public void setName(String name) {
    Objects.requireNonNull(name);
    this.name = name;
  }

  public void setSkin(EntityType skin) {
    Objects.requireNonNull(skin);
    this.skin = skin;
  }

  public void setPlayer(boolean player) {
    this.player = player;
  }

  public void setPosition(Coordinates position) {
    Objects.requireNonNull(position);
    this.position = position;
  }

  public void setHealth(int health) {
    if (health < 0) {
      throw new IllegalArgumentException("health < 0");
    }
    this.health = health;
  }

  public void setKind(Kind kind) {
    Objects.requireNonNull(kind);
    this.kind = kind;
  }

  public void setZone(Zone zone) {
    Objects.requireNonNull(zone);
    this.zone = zone;
  }

  public void setBehavior(Behavior behavior) {
    Objects.requireNonNull(behavior);
    this.behavior = behavior;
  }

  public void setDamage(int damage) {
    if (damage < 0) {
      // TODO est-ce qu'on pourrait mettre un damage positif ? ce serait rigolo
      throw new IllegalArgumentException("damage < 0");
    }
    this.damage = damage;
  }

  public void setText(String text) {
    Objects.requireNonNull(text);
    this.text = text;
  }

  /*public void setLocked(String group, String group2) {
    this.locked = 
  }*/

  public void setFlow(Direction flow) {
    Objects.requireNonNull(flow);
    this.flow = flow;
  }

  public void setPhantomized(boolean phantomized) {
    this.phantomized = phantomized;
  }

  public void setTeleport(String teleport) {
    Objects.requireNonNull(teleport);
    this.teleport = teleport;
  }

  // If health != 0, then it's a personage
  // TODO: add stealableItems in enemy constructor
  private Personage toPersonageEntity(PersonageType personageType) {
    if (skin.name().equals("GHOST"))
      return new Ghost();
    if (player)
      return new Player(personageType, name, position, health);
    if (kind == Kind.ENEMY)
      return new Enemy(personageType, name, position, kind, health, behavior, damage, zone, null);
    if (kind == Kind.FRIEND)
      return new Ally(personageType, name, position, zone, text);
    throw new IllegalStateException("Can't find which PersonageEntity is this"); // TODO custom exception
  }

  // TODO : add locked at "item to unlock" in obstacle constructor
  private Obstacle toObstacleEntity(ObstacleType obstacleType) {
    return new Obstacle(obstacleType, name, position, null);
  }

  private InventoryItem toWeaponEntity() {
    return switch (skin) {
      case BOLT -> new Bolt(name, position, damage);
      case SHOVEL -> new Shovel(name, position, damage);
      case STICK -> new Stick(name, position, damage);
      case SWORD -> new Sword(name, position, damage);
      default -> throw new IllegalStateException("Can't find which WeaponEntity is this"); // TODO custom exception
    };
  }

  private InventoryItem toItemEntity(InventoryItemType item) {
    return switch (item) {
      // TODO: direction for box
      case BOLT, SHOVEL, STICK, SWORD -> toWeaponEntity();
      case BOX -> new Box(item, name, position, null);
      case KEY -> new Key(name, position);
      case MIRROR -> new Mirror(name, position);
      case SEED -> new Seed(name, position);
      case BOOK, PAPER -> new LoreItem(item, name, text, position);
      default -> new BasicItem(item, name, position);
    };
  }

  public Entity toEntity() {
    return switch (skin) {
      case ObstacleType obstacleType -> toObstacleEntity(obstacleType);
      case InventoryItemType inventoryItemType ->
          toItemEntity(inventoryItemType);
      case PersonageType personageType -> toPersonageEntity(personageType);
      case null -> throw new IllegalStateException("No skin provided !"); // TODO custom exception
      default -> throw new IllegalStateException("Unexpected value: " + skin); // TODO custom exception
    };
  }
}
