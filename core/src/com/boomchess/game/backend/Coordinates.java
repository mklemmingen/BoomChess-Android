package com.boomchess.game.backend;

public class Coordinates {

    private int X;
    private int Y;

    // methods for setting and getting the coordinates

    public void setCoordinates(int x, int y) {
        // this method sets BOTH coordinates, for separate setting use setX and setY
        this.X = x;
        this.Y = y;
    }

    public void setX(int x) {
        this.X = x;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public int getX() {
        return this.X;
    }

    public int getY() {
        return this.Y;
    }

    public boolean checkEqual(Coordinates tile) {
        // method for checking if this coordinate object equals a parameter one
        return this.X == tile.getX() && this.Y == tile.getY();
    }
}
