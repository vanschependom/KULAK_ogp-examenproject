package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.*;

import java.util.List;

/**
 * A class representing an alchemic ingredient.
 *
 * @invar   The amount of an ingredient must always be valid.
 *          | isValidAmount(getAmount())
 * @invar   The unit of an ingredient must always be valid.
 *          | canHaveAsUnit(getUnit())
 * @invar	The temperature of an ingredient must always be valid.
 * 			| isValidTemperature(getTemperatureObject())
 * @invar   The type of an ingredient must always be valid.
 *          | isValidType(getType())
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class AlchemicIngredient {

    /**********************************************************
     * CONSTRUCTORS
     **********************************************************/

    /**
     * Initialize a new non-containerized alchemic ingredient with given amount, unit, temperature, type and state.
     *
     * @param   amount
     *          The amount of the new alchemic ingredient.
     * @param   unit
     *          The unit of the new alchemic ingredient.
     * @param   temperature
     *          The temperature of the new alchemic ingredient.
     * @param   type
     *          The type of the new alchemic ingredient.
     * @param   state
     *          The state of the new alchemic ingredient.
     *
     * @pre     The given amount must be a valid amount for an alchemic ingredient.
     *          | isValidAmount(amount)
     * @pre     The given unit must be a valid unit for an alchemic ingredient.
     *          | isValidUnit(unit)
     *
     * @post    The amount of this new alchemic ingredient is equal to the given amount.
     *          | new.getAmount() == amount
     * @post    The unit of this new alchemic ingredient is equal to the given unit.
     *          | new.getUnit() == unit
     * @post    If the given temperature is not a valid temperature, the temperature is set to the standard temperature of the ingredient type.
     *          | if (!isValidTemperature(temperature))
     *          | then new.getTemperature()[0] == 0
     *          |      && new.getTemperature()[1] == 20
     * @post    If the given temperature is a valid temperature, the temperature is set to
     *          the given temperature.
     *          | if (isValidTemperature(temperature))
     *          | then new.getTemperatureObject() == temperature
     * @post    If the given state is a valid state, the state is set to the given state.
     *          | if (isValidState(state))
     *          | then new.getState() == state
     *
     * @throws  IllegalStateException
     *          The given state is not valid.
     *          | !isValidState(state)
     */
    @Raw
    public AlchemicIngredient(double amount, Unit unit, Temperature temperature, IngredientType type, State state) throws IllegalStateException {
        if (!isValidState(state)) {
            throw new IllegalStateException("The given state is not valid!");
        }
        if (!isValidType(type)) {
            // default type (because this is implemented totally)
            this.type = IngredientType.DEFAULT;
        } else {
            this.type = type;
        }
        if (!isValidTemperature(temperature)) {
            // default temperature (because this is implemented totally)
            this.temperature = new Temperature();
        } else {
            this.temperature = temperature;
        }
        this.amount = amount;   // must be valid (@pre of nominal implementation)
        this.unit = unit;       // must be valid (@pre of nominal implementation)
        this.state = state;     // must be valid (because no exception was thrown)
    }



    /**********************************************************
     * AMOUNT and UNIT - NOMINAL PROGRAMMING
     *
     * @note    de unit en amount moeten altijd legal zijn
     *          wanneer een methode/constructor wordt aangeroepen
     **********************************************************/

    private final double amount;

    /**
     * A method to get the amount of this alchemic ingredient.
     */
    @Basic @Immutable
    public double getAmount() {
        return amount;
    }

    /**
     * A method to get the amount of this alchemic ingredient in spoons.
     * @return  The amount of this alchemic ingredient in spoons.
     *          | result == amount * getUnit().getSpoonEquivalent()
     */
    @Immutable
    public double getSpoonAmount() {
    	return amount * getUnit().getSpoonEquivalent();
    }

    /**
     * A method to check whether the given amount is a valid amount for an alchemic ingredient.
     * @param 	amount
     * 			The amount to check
     * @return	True if and only if the amount is positive.
     * 			| result == (amount >= 0)
     */
    public boolean isValidAmount(double amount) {
        return amount >= 0;
    }

    private final Unit unit;

    /**
     * A method to get the unit of this alchemic ingredient.
     */
    @Basic @Immutable
    public Unit getUnit() {
        return unit;
    }

    /**
     * A method to check whether the given unit is a valid unit for an alchemic ingredient.
     * @param 	unit
     * 			The unit to check
     * @return	True if and only if the unit is effective, and the unit
     *          is a legal unit for the state of the ingredient.
     * 			| result == ( (unit != null)
     * 		    |   && List.of(unit.getAllowedStates()).contains(getState()) )
     */
    public boolean canHaveAsUnit(Unit unit) {
        return (unit != null) &&
                List.of(unit.getAllowedStates()).contains(getState());
    }



    /**********************************************************
     * TEMPERATURE - TOTAL
     **********************************************************/

    /**
     * A variable referencing the temperature of the ingredient.
     */
    private final Temperature temperature;

    /**
     * A getter for the standard temperature object of the ingredient type.
     */
    @Model
    private Temperature getTemperatureObject() {
        return temperature;
    }

    /**
     * A method for checking whether the given temperature is a valid temperature for an ingredient.
     *
     * @return 	True if and only if the temperature is effective.
     * 			| result == (temperature != null)
     */
    @Raw
    public boolean isValidTemperature(Temperature temperature) {
        return temperature != null;
    }

    /**
     * A method for getting the coldness of the ingredient.
     *
     * @return  The valid coldness of the ingredient is returned.
     *          | result == getTemperatureObject().getColdness()
     *          | && Temperature.isValidColdness(result)
     */
    public long getHotness() {
    	return getTemperatureObject().getHotness();
    }

    /**
     * A method for getting the hotness of the ingredient.
     *
     * @return  The valid hotness of the ingredient is returned.
     *          | result == getTemperatureObject().getHotness()
     *          | && Temperature.isValidHotness(result)
     */
    public long getColdness() {
        return getTemperatureObject().getColdness();
    }

    /**
     * A getter for the temperature of the ingredient.
     */
    @Basic
    public long[] getTemperature() {
        return getTemperatureObject().getTemperature();
    }

    /**
     * A method to heat the ingredient.
     *
     * @param 	amount
     * 			The amount of heat to be added.
     * @effect  The ingredient is heated if it is not terminated.
     *          | if (!isTerminated())
     *          | then getTemperatureObject().heat(amount)
     * @note    Temperatures are implemented totally, so we don't throw an exception
     *          if any illegal cases come up (e.g. terminated ingredient, negative amounts, etc.)
     */
    public void heat(long amount) {
        if (!isTerminated()) {
            getTemperatureObject().heat(amount);
        }
    }

    /**
     * A method to cool the ingredient.
     *
     * @param 	amount
     * 			The amount of cold to be added.
     * @effect  The ingredient is cooled if it is not terminated.
     *          | if (!isTerminated())
     *          | then getTemperatureObject().cool(amount)
     * @note    Same as for heating.
     */
    public void cool(long amount) {
        if (!isTerminated()) {
            getTemperatureObject().cool(amount);
        }
    }




    /**********************************************************
     * TYPE - TOTAL PROGRAMMING
     **********************************************************/

    private final IngredientType type;

    /**
     * A method to get the type of this alchemic ingredient.
     */
    @Basic @Immutable
    public IngredientType getType() {
        return type;
    }

    /**
     * A method to check whether the given type is a valid type for an alchemic ingredient.
     *
     * @param 	type
     * 			The type to check
     * @return	True if and only if the type is effective.
     * 			| result == (type != null)
     */
    @Raw
    public boolean isValidType(IngredientType type) {
        return type != null;
    }



    /**********************************************************
     * STATE
     **********************************************************/

    private final State state;

    /**
     * A method to get the state of this alchemic ingredient.
     */
    @Basic @Immutable
    public State getState() {
        return state;
    }

    /**
     * A method to check whether the given state is a valid state for an alchemic ingredient.
     *
     * @param 	state
     * 			The state to check
     * @return	True if and only if the state is effective.
     * 			| result == (state != null)
     */
    @Raw
    public boolean isValidState(State state) {
        return state != null;
    }



    /**********************************************************
     * CONTAINERIZED
     **********************************************************/

    private boolean isContainerized = false;

    /**
     * A method to check whether the ingredient is containerized.
     */
    @Basic
    public boolean isContainerized() {
        return isContainerized;
    }

    /**
     * A method to containerize the ingredient.
     *
     * @param   containerized
     *          The new containerized state for the ingredient.
     * @post    The containerized state of the ingredient is set to the given containerized state,
     *          if the ingredient is not terminated.
     *          | if (!isTerminated())
     *          | then new.isContainerized() == containerized
     * @throws  IllegalStateException
     *          The ingredient is terminated.
     */
    protected void setContainerized(boolean containerized) throws IllegalStateException {
        if (isTerminated()) {
            throw new IllegalStateException("The ingredient is terminated!");
        }
        isContainerized = containerized;
    }



    /**********************************************************
     * NAME
     **********************************************************/

    /**
     * A method for getting the simple name of the ingredient.
     *
     * @return  The simple name of the ingredient, which is the simple name of the
     *          name of the type of the ingredient.
     *          | result == getType().getName().getSimpleName()
     */
    public String getSimpleName() {
        return getType().getName().getSimpleName();
    }

    /**
     * A method for getting the special name of the ingredient.
     *
     * @return  The special name of the ingredient, which is the special name of the
     *          name of the type of the ingredient.
     *          | result == getType().getName().getSpecialName()
     */
    public String getSpecialName() {
        return getType().getName().getSpecialName();
    }

    /**
     * A method for getting the extended simple name of the ingredient (with heated or cooled).
     *
     * @return  If the ingredient is heated, the full name is "Heated" + the simple name.
     *          | if ( temperature.getHotness() > Temperature.getStandardHotness() )
     *          | then result == "Heated " + getSimpleName()
     * @return  If the ingredient is cooled, the full name is "Cooled" + the simple name.
     *          | if ( temperature.getColdness() > Temperature.getStandardColdness() )
     *          | then result == "Cooled " + getSimpleName()
     * @return  If the ingredient is neither heated nor cooled, the full name is the simple name.
     *          | if (temperature.getHotness() = Temperature.getStandardHotness() &&
     *          |   temperature.getColdness() = Temperature.getStandardColdness() )
     *          | then result == getSimpleName()
     */
    private String getExtendedSimpleName() {
        if ( temperature.getHotness() > Temperature.getStandardHotness() ) {
            return "Heated " + getSimpleName();
        } else if ( temperature.getColdness() > Temperature.getStandardColdness() ) {
            return "Cooled " + getSimpleName();
        } else {
            return getSimpleName();
        }
    }

    /**
     * A method to get the full name of an ingredient.
     *
     * @return  If the ingredient is mixed and has a special name then the full name is
     *          the special name followed by the extended simple name in brackets.
     *          | if (getType().isMixed() && getSpecialName() != null)
     *          | then result == getSpecialName() + " (" + getExtendedSimpleName() + ")"
     * @return  Otherwise the full name is just the extended simple name
     *          | if !(getType().isMixed() && getSpecialName() != null)
     *          | then result == getExtendedSimpleName()
     */
    public String getFullName() {
        if (getType().isMixed() && getSpecialName() != null) {
            return getSpecialName() + " (" + getExtendedSimpleName() + ")";
        } else {
            return getExtendedSimpleName();
        }
    }



    /**********************************************************
     * DESTRUCTOR
     **********************************************************/

    /**
     * A variable for keeping track of whether the ingredient is terminated.
     */
    private boolean isTerminated = false;

    /**
     * A method to check whether the ingredient is terminated.
     */
    @Basic @Immutable
    public boolean isTerminated() {
        return isTerminated;
    }

    /**
     * A method to terminate the ingredient.
     *
     * @effect  The temperature of the ingredient is terminated.
     *          | getTemperatureObject().terminate()
     * @post    The ingredient is terminated.
     *          | new.isTerminated()
     */
    public void terminate() {
        getTemperatureObject().terminate();
        isTerminated = true;
    }


    // TODO methode die checkt of ingredients gelijk zijn
    //  alleen op vlak van type, state en temperature


}
