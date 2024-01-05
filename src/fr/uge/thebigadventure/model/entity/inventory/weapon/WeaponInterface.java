package fr.uge.thebigadventure.model.entity.inventory.weapon;

import fr.uge.thebigadventure.model.entity.inventory.InventoryItem;
import fr.uge.thebigadventure.model.type.entity.InventoryItemType;

public sealed interface WeaponInterface extends InventoryItem permits Bolt, Sword, Shovel, Stick, Weapon {

  @Override
  InventoryItemType skin();

  int damage();

  @Override
  default boolean isWeapon() {
    return true;
  }
}
