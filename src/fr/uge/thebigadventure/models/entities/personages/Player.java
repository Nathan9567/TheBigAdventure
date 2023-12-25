package fr.uge.thebigadventure.models.entities.personages;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.entities.inventory.InventoryItem;
import fr.uge.thebigadventure.models.entities.inventory.weapons.WeaponInterface;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;
import fr.uge.thebigadventure.models.enums.utils.Direction;
import fr.uge.thebigadventure.models.enums.utils.PlayerSkinType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Player implements Personage {

  private final PersonageType skin;
  private final String name;
  private final List<InventoryItem> inventory = new ArrayList<>();
  private final int maxHealth;
  private int currentHealth;
  private WeaponInterface weapon;
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

  public List<InventoryItem> getInventory() {
    return inventory;
  }

  public void addItemToInventory(InventoryItem item) {
    Objects.requireNonNull(item, "Item cannot be null");
    inventory.add(item);
  }

  public WeaponInterface getWeapon() {
    return weapon;
  }

  public void setWeapon(WeaponInterface weapon) {
    Objects.requireNonNull(weapon, "Weapon cannot be null");
    this.weapon = weapon;
  }

  public int health() {
    return currentHealth;
  }

  public void setCurrentHealth(int currentHealth) {
    this.currentHealth = currentHealth;
  }
}
