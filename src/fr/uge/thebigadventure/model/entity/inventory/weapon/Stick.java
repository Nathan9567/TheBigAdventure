package fr.uge.thebigadventure.model.entity.inventory.weapon;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.type.entity.InventoryItemType;

public final class Stick implements WeaponInterface {

  private final Weapon weapon;

  private boolean isIgnite;

  public Stick(String name, Coordinates position, int damage) {
    weapon = new Weapon(InventoryItemType.STICK, name, position, damage);
  }

  public Stick(String name, int damage) {
    this(name, null, damage);
  }

  @Override
  public String name() {
    return weapon.name();
  }

  @Override
  public InventoryItemType skin() {
    return weapon.skin();
  }

  @Override
  public Coordinates position() {
    return weapon.position();
  }

  @Override
  public int damage() {
    return weapon.damage();
  }

  public boolean isIgnite() {
    return isIgnite;
  }

  public void setIgnite(boolean ignite) {
    isIgnite = ignite;
  }

  @Override
  public String toString() {
    return weapon.toString();
  }
}
