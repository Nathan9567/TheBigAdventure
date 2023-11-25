package fr.uge.thebigadventure.skin.entities;

import fr.uge.thebigadventure.Direction;

public enum Special {
    BOOK, PAPER, BOX, BUCKET, FIRE, GHOST, KEY, LEVER, MIRROR, SEED, SPROUT, WIND;

    private Direction flow = null;

    public Direction getFlow() {
        return flow;
    }

    public void setFlow(Direction flow) {
        this.flow = flow;
    }

}
