package de.queisler.battleship.businessLogic.enums;

public enum AttackResult {
	MISS, LOST, HIT, SUNK_CARRIER(ShipType.CARRIER), SUNK_BATTLESHIP(ShipType.BATTLESHIP), SUNK_CRUISER(
		ShipType.CRUISER), SUNK_SUBMARINE(ShipType.SUBMARINE), SUNK_DESTROYER(ShipType.DESTROYER);

	private ShipType shipType;

	AttackResult()
	{
	}

	AttackResult(ShipType shipType)
	{
		this.shipType = shipType;
	}

	public ShipType getShipType()
	{
		return shipType;
	}

	public static AttackResult build(ShipType shipType)
	{
		for (AttackResult r : AttackResult.values())
		{
			if (r.getShipType() != null && r.getShipType().equals(shipType))
				return r;
		}
		return null;
	}
}
