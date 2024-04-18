public class Quantity {

	private double amount;
	private Unit unit;

	public Quantity(double amount, Unit unit) {
		this.amount = amount;
		this.unit = unit;
	}

	public String toString() {
		return amount + " " + unit;
	}

	public void convertTo(Unit targetUnit) {
		amount *= unit.getConversionFor(targetUnit);
		unit = targetUnit;
	}

}
