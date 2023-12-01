package fr.uge.thebigadventure.models.entities.weapons;

import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;
import fr.uge.thebigadventure.models.enums.utils.WeaponType;

public class Weapon implements Entity {
  private final int damage;
  private final WeaponType weaponType;
  protected boolean isIgnite;

  public Weapon(WeaponType weaponType, int damage) {
    this.weaponType = weaponType;
    this.damage = damage;
    this.isIgnite = false;
  }

  public int getDamage() {
    return damage;
  }

  public WeaponType getWeaponType() {
    return weaponType;
  }

  public boolean isIgnite() {
    return isIgnite;
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public InventoryItemType getSkin() {
    return null;
  }
}
