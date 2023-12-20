package fr.uge.thebigadventure.views.entities;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.entities.personages.NPC;
import fr.uge.thebigadventure.models.entities.personages.Personage;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;

import java.awt.*;
import java.util.Objects;

public record NPCView(NPC npc, int cellSize) {

  private static final EntityView entityView = new EntityView();

  public NPCView {
    Objects.requireNonNull(npc);
  }

  public void renderNPC(Graphics2D graphics2D, Personage npc, int cellSize) throws Exception {
    entityView.drawEntityTile(graphics2D,
        npc.skin(), npc.position(), cellSize);
  }

  public void clearNPC(Graphics2D graphics2D, Coordinates lastPosition, int cellSize) {
    entityView.clearTile(graphics2D, lastPosition, cellSize);
  }

  public void restoreLastTileState(Graphics2D graphics2D, PersonageType tile,
                                   Coordinates lastPosition, int cellSize) throws Exception {
    entityView.drawEntityTile(graphics2D, tile, lastPosition, cellSize);
  }
}
