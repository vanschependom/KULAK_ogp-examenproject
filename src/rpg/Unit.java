package rpg;
import be.kuleuven.cs.som.annotate.*;
import rpg.State;

public enum Unit {

	PINCH(1.0/6, new State[]{State.POWDER}),
	DROP(1.0/8, new State[]{State.LIQUID}),
	SPOON(1, new State[]{State.LIQUID, State.POWDER}),
	VIAL(5, new State[]{State.LIQUID}),
	SACHET(7, new State[]{State.POWDER}),
	BOTTLE(15, new State[]{State.LIQUID}),
	BOX(42, new State[]{State.POWDER}),
	JUG(105, new State[]{State.LIQUID}),
	SACK(126, new State[]{State.POWDER}),
	BARREL(1260, new State[]{State.LIQUID}),
	CHEST(1260, new State[]{State.POWDER}),
	STOREROOM(6300, new State[]{State.LIQUID, State.POWDER});

	private final double spoonEquivalent;
	private final State[] allowedStates;

	private Unit(double spoonEquivalent, State[] allowedStates) {
		this.spoonEquivalent = spoonEquivalent;
		this.allowedStates = allowedStates;
	}

	@Basic
	public double getSpoonEquivalent() {
		return spoonEquivalent;
	}

	@Basic
	public State[] getAllowedStates() {
		return allowedStates;
	}

	boolean conversionAllowed(Unit unit) {
		for (State state : unit.getAllowedStates()) {
			for (State allowedState : this.allowedStates) {
				if (state == allowedState) {
					return true;
				}
			}
		}
		return false;
	}

	public double getConversionFor(Unit unit) {

		if (conversionAllowed(unit)) {
			return this.spoonEquivalent / unit.spoonEquivalent;
		} else {
			throw new IllegalArgumentException("Cannot convert " + this + " to " + unit);
		}

	}

}
