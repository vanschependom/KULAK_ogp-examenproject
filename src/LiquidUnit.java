public enum LiquidUnit implements Unit {

	DROP(1.0/8),
	SPOON(1),
	VIAL(5),
	BOTTLE(15),
	JUG(105),
	BARREL(1260),
	STOREROOM(6300);

	private final double spoonEquivalent;

	LiquidUnit(double spoonEquivalent) {
		this.spoonEquivalent = spoonEquivalent;
	}

	public double getSpoonEquivalent() {
		return spoonEquivalent;
	}

	public double getConversionFor(Unit unit) {
		if (unit instanceof LiquidUnit) {
			LiquidUnit liquidUnit = (LiquidUnit) unit;
			return this.spoonEquivalent / liquidUnit.spoonEquivalent;
		}
		throw new IllegalArgumentException("Cannot convert LiquidUnit to " + unit.getClass().getSimpleName());
	}

}
