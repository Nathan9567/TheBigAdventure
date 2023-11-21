package fr.uge.thebigadventure;

public interface Element {
    default boolean player() {
        return false;
    }
}
