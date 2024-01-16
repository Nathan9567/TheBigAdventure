package fr.uge.thebigadventure.model.entity.personage;

import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.type.entity.PersonageType;
import fr.uge.thebigadventure.model.utils.Coordinates;

public sealed interface Personage extends Entity permits Ghost, NPC, Player {
  @Override
  PersonageType skin();

  @Override
  Coordinates position();

  void setPosition(Coordinates position);

  default boolean isEnemy() {
    return switch (this) {
      case Ghost ghost -> ghost.getUnderlyingPersonage().isEnemy();
      case Player ignored -> false;
      case NPC npc -> npc.isEnemy();
    };
  }
}
