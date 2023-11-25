package fr.uge.thebigadventure.skin.entities;

import fr.uge.thebigadventure.skin.background.Biome;
import fr.uge.thebigadventure.skin.background.Decoration;
import fr.uge.thebigadventure.skin.background.ElementMap;

public enum Transport {
    PLANE(Decoration.CLOUD, 2), ROCKET(Decoration.CLOUD, 2), UFO(Decoration.CLOUD, 2),
    CAR(Decoration.ROAD, 1.5),
    TRAIN(Decoration.TRACK, 1.75), CART(Decoration.TRACK, 1.75),
    BOAT(Biome.WATER, 1);

    private final ElementMap transportType;
    private final double speed;

    Transport(ElementMap transportType, double speed) {
        this.transportType = transportType;
        this.speed = speed;
    }

    public ElementMap getTransportType() {
        return transportType;
    }

    public double getSpeed() {
        return speed;
    }
}
