package fr.uge.thebigadventure.model.entity.inventory.weapon;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

public record Bolt(String name, Coordinates position,
                   int damage) implements WeaponInterface {

  public Bolt {
    new Weapon(InventoryItemRawType.BOLT, name, position, damage);
  }

  @Override
  public InventoryItemRawType skin() {
    return InventoryItemRawType.BOLT;
  }
}
