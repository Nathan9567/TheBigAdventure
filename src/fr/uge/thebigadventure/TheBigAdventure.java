package fr.uge.thebigadventure;

import fr.uge.thebigadventure.controllers.Lexer;
import fr.uge.thebigadventure.models.environments.EnvironmentObject;
import fr.uge.thebigadventure.models.interpreter.Result;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class TheBigAdventure {

    private static Lexer lexer;

    public static void main(String[] args) throws Exception {
        Application.run(Color.BLACK, context -> {

            // get the size of the screen
            ScreenInfo screenInfo = context.getScreenInfo();
            float width = screenInfo.getWidth();
            float height = screenInfo.getHeight();
            System.out.println("size of the screen (" + width + " x " + height + ")");

            Rectangle2D rectangle2D = new Rectangle2D.Float(1, 1, width, height);

//            context.renderFrame(graphics2D -> {
//                graphics2D.setColor(Color.WHITE);
//                graphics2D.fill(rectangle2D);
////                graphics2D.drawString("TA GROSSE MERE LA GENTILLE", 50, 50);
////                graphics2D.draw(new Rectangle2D.Float(0, 0, width, height));
//            });

            while (true) {
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

                context.renderFrame(graphics2D -> {
                    graphics2D.setColor(Color.WHITE);
                    graphics2D.fill(rectangle2D);
//                graphics2D.drawString("TA GROSSE MERE LA GENTILLE", 50, 50);
//                graphics2D.draw(new Rectangle2D.Float(0, 0, width, height));
                });

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

    private static void checkGridParams() {
        Result result;
        StringBuilder sb = new StringBuilder();
        List<Integer> sizes = null;
        Map<EnvironmentObject, Character> environmentEncodingMap = null;
        int matches = 0;
        while ((result = lexer.nextResult()) != null && matches < 3) {
            sb.append(result.content());
            switch (sb.toString()) {
                case "size:" -> {
                    matches++;
                    sizes = getSize();
                }
                case "encoding:" -> {
                    matches++;
                    environmentEncodingMap = getEncoding();
                }
//                case "data:" -> getData();
            }
        }
        System.out.println(sizes.toString());
        System.out.println(environmentEncodingMap.toString());
    }

    private static List<Integer> getSize() {
        Result result;
        StringBuilder sb = new StringBuilder();
        int cmpt = 0;
        while ((result = lexer.nextResult()) != null && cmpt < 5) {
            sb.append(result.content());
            cmpt++;
        }
        var sizePattern = Pattern.compile("\\((\\d+)x(\\d+)\\)");
        var matcher = sizePattern.matcher(sb);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid size format");
        }
        return List.of(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
    }


    private static Map<EnvironmentObject, Character> getEncoding() {
        Result result;
        StringBuilder sb = new StringBuilder();
        int cmpt = 0;
        while ((result = lexer.nextResult()) != null && cmpt < 5) {
            sb.append(result.content());
            cmpt++;
        }
        var sizePattern = Pattern.compile("(.+?)\\((.+?)\\)");
        var matcher = sizePattern.matcher(sb);
        while (matcher.matches()) {
            throw new IllegalArgumentException("Invalid size format");
        }
        System.out.println(matcher.groupCount());
//        return List.of(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        return null;
    }

}