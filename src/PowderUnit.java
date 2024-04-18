public enum PowderUnit implements Unit {

	PINCH(1.0/6),
	SPOON(1),
	SACHET(7),
	BOX(42),
	SACK(126),
	CHEST(1260),
	STOREROOM(6300);

	private final double spoonEquivalent;

	PowderUnit(double spoonEquivalent) {
		this.spoonEquivalent = spoonEquivalent;
	}

	public double getSpoonEquivalent() {
		return spoonEquivalent;
	}

	public double getConversionFor(Unit unit) {
		if (unit instanceof PowderUnit) {
			PowderUnit powderUnit = (PowderUnit) unit;
			return this.spoonEquivalent / powderUnit.spoonEquivalent;
		}
		throw new IllegalArgumentException("Cannot convert LiquidUnit to " + unit.getClass().getSimpleName());
	}

}
