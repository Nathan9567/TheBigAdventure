package fr.uge.thebigadventure.view.entity;

import fr.uge.thebigadventure.model.entity.personage.Ally;
import fr.uge.thebigadventure.model.entity.personage.Enemy;
import fr.uge.thebigadventure.model.entity.personage.NPC;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.view.MapView;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The NPC view, used to render the NPC in the map.
 *
 * @param npc      the NPC (not null)
 * @param cellSize the cell size (positive)
 */
public record NPCView(NPC npc, int cellSize) {

  private static final EntityView entityView = new EntityView();
  private static int page = 0;

  public NPCView {
    Objects.requireNonNull(npc);
    if (cellSize <= 0) {
      throw new IllegalArgumentException("Cell size must be positive");
    }
  }

  private List<String[]> splitText(String text, int maxWidth, FontMetrics fontMetrics) {
    String[] words = text.split("\\s+");
    StringBuilder currentLine = new StringBuilder();
    int currentWidth = 0;
    List<String[]> textLines = new ArrayList<>();
    for (String word : words) {
      int wordWidth = fontMetrics.stringWidth(word);
      if (currentWidth + wordWidth <= maxWidth) {
        currentLine.append(word).append(" ");
        currentWidth += wordWidth + fontMetrics.stringWidth(" ");
      } else {
        textLines.add(currentLine.toString().split("\\s+"));
        currentLine = new StringBuilder(word + " ");
        currentWidth = wordWidth + fontMetrics.stringWidth(" ");
      }
    }
    if (!currentLine.isEmpty()) {
      textLines.add(currentLine.toString().split("\\s+"));
    }
    return textLines;
  }

  private void drawBubble(Graphics2D graphics2D, int bubbleX, int bubbleY,
                          int bubbleWidth, int bubbleHeight) {
    // Draw the bubble
    graphics2D.setColor(new Color(255, 255, 255, 200));
    graphics2D.fillRect(bubbleX, bubbleY, bubbleWidth, bubbleHeight);
    graphics2D.setColor(new Color(0, 0, 0, 200));
    graphics2D.drawRect(bubbleX, bubbleY, bubbleWidth, bubbleHeight);

    // Draw the arrow
    int arrowX = bubbleX + bubbleWidth / 2;
    int arrowY = bubbleY + bubbleHeight;
    int arrowWidth = cellSize / 2;
    int arrowHeight = cellSize / 2;
    int[] xPoints = {arrowX, arrowX + arrowWidth, arrowX + arrowWidth / 2};
    int[] yPoints = {arrowY, arrowY, arrowY + arrowHeight};
    graphics2D.setColor(new Color(255, 255, 255, 200));
    graphics2D.fillPolygon(xPoints, yPoints, 3);
    graphics2D.setColor(new Color(0, 0, 0, 200));
    graphics2D.drawPolygon(xPoints, yPoints, 3);
  }

  private void drawTextInBubble(Graphics2D graphics2D, String text, int bubbleX, int bubbleY, int bubbleWidth) {
    FontMetrics fontMetrics = graphics2D.getFontMetrics();
    var lines = splitText(text, bubbleWidth - 10, fontMetrics);
    graphics2D.setColor(Color.BLACK);
    int y = bubbleY + (int) (fontMetrics.getHeight() * 0.75);
    for (int i = (page * 3); i < lines.size(); i++) {
      var line = String.join(" ", lines.get(i));
      line += (i == ((page + 1) * 3) - 1 && lines.size() > ((page + 1) * 3)) ? "..." : "";
      graphics2D.drawString(line, bubbleX + 5, y);
      if (i == ((page + 1) * 3) - 1 && lines.size() > ((page + 1) * 3)) {
        page++;
        break;
      }
      y += fontMetrics.getHeight();
    }
  }

  private void renderTextBubble(Graphics2D graphics2D, String text, Coordinates position) {
    if (Objects.equals(text, "")) {
      return;
    }
    int bubbleWidth = cellSize * 4;
    int bubbleHeight = cellSize;
    int bubbleX = position.x() * cellSize - bubbleWidth / 2;
    int bubbleY = position.y() * cellSize - bubbleHeight * 2;

    drawBubble(graphics2D, bubbleX, bubbleY, bubbleWidth, bubbleHeight);
    drawTextInBubble(graphics2D, text, bubbleX, bubbleY, bubbleWidth);
  }

  private boolean renderAlly(Ally ally, Graphics2D graphics2D, int currentDialogPosition, Coordinates NPCPositionCentered) throws IOException {
    entityView.drawEntityTileInMap(graphics2D, ally.skin(), NPCPositionCentered, cellSize);
    var text = Arrays.stream(ally.text().split(" "))
        .limit(currentDialogPosition + 1).collect(Collectors.joining(" "));
    renderTextBubble(graphics2D, text, NPCPositionCentered);
    if (text.equals(ally.text())) {
      page = 0;
      return true;
    }
    return false;
  }

  private void renderEnemy(Enemy enemy, Graphics2D graphics2D,
                           Coordinates position) throws IOException {
    var x = position.x() * cellSize;
    var y = position.y() * cellSize - cellSize / 4;
    entityView.renderHealthBar(graphics2D, x, y, cellSize, cellSize / 4,
        enemy.getHealth(), enemy.maxHealth());
    entityView.drawEntityTileInMap(graphics2D,
        enemy.skin(), position, cellSize);
  }

  /**
   * Render the NPC in the map. If the NPC is an Ally,
   * it will render the dialog in a bubble.
   * If the NPC is an Enemy, it will render the health bar.
   *
   * @param graphics2D            the graphics
   * @param currentDialogPosition the current dialog position
   * @return true if the Ally rendered dialog is finished
   * @throws IOException if the image cannot be loaded
   */
  public boolean renderNPC(Graphics2D graphics2D, int currentDialogPosition) throws IOException {
    var NPCPositionCentered =
        MapView.coordinatesToPlayerCenteredMapCoordinates(npc.position());
    switch (npc) {
      case Enemy enemy -> renderEnemy(enemy, graphics2D, NPCPositionCentered);
      case Ally ally -> {
        return renderAlly(ally, graphics2D, currentDialogPosition, NPCPositionCentered);
      }
    }
    return false;
  }
}
