package fr.uge.thebigadventure;

import fr.uge.thebigadventure.skin.background.ElementMap;

public class Map {

    private final int width;
    private final int height;
    private final ElementMap[][] elem;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        elem = new ElementMap[height][width];
    }

    public void setElement(int x, int y, ElementMap e) {
        elem[y][x] = e;
    }

}
