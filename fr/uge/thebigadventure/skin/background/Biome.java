package fr.uge.thebigadventure.skin.background;

import fr.uge.thebigadventure.Direction;
import fr.uge.thebigadventure.Zone;

public enum Biome implements ElementMap {
    ICE, LAVA, WATER;

    private final String name = this.toString().toUpperCase();
    private String encoding = null;

    private Zone zone = null;
    private Direction flow = null;

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


    Zone getZone() {
        return zone;
    }

    void setZone(Zone zone) {
        this.zone = zone;
    }

    public Direction getFlow() {
        return flow;
    }

    public void setFlow(Direction flow) {
        this.flow = flow;
    }
}
