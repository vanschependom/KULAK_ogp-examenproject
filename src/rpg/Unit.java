package rpg;
import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration of units.
 *
 * @note 	We use NOMINAL PROGRAMMING for this class.
 *
 * @author 	Vincent Van Schependom
 * @author	Arne Claerhout
 * @author 	Flor De Meulemeester
 * @version 1.0
 */
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

	/**
	 * Variable with the spoon equivalent value of that unit.
	 */
	private final double spoonEquivalent;

	/**
	 * A list of states containing the states which the unit can be used for.
	 */
	private final State[] allowedStates;

	/**
	 * Initialize a new unit with a given spoon equivalent and a list of allowed states.
	 *
	 * @param 	spoonEquivalent
	 *			The spoon equivalent value of the new unit.
	 * @param 	allowedStates
	 *			The list of allowed states which the new unit can be used for.
	 * @post	The spoon equivalent of the new unit is set to the given spoon equivalent.
	 * 			| new.getSpoonEquivalent().equals(spoonEquivalent)
	 * @post	The list of allowed states of the new unit is set to the given list of allowed states.
	 * 			| new.getAllowedStates().equals(allowedStates)
	 */
	@Model
	private Unit(double spoonEquivalent, State[] allowedStates) {
		this.spoonEquivalent = spoonEquivalent;
		this.allowedStates = allowedStates;
	}

	/**
	 * Return the spoon equivalent of this unit.
	 */
	@Basic
	public double getSpoonEquivalent() {
		return spoonEquivalent;
	}

	/**
	 * Return the list of allowed states of this unit.
	 */
	@Basic
	public State[] getAllowedStates() {
		return allowedStates;
	}

	public boolean conversionAllowed(Unit unit) {
		for (State state : unit.getAllowedStates()) {
			for (State allowedState : this.allowedStates) {
				if (state == allowedState) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * A method to convert the spoon equivalent value to a different unit.
	 *
	 * @param 	unit
	 * 			The unit you want to convert to.
	 * @return 	...
	 */
	public double getConversionFor(Unit unit) {
		if (conversionAllowed(unit)) {
			return this.spoonEquivalent / unit.spoonEquivalent;
		} else {
			// niet nodig want quantity moet nominaal
			throw new IllegalArgumentException("Cannot convert " + this + " to " + unit);
		}
	}

}
