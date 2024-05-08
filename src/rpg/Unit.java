package rpg;
import be.kuleuven.cs.som.annotate.*;
import rpg.alchemy.AlchemicIngredient;

/**
 * An enumeration of units.
 *
 * @note 	We use NOMINAL PROGRAMMING for this class.
 *
 * @author 	Vincent Van Schependom
 * @author	Arne Claerhout
 * @author 	Flor De Meulemeester
 *
 * @version 1.0
 */
public enum Unit {

	/**********************************************************
	 * POSSIBLE UNITS
	 **********************************************************/

	PINCH(1.0/6, new State[]{State.POWDER}, false),
	DROP(1.0/8, new State[]{State.LIQUID}, false),
	SPOON(1, new State[]{State.LIQUID, State.POWDER}, true),
	VIAL(5, new State[]{State.LIQUID}, true),
	SACHET(7, new State[]{State.POWDER}, true),
	BOTTLE(15, new State[]{State.LIQUID}, true),
	BOX(42, new State[]{State.POWDER}, true),
	JUG(105, new State[]{State.LIQUID}, true),
	SACK(126, new State[]{State.POWDER}, true),
	BARREL(1260, new State[]{State.LIQUID}, true),
	CHEST(1260, new State[]{State.POWDER}, true),
	STOREROOM(6300, new State[]{State.LIQUID, State.POWDER}, false);



	/**********************************************************
	 * PROPERTIES
	 **********************************************************/

	/**
	 * Variable with the spoon equivalent value of that unit.
	 */
	private final double spoonEquivalent;

	/**
	 * A list of states containing the states which the unit can be used for.
	 */
	private final State[] allowedStates;

	/**
	 * A boolean to check if the unit is allowed to be the unit for a container.
	 */
	private final boolean allowedForContainer;



	/**********************************************************
	 * CONSTRUCTOR
	 **********************************************************/

	/**
	 * Initialize a new unit with a given spoon equivalent and a list of allowed states.
	 *
	 * @param 	spoonEquivalent
	 *			The spoon equivalent value of the new unit.
	 * @param 	allowedStates
	 *			The list of allowed states which the new unit can be used for.
	 * @param 	allowedForContainer
	 * 			A boolean to check if the unit is allowed to be the unit for a container.
	 *
	 * @pre		The given spoon equivalent must be a positive number.
	 * 			| spoonEquivalent > 0
	 * @pre		The given list of allowed states must be effective.
	 * 			| allowedStates != null &&
	 * 			| for each state in allowedStates:
	 * 			|	state != null
	 *
	 * @post	The spoon equivalent of the new unit is set to the given spoon equivalent.
	 * 			| new.getSpoonEquivalent().equals(spoonEquivalent)
	 * @post	The list of allowed states of the new unit is set to the given list of allowed states.
	 * 			| new.getAllowedStates().equals(allowedStates)
	 * @post	The new unit is allowed to be the unit for a container if
	 * 			and only if the boolean allowedForContainer is true.
	 * 			| new.isAllowedForContainer() == allowedForContainer
	 */
	@Model
	private Unit(double spoonEquivalent, State[] allowedStates, boolean allowedForContainer) {
		this.spoonEquivalent = spoonEquivalent;
		this.allowedStates = allowedStates;
		this.allowedForContainer = allowedForContainer;
	}



	/**********************************************************
	 * METHODS
	 **********************************************************/

	/**
	 * Return the spoon equivalent of this unit.
	 */
	@Basic @Immutable
	public double getSpoonEquivalent() {
		return spoonEquivalent;
	}

	public double getStoreroomEquivalent() {
		return getConversionFor(STOREROOM);
	}

	/**
	 * Return a copy of the (fixed length) list of allowed states of this unit.
	 */
	@Basic @Immutable
	public State[] getAllowedStates() {
		return allowedStates.clone();
	}

	/**
	 * Return the private list of allowed states of this unit.
	 */
	@Model
	private State[] getPrivateAllowedStates() {
		return allowedStates;
	}

	/**
	 * Check whether this unit has the given state as an allowed state.
	 *
	 * @param 	state
	 * 			The state to check.
	 * @return	True if and only if the given state is in the list of allowed states of this unit.
	 * 			| result == ( for some allowedState in getAllowedStates():
	 * 			|	allowedState == state )
	 */
	public boolean hasAsAllowedState(State state) {
		for (State allowedState : getPrivateAllowedStates()) {
			if (state == allowedState) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether this unit can be converted to the given unit.
	 *
	 * @param 	unit
	 * 			The unit to convert to.
	 * @return	False if the given unit is not effective.
	 * 			| if (unit == null)
	 * 			|	then result == false
	 * @return	True if and only if the given effective unit has an allowed state which is also an allowed
	 *			state of this unit.
	 * 			| if ( (unit != null)
	 * 			|	then result == (for some allowedState in getPrivateAllowedStates():
	 * 			|			unit.hasAsAllowedState(allowedState))
	 */
	public boolean conversionAllowed(Unit unit) {
		if (unit == null) {
			return false;
		}
		for (State allowedState : getPrivateAllowedStates()) {
			if (unit.hasAsAllowedState(allowedState)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * A method to convert the spoon equivalent value to a different unit.
	 *
	 * @param 	unit
	 * 			The unit you want to convert to.
	 *
	 * @pre		The conversion must be allowed.
	 * 			| conversionAllowed(unit)
	 *
	 * @return 	The conversion factor between this unit and the given unit.
	 * 			| result == this.getSpoonEquivalent() / unit.getSpoonEquivalent()
	 */
	public double getConversionFor(Unit unit) {
		return this.getSpoonEquivalent() / unit.getSpoonEquivalent();
	}

	/**
	 * Return whether this unit is allowed to be the unit for a container.
	 */
	@Basic
	public boolean isAllowedForContainer() {
		return allowedForContainer;
	}

	/**
	 * A method for getting the maximum unit for a container.
	 *
	 * @param 	state
	 * 			The state of the container.
	 *
	 * @pre 	The given state must be effective.
	 * 			| state != null
	 *
	 * @return	The maximum unit for a container.
	 * 			TODO
	 */
	public static Unit getMaxUnitForContainer(State state) {
		Unit maxUnit = null;
		for (Unit unit : Unit.values()) {
			if (unit.isAllowedForContainer() && unit.hasAsAllowedState(state)) {
				if (maxUnit == null || unit.getSpoonEquivalent() > maxUnit.getSpoonEquivalent()) {
					maxUnit = unit;
				}
			}
		}
		return maxUnit;
	}

	/**
	 * A method for getting the minimum unit for a result container.
	 *
	 * @param 	ingr
	 * 			The alchemic ingredient that needs to be in a container.
	 *
	 * @pre 	The given ingredient must be effective
	 * 			| ingr != null
	 *
	 * @return	The minimum unit for a container for the result.
	 * 			TODO
	 */
	public static Unit getMinUnitForContainerWith(AlchemicIngredient ingr) {
		Unit minUnit = getMaxUnitForContainer(ingr.getState());
		for (Unit u : Unit.values()) {
			if (u.isAllowedForContainer()
					&& u.hasAsAllowedState(ingr.getState())
					&& u.getSpoonEquivalent() < minUnit.getSpoonEquivalent()
					&& u.getSpoonEquivalent() >= ingr.getSpoonAmount() ) {
				minUnit = u;
			}
		}
		if (minUnit.getSpoonEquivalent() < ingr.getSpoonAmount()) {
			throw new IllegalArgumentException("The given result is too large for any container!");
		}
		return minUnit;
	}

}
