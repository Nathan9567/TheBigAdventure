package fr.uge.thebigadventure.models.entities.inventory.weapons;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

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
