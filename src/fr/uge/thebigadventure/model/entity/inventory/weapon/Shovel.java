package fr.uge.thebigadventure.model.entity.inventory.weapon;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.enums.entity.InventoryItemType;

public record Shovel(String name, Coordinates position,
                     int damage) implements WeaponInterface {

  public Shovel {
    new Weapon(InventoryItemType.SHOVEL, name, position, damage);
  }

  public Shovel(String name, int damage) {
    this(name, null, damage);
  }


  @Override
  public InventoryItemType skin() {
    return InventoryItemType.SHOVEL;
  }
}
