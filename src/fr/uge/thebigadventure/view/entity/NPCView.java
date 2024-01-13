package fr.uge.thebigadventure.view.entity;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.entity.personage.Ally;
import fr.uge.thebigadventure.model.entity.personage.Enemy;
import fr.uge.thebigadventure.model.entity.personage.NPC;
import fr.uge.thebigadventure.view.MapView;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public record NPCView(NPC npc, int cellSize) {

  private static final EntityView entityView = new EntityView();

  public NPCView {
    Objects.requireNonNull(npc);
    if (cellSize <= 0) {
      throw new IllegalArgumentException("Cell size must be positive");
    }
  }

//  private void renderTextBubble(Graphics2D graphics2D, String text,
//                                Coordinates position) {
//    if (text == null) {
//    }
//    // I want to draw text in a bubble above the NPC
//    // This bubble is a rectangle of 4 cases width and 1 case height
//    // The text is centered at the west of the rectangle and
//    // is centered at the NPC position
//    // We want to have only 3 lines of text in the bubble
//    // If the text is longer than 3 lines, we want to display
//    // the first 3 lines and add "..." at the end of the third line
//
//
//  }

  private void renderTextBubble(Graphics2D graphics2D, String text, Coordinates position) {
    if (Objects.equals(text, "")) {
      return;
    }

    int bubbleWidth = cellSize * 4;
    int bubbleHeight = cellSize;
    int bubbleX = position.x() * cellSize - bubbleWidth / 2;
    int bubbleY = position.y() * cellSize - bubbleHeight * 2;

    // Draw the bubble
    graphics2D.setColor(Color.WHITE);
    graphics2D.fillRect(bubbleX, bubbleY, bubbleWidth, bubbleHeight);
    graphics2D.setColor(Color.BLACK);
    graphics2D.drawRect(bubbleX, bubbleY, bubbleWidth, bubbleHeight);

    // Split the text into words
    String[] words = text.split(" ");
    String[] lines = new String[3];
    int lineIndex = 0;
    StringBuilder currentLine = new StringBuilder();

    for (String word : words) {
      if (currentLine.length() + word.length() > bubbleWidth / 10) {
        if (lineIndex < 3) {
          lines[lineIndex++] = currentLine.toString();
          currentLine = new StringBuilder();
        } else {
          currentLine.append("...");
          break;
        }
      }
      currentLine.append(word).append(" ");
    }
    if (lineIndex < 3) {
      lines[lineIndex] = currentLine.toString();
    }

    // Draw the text
    graphics2D.setColor(Color.BLACK);
    for (int i = 0; i < lines.length; i++) {
      if (lines[i] != null) {
        graphics2D.drawString(lines[i], bubbleX + 5, bubbleY + (i + 1) * bubbleHeight / 4);
      }
    }
  }

  public void renderNPC(Graphics2D graphics2D, int currentDialogPosition) throws IOException {
    var NPCPositionCentered =
        MapView.coordinatesToPlayerCenteredMapCoordinates(npc.position());
    switch (npc) {
      case Enemy enemy -> renderEnemy(enemy, graphics2D, NPCPositionCentered);
      case Ally ally -> {
        entityView.drawEntityTileInMap(graphics2D,
            ally.skin(), NPCPositionCentered, cellSize);
        var replaceBackslashN = String.join(" ", ally.text().split("\n"));
        var text = Arrays.stream(replaceBackslashN.split(" "))
            .limit(currentDialogPosition + 1).collect(Collectors.joining(" "));
        renderTextBubble(graphics2D, text, NPCPositionCentered);
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
