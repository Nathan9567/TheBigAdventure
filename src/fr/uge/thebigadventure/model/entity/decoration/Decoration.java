package fr.uge.thebigadventure.model.entity.decoration;

import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.type.entity.DecorationType;

public record Decoration(DecorationType skin, String name,
                         Coordinates position) implements Entity {
}
