package fr.uge.thebigadventure.models.entities.weapons;

import fr.uge.thebigadventure.models.enums.utils.WeaponType;

public class Stick extends Weapon {
  public Stick(int damage) {
    super(WeaponType.STICK, damage);
  }

  public void setIgnite(boolean ignite) {
    isIgnite = ignite;
  }
}
