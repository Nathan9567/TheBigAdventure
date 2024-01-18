package fr.uge.thebigadventure.model.entity.inventory.weapon;

import fr.uge.thebigadventure.model.entity.inventory.InventoryItem;
import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;

/**
 * Weapon interface which is a specialization of the {@link InventoryItem}.
 * Contains all the methods that a weapon should have more over the
 * {@link InventoryItem}.
 */
public sealed interface WeaponInterface extends InventoryItem permits Bolt, Sword, Shovel, Stick, Weapon {

  @Override
  InventoryItemRawType skin();

  /**
   * Return the weapon damage. Of course if the damage is negative, it can be
   * considered as a healed weapon... warning, it's not a good idea to heal
   * someone with a sword.
   *
   * @return the weapon damage
   */
  int damage();

  @Override
  default boolean isWeapon() {
    return true;
  }
}
