package fr.uge.thebigadventure.models.entities.inventory.weapons;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;
import fr.uge.thebigadventure.models.enums.utils.WeaponType;

import java.util.Arrays;
import java.util.Objects;

public record Weapon(InventoryItemType skin, String name, Coord position,
                     int damage) implements WeaponInterface {

  public Weapon {
    Objects.requireNonNull(skin);
    if (!isWeaponSkin(skin))
      throw new IllegalArgumentException("The skin must be a weapon type");
  }

  public Weapon(InventoryItemType skin, String name, int damage) {
    this(skin, name, null, damage);
  }

  private boolean isWeaponSkin(InventoryItemType skin) {
    return Arrays.stream(WeaponType.values()).anyMatch(weaponType -> weaponType.name()
        .equals(skin.name()));
  }
}
