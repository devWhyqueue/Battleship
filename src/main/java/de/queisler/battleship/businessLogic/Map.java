package de.queisler.battleship.businessLogic;

public class Map {
    private boolean[][] hitMap;

    public Map() {
        hitMap = new boolean[10][10];
    }

    public void setStatus(Point point) {
        hitMap[point.getRow()][point.getColumn()] = true;
    }

    public boolean getStatus(Point point) {
        return hitMap[point.getRow()][point.getColumn()];
    }
}
