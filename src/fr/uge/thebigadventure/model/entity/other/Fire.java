package fr.uge.thebigadventure.model.entity.other;

import fr.uge.thebigadventure.model.type.entity.OtherType;
import fr.uge.thebigadventure.model.utils.Coordinates;

public record Fire(String name, Coordinates position) implements SpecialEntity {

  @Override
  public OtherType skin() {
    return OtherType.FIRE;
  }
}
