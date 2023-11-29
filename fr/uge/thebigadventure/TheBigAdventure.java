package fr.uge.thebigadventure;

import fr.uge.thebigadventure.controllers.Parser;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.views.MapView;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TheBigAdventure {

  public static void main(String[] args) throws Exception {
    // Number of tiles to show :
    int nb_tiles = 50;

    File file = new File("resources/test.map");
    BufferedReader reader = new BufferedReader(new FileReader(file));
    StringBuilder stringBuilder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      stringBuilder.append(line);
      stringBuilder.append("\n");
    }
    reader.close();
    GameMap gameMap = Parser.parse(stringBuilder.toString());
    Application.run(Color.BLACK, context -> {

      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();
      float width = screenInfo.getWidth();
      float height = screenInfo.getHeight();
      System.out.println("size of the screen (" + width + " x " + height + ")");
      float cell = width / nb_tiles;

      Rectangle2D rectangle2D = new Rectangle2D.Float(1, 1, width, height);

//            context.renderFrame(graphics2D -> {
//                graphics2D.setColor(Color.WHITE);
//                graphics2D.fill(rectangle2D);
////                graphics2D.drawString("TA GROSSE MERE LA GENTILLE", 50, 50);
////                graphics2D.draw(new Rectangle2D.Float(0, 0, width, height));
//            });

      while (true) {
        context.renderFrame(graphics2D -> {
          graphics2D.setColor(new Color(113, 94, 68, 255));
          graphics2D.fill(rectangle2D);
          MapView.drawMap(gameMap, graphics2D, (int) cell);
//                graphics2D.drawString("TA GROSSE MERE LA GENTILLE", 50, 50);
//                graphics2D.draw(new Rectangle2D.Float(0, 0, width, height));
        });

        Event event = context.pollOrWaitEvent(10);
        if (event == null) {
          continue;
        }

        // Quit application on key pressed or released
        Action action = event.getAction();
        if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
          context.exit(0);
          return;
        }

      }
    });

//        var path = Path.of("resources/test.map");
//        var text = Files.readString(path);
//        lexer = new Lexer(text);
//        Result result;
//        StringBuilder sb = new StringBuilder();
//        while ((result = lexer.nextResult()) != null) {
//            sb.append(result.content());
//            System.out.println(result);
//            if (sb.toString().equals("[grid]")) {
//                // checkGridParams();
//                sb.setLength(0);
//            }
//        }
  }

}