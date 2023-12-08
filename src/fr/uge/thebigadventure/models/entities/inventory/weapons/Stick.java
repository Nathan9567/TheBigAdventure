package fr.uge.thebigadventure.models.entities.inventory.weapons;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public class Stick implements WeaponInterface {

  private final Weapon weapon;

  private boolean isIgnite;

  public Stick(String name, Coord position, int damage) {
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
  public Coord position() {
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
}
