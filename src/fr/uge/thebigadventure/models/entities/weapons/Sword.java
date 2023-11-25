package fr.uge.thebigadventure.models.entities.weapons;

import fr.uge.thebigadventure.models.enums.entities.WeaponType;

public class Sword extends Weapon {
    public Sword(int damage) {
        super(WeaponType.SWORD, damage);
    }

    public void setIgnite(boolean ignite) {
        isIgnite = ignite;
    }
}
