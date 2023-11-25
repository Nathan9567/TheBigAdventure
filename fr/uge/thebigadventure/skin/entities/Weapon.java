package fr.uge.thebigadventure.skin.entities;

public enum Weapon implements Inventory {
    STICK, SHOVEL, SWORD,
    BOLT;

    private boolean ignite = false;
    private int damage = 0;

    public boolean isIgnite() {
        return ignite;
    }

    public void setIgnite(boolean ignite) {
        this.ignite = ignite;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
