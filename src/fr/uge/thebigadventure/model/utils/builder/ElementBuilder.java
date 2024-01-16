package fr.uge.thebigadventure.model.utils.builder;

import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.entity.inventory.*;
import fr.uge.thebigadventure.model.entity.inventory.weapon.Bolt;
import fr.uge.thebigadventure.model.entity.inventory.weapon.Shovel;
import fr.uge.thebigadventure.model.entity.inventory.weapon.Stick;
import fr.uge.thebigadventure.model.entity.inventory.weapon.Sword;
import fr.uge.thebigadventure.model.entity.obstacle.Obstacle;
import fr.uge.thebigadventure.model.entity.other.Bucket;
import fr.uge.thebigadventure.model.entity.other.Fire;
import fr.uge.thebigadventure.model.entity.other.Lever;
import fr.uge.thebigadventure.model.entity.other.Wind;
import fr.uge.thebigadventure.model.entity.personage.Ally;
import fr.uge.thebigadventure.model.entity.personage.Enemy;
import fr.uge.thebigadventure.model.entity.personage.Player;
import fr.uge.thebigadventure.model.type.entity.*;
import fr.uge.thebigadventure.model.type.util.Behavior;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.model.type.util.Kind;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.ElementRef;
import fr.uge.thebigadventure.model.utils.Trade;
import fr.uge.thebigadventure.model.utils.Zone;

import java.util.List;
import java.util.Objects;

import static fr.uge.thebigadventure.model.type.entity.InventoryItemRawType.*;

public class ElementBuilder {
  private String name = null;
  private EntityType skin = null;
  private boolean player = false;
  private Coordinates position = null;
  private int health = 1;
  private Kind kind = null;
  private Zone zone = null;
  private Behavior behavior = null;
  private int damage = 0;
  private String text = null;
  private List<InventoryItemType> steal = null;
  private List<Trade> trades = null;
  private ElementRef locked = null;
  private Direction flow = null;
  private boolean phantomized = false;
  private String teleport = null;

  /**
   * Set the name of the entity.
   *
   * @param name the name of the entity.
   */
  public void setName(String name) {
    Objects.requireNonNull(name);
    this.name = name;
  }

  /**
   * Set the skin of the entity.
   *
   * @param skin the skin of the entity.
   */
  public void setSkin(EntityType skin) {
    Objects.requireNonNull(skin);
    this.skin = skin;
  }

  /**
   * Set if the entity is a player.
   *
   * @param player true if the entity is a player.
   */
  public void setPlayer(boolean player) {
    this.player = player;
  }

  /**
   * Set the position of the entity.
   *
   * @param position the position of the entity.
   */
  public void setPosition(Coordinates position) {
    Objects.requireNonNull(position);
    this.position = position;
  }

  /**
   * Set the health of the entity.
   *
   * @param health the health of the entity, must be positive.
   */
  public void setHealth(int health) {
    if (health < 0) {
      throw new IllegalArgumentException("health < 0");
    }
    this.health = health;
  }

  /**
   * Set the kind of the entity.
   *
   * @param kind the kind of the entity.
   */
  public void setKind(Kind kind) {
    Objects.requireNonNull(kind);
    this.kind = kind;
  }

  /**
   * Set the zone of the entity.
   *
   * @param zone the zone of the entity.
   */
  public void setZone(Zone zone) {
    Objects.requireNonNull(zone);
    this.zone = zone;
  }

  /**
   * Set the behavior of the entity.
   *
   * @param behavior the behavior of the entity.
   */
  public void setBehavior(Behavior behavior) {
    Objects.requireNonNull(behavior);
    this.behavior = behavior;
  }

  /**
   * Set the damage to the entity.
   *
   * @param damage the damage to the entity, must be positive.
   */
  public void setDamage(int damage) {
    if (damage < 0) {
      throw new IllegalArgumentException("damage < 0");
    }
    this.damage = damage;
  }

  /**
   * Set the text of the entity.
   *
   * @param text the text of the entity.
   */
  public void setText(String text) {
    Objects.requireNonNull(text);
    this.text = text.replaceAll("\n", " ");
  }

  /**
   * Set the trades of the entity.
   *
   * @param trades the trades of the entity.
   */
  public void setTrades(List<Trade> trades) {
    trades = List.copyOf(trades);
    if (trades.isEmpty()) {
      throw new IllegalArgumentException("trades list is empty");
    }
    this.trades = trades;
  }

  /**
   * Set the steal of the entity.
   *
   * @param steal the steal of the entity.
   */
  public void setSteal(List<InventoryItemType> steal) {
    steal = List.copyOf(steal);
    if (steal.isEmpty()) {
      throw new IllegalArgumentException("steal list is empty");
    }
    this.steal = steal;
  }

  /**
   * Set the item to unlock the entity.
   * If the entity is not an obstacle, this method will throw an exception.
   *
   * @param locked the item to unlock the entity.
   */
  public void setLocked(ElementRef locked) {
    this.locked = locked;
  }

  /**
   * Set the flow of the entity.
   *
   * @param flow the flow of the entity.
   */
  public void setFlow(Direction flow) {
    Objects.requireNonNull(flow);
    this.flow = flow;
  }

  /**
   * Set the phantomized of the entity.
   *
   * @param phantomized the phantomized of the entity.
   */
  public void setPhantomized(boolean phantomized) {
    this.phantomized = phantomized;
  }

  /**
   * Set the teleport of the entity.
   *
   * @param teleport the teleport of the entity.
   */
  public void setTeleport(String teleport) {
    Objects.requireNonNull(teleport);
    this.teleport = teleport;
  }

  private Entity toPersonageEntity(PersonageType personageType) {
    if (player)
      return new Player(personageType, name, position, health);
    if (kind == Kind.ENEMY)
      return new Enemy(personageType, name, position, health, behavior, damage, zone, steal);
    if (kind == Kind.FRIEND)
      return new Ally(personageType, name, position, zone, text, trades);
    if (kind == Kind.ITEM)
      return new Food(personageType, health, name, position);
    throw new IllegalStateException("Can't find which PersonageEntity is this");
  }

  private Obstacle toObstacleEntity(ObstacleType obstacleType) {
    if (kind != Kind.OBSTACLE)
      System.err.println("Warning: obstacle without kind");
    return new Obstacle(obstacleType, name, position, locked);
  }

  private InventoryItem toWeaponEntity() {
    return switch (skin) {
      case BOLT -> new Bolt(name, position, damage);
      case SHOVEL -> new Shovel(name, position, damage);
      case STICK -> new Stick(name, position, damage);
      case SWORD -> new Sword(name, position, damage);
      default ->
          throw new IllegalStateException("Can't find which WeaponEntity is this");
    };
  }

  /**
   * Create an entity from the current state of the element builder.
   *
   * @param item the item type.
   * @return the entity.
   */
  public InventoryItem toItemEntity(InventoryItemRawType item) {
    return switch (item) {
      case BOLT, SHOVEL, STICK, SWORD -> toWeaponEntity();
      case BOX -> new Box(name, position, null);
      case KEY -> new Key(name, position);
      case MIRROR -> new Mirror(name, position);
      case SEED -> new Seed(name, position);
      case BOOK, PAPER -> new LoreItem(item, name, text, position);
      default -> new BasicItem(item, name, position);
    };
  }

  /**
   * Create a food entity from the current state of the element builder.
   *
   * @param foodType the food type.
   * @return the entity.
   */
  public Food toFoodEntity(FoodType foodType) {
    return new Food(foodType, health, name, position);
  }

  private Entity toOtherEntity(OtherType otherType) {
    return switch (otherType) {
      case BUCKET -> new Bucket(name, position);
      case LEVER -> new Lever(name, position);
      // Strange case, but it's actually the only way to create a fire
      case FIRE -> new Fire(name, position);
      case WIND -> new Wind(zone, flow);
      default ->
          throw new IllegalStateException("Can't find which OtherEntity is this");
    };
  }

  /**
   * Create an entity from the current state of the element builder.
   *
   * @return the entity.
   */
  public Entity toEntity() {
    return switch (skin) {
      case ObstacleType obstacleType -> toObstacleEntity(obstacleType);
      case InventoryItemRawType inventoryItemRawType ->
          toItemEntity(inventoryItemRawType);
      case PersonageType personageType -> toPersonageEntity(personageType);
      case FoodType foodType -> toFoodEntity(foodType);
      case OtherType otherType -> toOtherEntity(otherType);
      case null -> throw new IllegalStateException("No skin provided !");
      default -> throw new IllegalStateException("Unexpected value: " + skin);
    };
  }
}
