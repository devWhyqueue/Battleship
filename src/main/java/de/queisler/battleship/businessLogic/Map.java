package de.queisler.battleship.businessLogic;

import de.queisler.battleship.businessLogic.exceptions.PositionAlreadyMarkedException;

public class Map {
    private boolean[][] hitMap;

    public Map() {
        hitMap = new boolean[10][10];
    }

    public void markPos(Point position) throws PositionAlreadyMarkedException {
        int row = position.getRow() - 1;
        int column = position.getColumn() - 65; // uppercase

        if(!hitMap[row][column]){
            hitMap[row][column] = true;
        }
        else{
            throw new PositionAlreadyMarkedException("Diese Point wurde bereits attackiert!");
        }
    }

    public boolean getPosStatus(Point position){
        int row = position.getRow() - 1;
        int column = position.getColumn() - 65; // uppercase

        return hitMap[row][column];
    }
}
