package fr.uge.thebigadventure.view.entity;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.entity.personage.Ally;
import fr.uge.thebigadventure.model.entity.personage.Enemy;
import fr.uge.thebigadventure.model.entity.personage.NPC;
import fr.uge.thebigadventure.view.MapView;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public record NPCView(NPC npc, int cellSize) {

  private static final EntityView entityView = new EntityView();

  public NPCView {
    Objects.requireNonNull(npc);
    if (cellSize <= 0) {
      throw new IllegalArgumentException("Cell size must be positive");
    }
  }

  private void renderTextBubble(Graphics2D graphics2D, String text,
                                Coordinates position) {
    if (text == null) {
      return;
    }
    graphics2D.drawString(text, position.x() * cellSize,
        position.y() * cellSize - cellSize / 2);
  }

  public void renderNPC(Graphics2D graphics2D) throws IOException {
    var NPCPositionCentered =
        MapView.coordinatesToPlayerCenteredMapCoordinates(npc.position());
    switch (npc) {
      case Enemy enemy -> renderEnemy(enemy, graphics2D, NPCPositionCentered);
      case Ally ally -> {
        entityView.drawEntityTileInMap(graphics2D,
            ally.skin(), NPCPositionCentered, cellSize);
        renderTextBubble(graphics2D, ally.text(), NPCPositionCentered);
      }
      default -> entityView.drawEntityTileInMap(graphics2D,
          npc.skin(), NPCPositionCentered, cellSize);
    }
  }

  private void renderEnemy(Enemy enemy, Graphics2D graphics2D,
                           Coordinates position) throws IOException {
    var x = position.x() * cellSize;
    var y = position.y() * cellSize - cellSize / 4;
    graphics2D.setColor(Color.BLACK);
    graphics2D.drawRect(x, y, cellSize, cellSize / 4);
    graphics2D.setColor(new Color(77, 194, 26));
    graphics2D.fillRect(x, y, cellSize * enemy.getHealth() / enemy.maxHealth(),
        cellSize / 4);
    entityView.drawEntityTileInMap(graphics2D,
        enemy.skin(), position, cellSize);
  }
}
