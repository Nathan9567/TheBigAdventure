package fr.uge.thebigadventure.skin.entities;

public enum InventoryItem implements Inventory {
    BOOK, BOLT, BOX, CASH, CLOCK, COG, CRYSTAL, CUP, DRUM, FLAG, GEM, GUITAR, HIHAT, KEY, LAMP, LEAF, MIRROR, MOON,
    ORB, PANTS, PAPER, PLANET, RING, ROSE, SAX, SCISSORS, SEED, SHIRT, SHOVEL, STAR, STICK, SUN, SWORD, TRUMPET, VASE;

    private String text = null;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
