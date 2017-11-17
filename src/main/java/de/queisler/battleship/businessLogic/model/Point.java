package de.queisler.battleship.businessLogic.model;

import de.queisler.battleship.businessLogic.exceptions.InvalidPointException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = {"row", "column"})
public class Point {
    private int row;
    private int column;
    @Setter
    private boolean hit;

    public Point(int row, int column) throws InvalidPointException {
        if(isValidRowOrColumn(row) && isValidRowOrColumn(column)){
            this.column = column;
            this.row = row;
            this.hit = false;
        }else{
            throw new InvalidPointException("Der Punkt ist nicht gültig!");
        }
    }

    private boolean isValidRowOrColumn(int rowOrColumn){
        return rowOrColumn > 0 && rowOrColumn < 11;
    }

    public boolean isNextTo(Point point){
        boolean isAbove = column == point.getColumn() && point.getRow() == (row + 1);
        boolean isBelow = column == point.getColumn() && point.getRow() == (row - 1);
        boolean isLeft = row == point.getRow() && point.getColumn() == (column - 1);
        boolean isRight = row == point.getRow() && point.getColumn() == (column + 1);

        return isAbove || isBelow || isLeft || isRight;
    }
}
