package fr.uge.thebigadventure.models.entities.decorations;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.enums.entities.DecorationType;

public record Decoration(DecorationType skin, String name,
                         Coord position) implements Entity {
}
