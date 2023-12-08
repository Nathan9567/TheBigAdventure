package fr.uge.thebigadventure.models.entities.inventory.weapons;

import fr.uge.thebigadventure.models.entities.inventory.InventoryItem;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public interface WeaponInterface extends InventoryItem {

  @Override
  InventoryItemType skin();

  int damage();
}
