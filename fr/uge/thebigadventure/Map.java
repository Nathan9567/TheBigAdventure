package fr.uge.thebigadventure;

public class Map {

  private final int width;
  private final int height;
  private final Element[][] elem;

  public Map(int width, int height) {
    this.width = width;
    this.height = height;
    elem = new Element[height][width];
  }

  public void setElement(int x, int y, Element e) {
    elem[y][x] = e;
  }

}
