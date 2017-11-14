package de.queisler.battleship.businessLogic;

import de.queisler.battleship.businessLogic.enums.Alignment;
import de.queisler.battleship.businessLogic.exceptions.InvalidPointException;
import de.queisler.battleship.businessLogic.exceptions.InvalidPositionException;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class Position {
    private List<Point> points;

    public Position(Point startPoint, Alignment alignment, int length) throws InvalidPositionException {
        this.points = new ArrayList<>();

        if (alignment == Alignment.HORIZONTAL) {
            try {
                new Point(startPoint.getRow(), startPoint.getColumn() + length); // Grenzentest

                for (int i = 0; i < length; i++)
                    points.add(new Point(startPoint.getRow(), startPoint.getColumn() + i));

            } catch (InvalidPointException e) {
                throw new InvalidPositionException("Diese Position beinhaltet ungültige Punkte!");
            }
        } else {
            try {
                new Point(startPoint.getRow() + length, startPoint.getColumn()); // Grenzentest

                for (int i = 0; i < length; i++)
                    points.add(new Point(startPoint.getRow() + i, startPoint.getColumn()));

            } catch (InvalidPointException e) {
                throw new InvalidPositionException("Diese Position beinhaltet ungültige Punkte!");
            }
        }
    }

    public void markPointAsHit(Point point) throws InvalidPointException {
        if(points.contains(point)){
            int i = points.indexOf(point);
            points.get(i).setHit(true);
        }else{
            throw new InvalidPointException("Der angegebene Punkt existiert nicht!");
        }
    }

    public boolean isOverlapping(Position position){
        for(Point p : points){
            if (position.points.contains(p))
                return true;
        }
        return false;
    }

    public boolean isNextTo(Position position){
        for(Point p : points){
            for (Point otherP : position.points) {
                if(p.isNextTo(otherP))
                    return true;
            }
        }
        return false;
    }

    public boolean containsPoint(Point point) {
        return points.contains(point);
    }

    public boolean allHit() {
        for (Point p : points) {
            if (!p.isHit()) return false;
        }
        return true;
    }
}
