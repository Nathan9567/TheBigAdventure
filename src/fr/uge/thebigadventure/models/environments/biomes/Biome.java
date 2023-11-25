package fr.uge.thebigadventure.models.environments.biomes;

import fr.uge.thebigadventure.models.enums.environment.BiomeType;
import fr.uge.thebigadventure.models.environments.EnvironmentObject;

public class Biome extends EnvironmentObject {
    public Biome(BiomeType biomeType, char encoding) {
        super(biomeType, encoding);
    }
}
