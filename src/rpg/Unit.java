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
	private final boolean isAllowedForContainer;



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
	 * @param 	isAllowedForContainer
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
	 * 			| new.getSpoonEquivalent() == spoonEquivalent
	 * @post	The list of allowed states of the new unit is set to the given list of allowed states.
	 * 			| new.getAllowedStates() == allowedStates
	 * @post	The new unit is allowed to be the unit for a container if
	 * 			and only if the boolean isAllowedForContainer is true.
	 * 			| new.isAllowedForContainer() == isAllowedForContainer
	 */
	@Model
    Unit(double spoonEquivalent, State[] allowedStates, boolean isAllowedForContainer) {
		this.spoonEquivalent = spoonEquivalent;
		this.allowedStates = allowedStates;
		this.isAllowedForContainer = isAllowedForContainer;
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

	/**
	 * A method for returning the equivalent of this unit in storerooms.
	 * @return	The equivalent of this unit in storerooms.
	 * 			| result == getConversionFor(Unit.STOREROOM)
	 */
	@Immutable
	public double getStoreroomEquivalent() {
		return getConversionFor(Unit.STOREROOM);
	}

	/**
	 * A method that returns the allowed states.
	 *
	 * @return	A copy of the allowed states for this unit.
	 * 			| result == getAllowedStatesObject().clone()
	 *
	 * @note 	This is not Immutable because the clone method creates a new clone everytime.
	 */
	public State[] getAllowedStates() {
		return getAllowedStatesObject().clone();
	}

	/**
	 * Return the private list of allowed states of this unit.
	 */
	@Model @Basic @Immutable
	private State[] getAllowedStatesObject() {
		return allowedStates;
	}

	/**
	 * Check whether this unit has the given state as an allowed state.
	 *
	 * @param 	state
	 * 			The state to check.
	 * @return	True if and only if the given state is in the list of allowed states of this unit.
	 * 			| result == ( for some allowedState in getAllowedStates():
	 * 			|				allowedState == state )
	 */
	public boolean hasAsAllowedState(State state) {
		for (State allowedState : getAllowedStatesObject()) {
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
	 * 			|	then result == (for some allowedState in getAllowedStates():
	 * 			|			unit.hasAsAllowedState(allowedState))
	 */
	public boolean conversionAllowed(Unit unit) {
		if (unit == null) {
			return false;
		}
		for (State allowedState : getAllowedStatesObject()) {
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
	 * @pre 	The given unit must be effective.
	 * 			| unit != null
	 * @pre		The conversion must be allowed.
	 * 			| conversionAllowed(unit)
	 * @return 	The conversion factor between this unit and the given unit.
	 * 			| result == this.getSpoonEquivalent() / unit.getSpoonEquivalent()
	 */
	public double getConversionFor(Unit unit) {
		return this.getSpoonEquivalent() / unit.getSpoonEquivalent();
	}

	/**
	 * Return whether this unit is allowed to be the unit for a container.
	 */
	@Basic @Immutable
	public boolean isAllowedForContainer() {
		return isAllowedForContainer;
	}

	/**
	 * A method for getting the maximum unit a container with contents of the given state can have.
	 *
	 * @param 	state
	 * 			The state of the container.
	 * @pre 	The given state must be effective.
	 * 			| state != null
	 * @return	The maximum unit a container with contents of the given state can have.
	 * 			| for each unit in Unit.values():
	 * 			|	if (unit.isAllowedForContainer()
	 * 			|		&& unit.hasAsAllowedState(state))
	 * 			|			then unit.getSpoonEquivalent() < result.getSpoonEquivalent()
	 */
	public static Unit getMaxUnitForContainerWithState(State state) {
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
	 * A method for getting the minimum unit for a container to be able
	 * to have the given ingredient as its content.
	 *
	 * @param 	ingredient
	 * 			The alchemic ingredient that needs to be in a container.
	 * @pre 	The given ingredient must be effective.
	 * 			| ingredient != null
	 * @pre 	The spoonAmount of the given ingredient must be less than or equal to the spoon equivalent
	 * 			of the maximum unit for a container.
	 * 			| ingredient.getSpoonAmount() <= getMaxUnitForContainerWithState(ingredient.getState()).getSpoonEquivalent()
	 * @return	The minimum unit for a container for the result.
	 * 			| for each unit in Unit.values():
	 * 			|	if (unit.isAllowedForContainer()
	 * 			|		&& unit.hasAsAllowedState(ingredient.getState())
	 * 			| 			then unit.getSpoonEquivalent() > result.getSpoonEquivalent()
	 */
	public static Unit getMinUnitForContainerWithIngredient(AlchemicIngredient ingredient) {
		Unit minUnit = getMaxUnitForContainerWithState(ingredient.getState());
		for (Unit u : Unit.values()) {
			if (u.isAllowedForContainer()
					&& u.hasAsAllowedState(ingredient.getState())
					&& u.getSpoonEquivalent() < minUnit.getSpoonEquivalent()
					&& u.getSpoonEquivalent() >= ingredient.getSpoonAmount() ) {
				minUnit = u;
			}
		}
		return minUnit;
	}

}
