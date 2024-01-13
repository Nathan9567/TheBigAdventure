package fr.uge.thebigadventure.view.entity;

import java.awt.*;

public class Utils {

  public static void renderHealthBar(Graphics2D graphics2D, int x, int y, int width, int height, int health, int maxHealth) {
    graphics2D.setColor(new Color(77, 194, 26));
    graphics2D.fillRect(x, y, width * health / maxHealth, height);
    graphics2D.setColor(Color.BLACK);
    graphics2D.drawRect(x, y, width, height);
  }
}
