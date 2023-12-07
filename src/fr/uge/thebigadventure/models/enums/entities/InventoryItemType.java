package fr.uge.thebigadventure.models.enums.entities;

public enum InventoryItemType implements EntityType {
  BOLT, BOOK, BOX, CASH, CLOCK, COG, CRYSTAL, CUP, DRUM, FLAG, GEM, GUITAR,
  HIHAT, KEY, LAMP, LEAF, MIRROR, MOON, ORB, PANTS, PAPER, PLANET, RING, ROSE,
  SAX, SCISSORS, SEED, SHIRT, SHOVEL, STAR, STICK, SUN, SWORD, TRUMPET, VASE;

  @Override
  public String folder() {
    return "items";
  }

}