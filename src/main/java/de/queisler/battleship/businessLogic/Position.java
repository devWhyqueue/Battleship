package de.queisler.battleship.businessLogic;

import de.queisler.battleship.businessLogic.enums.Alignment;
import de.queisler.battleship.businessLogic.exceptions.InvalidPointException;
import lombok.Getter;

import java.util.*;

@Getter
public class Position {
    private List<Point> points;

    public Position(Point startPoint, Alignment alignment, int length) throws InvalidPointException {
        this.points = new ArrayList<>();

        for(int i = 0; i < length; i++){
            if(alignment == Alignment.HORIZONTAL){
                points.add(new Point(startPoint.getRow(), startPoint.getColumn() + i));
            }else{
                points.add(new Point(startPoint.getRow() + i, startPoint.getColumn()));
            }
        }
    }

    public void markPoint(Point point) throws InvalidPointException{
        if(points.contains(point)){
            int i = points.indexOf(point);
            points.get(i).setHit(true);
        }else{
            throw new InvalidPointException("Der angegebene Punkt existiert nicht!");
        }
    }

    public boolean isOverlapping(Position position){
        for(Point p : points){
            if(position.getPoints().contains(p))
                return true;
        }
        return false;
    }

    public boolean isNextTo(Position position){
        for(Point p : points){
            for(Point otherP : position.getPoints()){
                if(p.isNextTo(otherP))
                    return true;
            }
        }
        return false;
    }
}
