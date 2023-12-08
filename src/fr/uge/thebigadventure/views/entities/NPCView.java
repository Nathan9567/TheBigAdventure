package fr.uge.thebigadventure.views.entities;

import fr.uge.thebigadventure.models.entities.personages.Personage;

import java.awt.*;

public class NPCView {

  public void renderNPC(Graphics2D graphics2D, Personage npc, int cellSize) throws Exception {
    EntityView.drawEntityTile(graphics2D,
            npc.skin(), npc.position(), cellSize);
  }

  public void clearNPC(Graphics2D graphics2D, Personage npc, int cellSize) {
    EntityView.clearTile(graphics2D, npc.position(), cellSize);
  }

  public void restoreLastTileState(Graphics2D graphics2D, Personage npc,
                                   int cellSize) throws Exception {
    EntityView.drawEntityTile(graphics2D, npc.skin(), npc.position(), cellSize);
  }
}
