package fr.uge.thebigadventure.models.entities.weapons;

import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public record Shovel(String name,
                     int damage) implements WeaponInterface {

  public Shovel {
    new Weapon(InventoryItemType.SHOVEL, name, damage);
  }


  @Override
  public InventoryItemType skin() {
    return InventoryItemType.SHOVEL;
  }
}
