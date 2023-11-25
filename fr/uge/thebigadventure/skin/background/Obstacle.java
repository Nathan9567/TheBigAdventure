package fr.uge.thebigadventure.skin.background;

import fr.uge.thebigadventure.skin.entities.InventoryItem;

public enum Obstacle implements ElementMap {
    BED, BOG, BOMB, BRICK, CHAIR, CLIFF, DOOR, FENCE, FORT, GATE, HEDGE, HOUSE, HUSK, HUSKS, LOCK, MONITOR, PIANO,
    PILLAR, PIPE, ROCK, RUBBLE, SHELL, SIGN, SPIKE, STATUE, STUMP, TABLE, TOWER, TREE, TREES, WALL;

    private final String name = this.toString().toUpperCase();
    private String encoding = null;
    private InventoryItem unlockItem = null;
    private String teleportMap = null;

    @Override
    public String getEncoding() {
        return encoding;
    }

    @Override
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String getName() {
        return name;
    }

    public InventoryItem getUnlockItem() {
        return unlockItem;
    }

    public void setUnlockItem(InventoryItem unlockItem) {
        this.unlockItem = unlockItem;
    }

    public String getTeleportMap() {
        return teleportMap;
    }

    public void setTeleportMap(String teleportMap) {
        this.teleportMap = teleportMap;
    }
}
