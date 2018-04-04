package de.queisler.battleship.businessLogic.enums;

public enum PointStatus {
	UNKNOWN, WATER, SHIP, SHIP_CARRIER(ShipType.CARRIER), SHIP_BATTLESHIP(ShipType.BATTLESHIP), SHIP_CRUISER(
		ShipType.CRUISER), SHIP_SUBMARINE(ShipType.SUBMARINE), SHIP_DESTROYER(ShipType.DESTROYER);

	private ShipType shipType;

	PointStatus()
	{
	}

	PointStatus(ShipType shipType)
	{
		this.shipType = shipType;
	}

	public ShipType getShipType()
	{
		return shipType;
	}

	public static PointStatus getByShipType(ShipType shipType)
	{
		for (PointStatus s : PointStatus.values())
		{
			if (s.getShipType() != null && s.getShipType().equals(shipType))
				return s;
		}
		return null;
	}
}
