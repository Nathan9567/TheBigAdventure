package fr.uge.thebigadventure.model.entity.inventory.weapon;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * This record represents a stick. A stick in our game is a weapon.
 * A stick can be ignited like a sword.
 * The class use the weapon class to create the weapon.
 *
 * @see Weapon
 */
public final class Stick implements WeaponInterface {

  private final Weapon weapon;

  private boolean isIgnite;

  /**
   * Create a stick.
   * The stick is a weapon, so it take the same parameters as a weapon.
   * The stick is not ignite by default.
   *
   * @param name     The name of the stick.
   * @param position The position of the stick.
   * @param damage   The damage of the stick.
   */
  public Stick(String name, Coordinates position, int damage) {
    weapon = new Weapon(InventoryItemRawType.STICK, name, position, damage);
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
   * Return true if the stick is ignited.
   *
   * @return the state of the stick.
   */
  public boolean isIgnite() {
    return isIgnite;
  }

  /**
   * Set the ignite state of the stick.
   *
   * @param ignite the state to set.
   */
  public void setIgnite(boolean ignite) {
    isIgnite = ignite;
  }

  /**
   * Use the record Weapon to create a string representation of the stick.
   * The string representation is the same as the weapon.
   *
   * @return the string representation of the stick.
   * @see Weapon#toString()
   */
  @Override
  public String toString() {
    return weapon.toString();
  }
}
