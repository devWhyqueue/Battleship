package de.queisler.battleship.businessLogic.model;

import java.util.Arrays;

import de.queisler.battleship.businessLogic.enums.PointStatus;

public class FieldMap
{
	private PointStatus[][] fieldMap;

	public FieldMap()
	{
		fieldMap = new PointStatus[10][10];
		for (int i = 0; i < fieldMap.length; i++)
			for (int j = 0; j < fieldMap[0].length; j++)
				fieldMap[i][j] = PointStatus.UNKNOWN;
	}

	public void setStatus(Point point, PointStatus status)
	{
		fieldMap[point.getRow() - 1][point.getColumn() - 1] = status;
	}

	public PointStatus getStatus(Point point)
	{
		return fieldMap[point.getRow() - 1][point.getColumn() - 1];
	}

	public PointStatus[][] getPointStatusArray()
	{
		return Arrays.copyOf(fieldMap, fieldMap.length);
	}
}
