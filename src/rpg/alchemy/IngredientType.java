package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.exceptions.IllegalNameException;
import rpg.*;

/**
 * A class representing an alchemic ingredient type.
 *
 * @invar	The name of an ingredient type must always be valid.
 * 			| isValidName(getNameObject())
 * @invar 	The state of an ingredient type must always be valid.
 * 			| isValidState(getStandardState())
 * @invar	The standard temperature of an ingredient type must always be valid.
 * 			| isValidTemperature(getStandardTemperatureObject())
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class IngredientType {

	/**
	 * A variable referencing the default ingredient type, water.
	 */
	public static final IngredientType DEFAULT = new IngredientType(Name.getWater(), State.LIQUID, new Temperature(0, 20), false);

	/**********************************************************
	 * CONSTRUCTORS
	 **********************************************************/

	/**
	 * A constructor for creating a new ingredient type with given name,
	 * state, temperature and mixed state.
	 *
	 * @param 	name
	 * 			The name of the new ingredient type.
	 * @param 	standardState
	 * 			The state of the new ingredient type.
	 * @param 	standardTemperature
	 * 			The temperature of the new ingredient type.
	 * @param 	isMixed
	 * 			A boolean indicating whether the new ingredient type is mixed or not.
	 *
	 * @post	If the given state is valid, the state of the new ingredient type is set to the
	 * 			given state.
	 * 			| if (isValidState(state))
	 * 			| then new.getStandardState() == state
	 * @post    If the given temperature is not a valid standard temperature, the temperature is
	 * 			set to the standard temperature.
	 *          | if (!isValidStandardTemperature(temperature))
	 *          | then new.getStandardTemperature()[0] == 0
	 *          |      && new.getStandardTemperature()[1] == 20
	 * @post    If the given temperature is a valid standard temperature, the temperature
	 * 			standard temperature is set to the given temperature.
	 *          | if (isValidStandardTemperature(temperature))
	 *          | then new.getStandardTemperatureObject() == temperature
	 * @post	The mixed state of the new ingredient type is set to the given mixed state.
	 * 			| new.isMixed() == isMixed
	 *
	 * @effect 	The name of the new ingredient type is set to the given name.
	 * 			| setName(name)
	 *
	 * @throws	IllegalStateException
	 * 			The given state is not a valid state for an ingredient type.
	 * 			| !isValidState(state)
	 */
	@Raw
	public IngredientType(Name name, State standardState, Temperature standardTemperature, boolean isMixed)
			throws IllegalNameException, IllegalStateException {
		if (!isValidState(standardState)) {
			throw new IllegalStateException("Invalid state! State must be effective.");
		}
		if (!isValidStandardTemperature(standardTemperature)) {
			this.standardTemperature = new Temperature();
		} else {
			this.standardTemperature = standardTemperature;
		}
		this.isMixed = isMixed;					// always valid (boolean)
		setName(name);
		this.standardState = standardState;		// legal, exception is thrown if not
	}



	/**********************************************************
	 * MIXED
	 **********************************************************/

	/**
	 * A variable to keep track of whether the ingredient type is mixed or not.
	 */
	private final boolean isMixed;

	/**
	 * A getter for the mixed state of the ingredient type.
	 */
	@Basic @Immutable
	public boolean isMixed() {
		return isMixed;
	}

	/**********************************************************
	 * NAME - DEFENSIVE
	 **********************************************************/

	/**
	 * A variable referencing the name of the ingredient type.
	 */
	private Name name;

	/**
	 * A getter for the name of the ingredient type.
	 */
	@Basic
	public Name getName() {
		return name;
	}

	/**
	 * A setter for the name of the ingredient type.
	 *
	 * @param 	name
	 * 			The name to set.
	 * @post	If the given name is a valid name for an ingredient type, the name of the ingredient type
	 * 			is set to the given name.
	 * 			| if (canHaveAsName(name))
	 * 			| then new.getName() == name
	 * @throws	IllegalNameException
	 * 			The given name is not a valid name for an ingredient type.
	 * 			| !canHaveAsName(name)
	 */
	@Model @Raw
	private void setName(Name name) throws IllegalNameException {
		if (!canHaveAsName(name)) {
			throw new IllegalNameException(null);
		}
		this.name = name;
	}

	/**
	 * Check whether the given name is a valid name for an ingredient type.
	 *
	 * @param 	name
	 * 			The name to check.
	 * @return	True if and only if the name is effective and if the
	 * 			mixed state of the name is equal to the mixed state of the ingredient type.
	 * 			| result == (name != null && name.isMixed() == isMixed())
	 */
	@Raw
	public boolean canHaveAsName(Name name) {
		return (name == null && name.isMixed() == isMixed());
	}



	/**********************************************************
	 * STATE
	 **********************************************************/

	/**
	 * A variable referencing the standard state of the ingredient type.
	 */
	private final State standardState;

	/**
	 * A getter for the standard state of the ingredient type.
	 */
	@Basic @Immutable
	public State getStandardState() {
		return standardState;
	}

	/**
	 * Check whether the given state is a valid state for an ingredient type.
	 *
	 * @param 	state
	 * 			The state to check.
	 * @return	True if and only if the state is effective.
	 * 			| result == (state != null)
	 */
	@Raw
	public static boolean isValidState(State state) {
		return state != null;
	}



	/**********************************************************
	 * TEMPERATURE - TOTAL
	 **********************************************************/

	/**
	 * A variable referencing the standard temperature of the ingredient type.
	 */
	private final Temperature standardTemperature;

	/**
	 * A getter for the standard temperature of the ingredient type.
	 */
	@Basic @Immutable
	public long[] getStandardTemperature() {
		return getStandardTemperatureObject().getTemperature();
	}

	/**
	 * A getter for the standard temperature object of the ingredient type.
	 */
	@Model
	private Temperature getStandardTemperatureObject() {
		return standardTemperature;
	}

	/**
	 * A method for checking whether the given temperature is a valid standard temperature.
	 *
	 * @return 	True if and only if the temperature is effective.
	 * 			| result == (temperature != null)
	 */
	@Raw
	public static boolean isValidStandardTemperature(Temperature temperature) {
		return temperature != null;
	}


}
