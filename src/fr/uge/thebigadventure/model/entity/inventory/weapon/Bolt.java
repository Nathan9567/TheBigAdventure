package fr.uge.thebigadventure.model.entity.inventory.weapon;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * Bolt is a weapon that can be used by the player. It is a ranged weapon.
 * This weapon is a projectile that can be thrown by the player.
 *
 * @param name     name of the weapon
 * @param position position of the weapon
 * @param damage   weapon damage (health points removed to the target)
 */
public record Bolt(String name, Coordinates position,
                   int damage) implements WeaponInterface {

  /**
   * Constructor of the Bolt class.
   *
   * @param name     name of the weapon
   * @param position position of the weapon
   * @param damage   weapon damage (health points removed to the target)
   */
  public Bolt {
    new Weapon(InventoryItemRawType.BOLT, name, position, damage);
  }

  @Override
  public InventoryItemRawType skin() {
    return InventoryItemRawType.BOLT;
  }
}
