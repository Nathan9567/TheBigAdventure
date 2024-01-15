package fr.uge.thebigadventure.model.entity.decoration;

import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.type.entity.DecorationType;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.Objects;

public record Decoration(DecorationType skin, String name,
                         Coordinates position) implements Entity {

  public Decoration {
    Objects.requireNonNull(skin);
    Objects.requireNonNull(position);
  }
}
