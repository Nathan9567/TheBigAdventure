package fr.uge.thebigadventure.model.entity.inventory.weapon;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * This record represents a shovel. A shovel in our game is a weapon.
 *
 * @param name     The name of the shovel.
 * @param position The position of the shovel.
 * @param damage   The damage of the shovel.
 * @see Weapon
 */
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
