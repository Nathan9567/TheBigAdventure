package fr.uge.thebigadventure.skin.entities;

import fr.uge.thebigadventure.Position;
import fr.uge.thebigadventure.Zone;
import fr.uge.thebigadventure.skin.Element;
import fr.uge.thebigadventure.skin.entities.types.Behavior;
import fr.uge.thebigadventure.skin.entities.types.KindElement;

import java.util.List;
import java.util.Map;

public class Character implements ElementEntity {
    private final Element skin = this;
    private Zone zone = null;
    private int health = 0;
    private Behavior behavior = null;
    private int damage = 0;
    private boolean isPhantomized = false;
    private List<InventoryItem> stealableItems = null;
    private Map<Element, String> tradeableItems = null;
    private String dialog = null;
    private String name = null;
    private Position position = null;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Zone getZone() {
        return zone;
    }

    void setZone(Zone zone) {
        this.zone = zone;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public List<InventoryItem> getStealableItems() {
        return stealableItems;
    }

    public void setStealableItems(List<InventoryItem> stealableItems) {
        this.stealableItems = stealableItems;
    }

    public Map<Element, String> getTradeableItems() {
        return tradeableItems;
    }

    public void setTradeableItems(Map<Element, String> tradeableItems) {
        this.tradeableItems = tradeableItems;
    }

    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    public boolean isPhantomized() {
        return isPhantomized;
    }

    public void setPhantomized(boolean phantomized) {
        isPhantomized = phantomized;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Element getSkin() {
        return this.skin;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public KindElement getKind() {
        return null;
    }

    @Override
    public boolean canPhantomized() {
        return false;
    }

    private enum Type {
        BABA, BADBAD, BAT, BEE, BIRD, BUG, BUNNY, CAT, CRAB,
        DOG, FISH, FOFO, FROG, GHOST, IT, JELLY, JIJI, KEKE,
        LIZARD, ME, MONSTER, ROBOT, SNAIL, SKULL, TEETH, TURTLE, WORM
    }
}
