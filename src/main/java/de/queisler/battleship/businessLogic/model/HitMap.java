package de.queisler.battleship.businessLogic.model;

public class HitMap
{
	private int[][] hitMap; // 0: unknown, 1: water, 2: ship

	public HitMap()
	{
		hitMap = new int[10][10];
	}

	public void setStatus(Point point, int status)
	{
		hitMap[point.getRow() - 1][point.getColumn() - 1] = status;
	}

	public int getStatus(Point point)
	{
		return hitMap[point.getRow() - 1][point.getColumn() - 1];
	}
}
