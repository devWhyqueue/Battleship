package de.queisler.battleship.businessLogic.model;

import de.queisler.battleship.businessLogic.enums.PointStatus;

public class HitMap
{
	private PointStatus[][] hitMap;

	public HitMap()
	{
		hitMap = new PointStatus[10][10];
	}

	public void setStatus(Point point, PointStatus status)
	{
		hitMap[point.getRow() - 1][point.getColumn() - 1] = status;
	}

	public PointStatus getStatus(Point point)
	{
		return hitMap[point.getRow() - 1][point.getColumn() - 1];
	}
}
