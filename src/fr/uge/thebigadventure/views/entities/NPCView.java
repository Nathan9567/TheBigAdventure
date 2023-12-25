package fr.uge.thebigadventure.views.entities;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.entities.personages.NPC;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public record NPCView(NPC npc, int cellSize) {

  private static final EntityView entityView = new EntityView();

  private static Coordinates lastNPCPosition = null;

  public NPCView {
    Objects.requireNonNull(npc);
    if (cellSize <= 0) {
      throw new IllegalArgumentException("Cell size must be positive");
    }
  }

  public void renderNPC(Graphics2D graphics2D) throws IOException {
    clearLastNPCPosition(graphics2D);
    lastNPCPosition = npc.position();
    entityView.drawEntityTile(graphics2D,
        npc.skin(), npc.position(), cellSize);
  }

  private void clearLastNPCPosition(Graphics2D graphics2D) {
    if (lastNPCPosition != null) {
      entityView.clearTile(graphics2D, lastNPCPosition, cellSize);
    }
  }
}
