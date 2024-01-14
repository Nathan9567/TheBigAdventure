package fr.uge.thebigadventure.model.entity.inventory.weapon;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.type.util.WeaponType;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.Arrays;
import java.util.Objects;

public record Weapon(InventoryItemRawType skin, String name,
                     Coordinates position,
                     int damage) implements WeaponInterface {

  public Weapon {
    Objects.requireNonNull(skin);
    if (!isWeaponSkin(skin))
      throw new IllegalArgumentException("The skin must be a weapon type");
  }

  public Weapon(InventoryItemRawType skin, String name, int damage) {
    this(skin, name, null, damage);
  }

  private boolean isWeaponSkin(InventoryItemRawType skin) {
    return Arrays.stream(WeaponType.values()).anyMatch(weaponType -> weaponType.name()
        .equals(skin.name()));
  }
}
