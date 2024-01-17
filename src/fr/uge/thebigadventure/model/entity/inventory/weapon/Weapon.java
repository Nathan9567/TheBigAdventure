package fr.uge.thebigadventure.model.entity.inventory.weapon;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.type.util.WeaponType;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.Arrays;
import java.util.Objects;

/**
 * Weapon is the default implementation of the WeaponInterface.
 * It can be used to create a weapon that can be used by the player.
 *
 * @param skin     skin of the weapon (must be a weapon type)
 * @param name     name of the weapon
 * @param position position of the weapon (can be null)
 * @param damage   weapon damage
 */
public record Weapon(InventoryItemRawType skin, String name,
                     Coordinates position,
                     int damage) implements WeaponInterface {

  public Weapon {
    Objects.requireNonNull(skin);
    if (!isWeaponSkin(skin))
      throw new IllegalArgumentException("The skin must be a weapon type");
  }

  /**
   * Create a weapon with no position.
   * The position will be set to null.
   * The weapon will be created with the given skin, name and damage.
   *
   * @param skin   skin of the weapon (must be a weapon type)
   * @param name   name of the weapon
   * @param damage weapon damage
   * @see Weapon#Weapon(InventoryItemRawType, String, Coordinates, int)
   */
  public Weapon(InventoryItemRawType skin, String name, int damage) {
    this(skin, name, null, damage);
  }

  private boolean isWeaponSkin(InventoryItemRawType skin) {
    return Arrays.stream(WeaponType.values()).anyMatch(weaponType -> weaponType.name()
        .equals(skin.name()));
  }
}
