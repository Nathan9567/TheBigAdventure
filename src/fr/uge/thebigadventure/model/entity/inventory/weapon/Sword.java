package fr.uge.thebigadventure.model.entity.inventory.weapon;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * Sword is a nice weapon that can be used to kill enemies.
 * It can be ignited to burn enemies.
 */
public final class Sword implements WeaponInterface {

  private final Weapon weapon;
  private boolean isIgnite;

  /**
   * Create a new sword with a name, a position and damages.
   *
   * @param name     name of the sword
   * @param position position of the sword
   * @param damage   damages of the sword
   */
  public Sword(String name, Coordinates position, int damage) {
    weapon = new Weapon(InventoryItemRawType.SWORD, name, position, damage);
  }

  @Override
  public String name() {
    return weapon.name();
  }

  @Override
  public InventoryItemRawType skin() {
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

  /**
   * Check if the sword is ignited.
   *
   * @return true if the sword is ignited.
   */
  public boolean isIgnite() {
    return isIgnite;
  }

  /**
   * Set the sword to ignited or not.
   *
   * @param ignite true if we want to ignite the sword.
   */
  public void setIgnite(boolean ignite) {
    isIgnite = ignite;
  }

  /**
   * Return the string representation of the sword.
   * It uses the toString method of the weapon.
   *
   * @return the string representation of the sword.
   */
  @Override
  public String toString() {
    return weapon.toString();
  }
}
