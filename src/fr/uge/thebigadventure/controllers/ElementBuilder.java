package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.Zone;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.entities.inventory.*;
import fr.uge.thebigadventure.models.entities.obstacles.Obstacle;
import fr.uge.thebigadventure.models.entities.personages.*;
import fr.uge.thebigadventure.models.entities.weapons.Bolt;
import fr.uge.thebigadventure.models.entities.weapons.Shovel;
import fr.uge.thebigadventure.models.entities.weapons.Stick;
import fr.uge.thebigadventure.models.entities.weapons.Sword;
import fr.uge.thebigadventure.models.enums.entities.EntityType;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;
import fr.uge.thebigadventure.models.enums.entities.ObstacleType;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;
import fr.uge.thebigadventure.models.enums.utils.Behavior;
import fr.uge.thebigadventure.models.enums.utils.Kind;

import static fr.uge.thebigadventure.models.enums.entities.InventoryItemType.*;

public class ElementBuilder {
  private String name = null;
  private EntityType skin = null;
  private boolean player = false;
  private Coord position = null;
  private int health = 0;
  private Kind kind = null;
  private Zone zone = null;
  private Behavior behavior = null;
  private int damage = 0;
  private String text = null;
//  private List<String> steal;
//  private List<Trade> trade;
//  private Lock locked;
//  private Direction flow;
//  private boolean phantomized;
//  private String teleport;

  public ElementBuilder() {
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSkin(EntityType skin) {
    this.skin = skin;
  }

  public void setPlayer(boolean player) {
    this.player = player;
  }

  public void setPosition(Coord position) {
    this.position = position;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public void setKind(Kind kind) {
    this.kind = kind;
  }

  public void setZone(Zone zone) {
    this.zone = zone;
  }

  public void setBehavior(Behavior behavior) {
    this.behavior = behavior;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public void setText(String text) {
    this.text = text;
  }

  // If health != 0, then it's a personage
  // TODO: add strealableItems in enemy constructor
  private Personage toPersonageEntity(PersonageType personageType) {
    if (skin.name().equals("GHOST"))
      return new Ghost();
    if (player)
      return new Player(personageType, name, position, health);
    if (kind == Kind.ENEMY)
      return new Enemy(personageType, name, position, kind, health, behavior, damage, zone, null);
    if (kind.equals(Kind.FRIEND))
      return new Ally(personageType, name, position, zone, text);
    return null;
  }

  // TODO : add locked at "item to unlock" in obstacle constructor
  private Obstacle toObstacleEntity(ObstacleType obstacleType) {
    return new Obstacle(obstacleType, name, position, null);
  }

  private InventoryItem toWeaponEntity() {
    return switch (skin) {
      case BOLT -> new Bolt(name, damage);
      case SHOVEL -> new Shovel(name, damage);
      case STICK -> new Stick(name, damage);
      case SWORD -> new Sword(name, damage);
      default -> null;
    };
  }

  private InventoryItem toItemEntity(InventoryItemType item) {
    return switch (item) {
      // TODO: direction for box
      case BOLT, SHOVEL, STICK, SWORD -> toWeaponEntity();
      case BOX -> new Box(item, name, position, null);
      case KEY -> new Key(name);
      case MIRROR -> new Mirror();
      case SEED -> new Seed(name);
      case BOOK, PAPER -> new LoreItem(item, name, text);
      default -> new BasicItem(item, name);
    };
  }

  public Entity toEntity() {
    return switch (skin) {
      case null -> null;
      case PersonageType personageType -> toPersonageEntity(personageType);
      case ObstacleType obstacleType -> toObstacleEntity(obstacleType);
      case InventoryItemType inventoryItemType ->
          toItemEntity(inventoryItemType);
      default -> throw new IllegalStateException("Unexpected value: " + skin);
    };
//    if (skin == null)
//      return null;
//    if (health != 0)
//      return toPersonageEntity();
//    if (kind.equals(Kind.OBSTACLE))
//      return toObstacleEntity();
//    if (kind.equals(Kind.ITEM)) {
//      return switch (skin) {
//        case BOLT, SHOVEL, STICK, SWORD -> toWeaponEntity();
//        default -> toItemEntity();
//      };
//    }
//    return null;
  }
}
