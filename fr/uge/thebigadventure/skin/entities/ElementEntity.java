package fr.uge.thebigadventure.skin.entities;

import fr.uge.thebigadventure.Position;
import fr.uge.thebigadventure.skin.Element;
import fr.uge.thebigadventure.skin.entities.types.KindElement;

public interface ElementEntity extends Element {
    void setName(String name);

    Element getSkin();

    default boolean isPlayer() {
        return false;
    }

    Position getPosition();

    KindElement getKind();

    boolean canPhantomized();
}
