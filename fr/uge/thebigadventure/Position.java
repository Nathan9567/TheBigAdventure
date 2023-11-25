package fr.uge.thebigadventure;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("x and y must be positive");
        }
    }

    public void move(Direction direction) {
        switch (direction) {
            case NORTH -> y -= 1;
            case SOUTH -> y += 1;
            case WEST -> x -= 1;
            case EAST -> x += 1;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
