package fr.uge.thebigadventure.model.entity.inventory.weapon;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

public record Shovel(String name, Coordinates position,
                     int damage) implements WeaponInterface {

  public Shovel {
    new Weapon(InventoryItemRawType.SHOVEL, name, position, damage);
  }

  @Override
  public InventoryItemRawType skin() {
    return InventoryItemRawType.SHOVEL;
  }
}
