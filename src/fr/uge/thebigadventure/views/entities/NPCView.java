package fr.uge.thebigadventure.views.entities;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.entities.personages.Personage;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;

import java.awt.*;

public class NPCView {

  public void renderNPC(Graphics2D graphics2D, Personage npc, int cellSize) throws Exception {
    EntityView.drawEntityTile(graphics2D,
        npc.skin(), npc.position(), cellSize);
  }

  public void clearNPC(Graphics2D graphics2D, Coord lastPosition, int cellSize) {
    EntityView.clearTile(graphics2D, lastPosition, cellSize);
  }

  public void restoreLastTileState(Graphics2D graphics2D, PersonageType tile,
                                   Coord lastPosition, int cellSize) throws Exception {
    EntityView.drawEntityTile(graphics2D, tile, lastPosition, cellSize);
  }
}
