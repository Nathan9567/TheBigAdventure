package fr.uge.thebigadventure.models.entities.inventory.weapons;

import fr.uge.thebigadventure.models.entities.inventory.InventoryItem;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public sealed interface WeaponInterface extends InventoryItem permits Bolt, Sword, Shovel, Stick, Weapon {

  @Override
  InventoryItemType skin();

  int damage();

  @Override
  default boolean isWeapon() {
    return true;
  }
}
