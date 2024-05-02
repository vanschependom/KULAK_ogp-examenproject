package rpg.ingredient;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import rpg.*;

/**
 * A class representing an alchemic ingredient.
 *
 * @invar   The amount of an ingredient must always be valid.
 *          | isValidAmount(getAmount())
 * @invar   The unit of an ingredient must always be valid.
 *          | isValidUnit(getUnit())
 * @invar	The temperature of an ingredient must always be valid.
 * 			| Temperature.isValidTemperature(getTemperatureObject.getColdness(), getTemperatureObject.getHotness())
 * @invar   The type of an ingredient must always be valid.
 *          | isValidType(getType())
 * @invar   The state of an ingredient must always be valid.
 *          | isValidState(getState())
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

    // ...



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
    public double getAmount() {
        return amount;
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
    public Unit getUnit() {
        return unit;
    }

    /**
     * A method to check whether the given unit is a valid unit for an alchemic ingredient.
     * @param 	unit
     * 			The unit to check
     * @return	True if and only if the unit is effective.
     * 			| result == (unit != null)
     */
    public boolean isValidUnit(Unit unit) {
        return unit != null;
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
     * @effect  The ingredient is heated if it is not terminated.
     *          | if (!isTerminated())
     *          | then getTemperatureObject().heat()
     * @note    Temperatures are implemented totally, so we don't throw an exception.
     */
    public void heat() {
        if (!isTerminated()) {
            getTemperatureObject().heat();
        }
    }

    /**
     * A method to cool the ingredient.
     *
     * @effect  The ingredient is cooled if it is not terminated.
     *          | if (!isTerminated())
     *          | then getTemperatureObject().cool()
     * @note    Temperatures are implemented totally, so we don't throw an exception.
     */
    public void cool() {
        if (!isTerminated()) {
            getTemperatureObject().cool();
        }
    }




    /**********************************************************
     * TYPE - TOTAL PROGRAMMING
     *
     * @note    in de constructor moet het type geinitialiseerd
     *          worden op water als het illegal is
     **********************************************************/

    private final IngredientType type;

    /**
     * A method to get the type of this alchemic ingredient.
     */
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
    public boolean isValidType(IngredientType type) {
        return type != null;
    }



    /**********************************************************
     * STATE
     **********************************************************/

    private State state = null;

    /**
     * A method to get the state of this alchemic ingredient.
     */
    public State getState() {
        return state;
    }

    /**
     * A method for changing the state of an ingredient.
     *
     * @param 	state
     *          The new state for the ingredient.
     * @post    If the given state is a legal state and the ingredient is not terminated,
     *          the state of the ingredient is set to the given state.
     *          | if (isValidState(state) && !isTerminated()
     *          |   then new.getState() == state
     * @throws  IllegalStateException
     *          The ingredient is terminated.
     *          | isTerminated()
     * @throws  IllegalStateException
     *          The given state is not a legal state.
     *          | !isValidState(state)
     *
     * TODO: zorg ervoor dat enkel de Transmogrifier de state kan veranderen
     */
    private void setState(State state) throws IllegalStateException {
        if (isTerminated()) {
            throw new IllegalStateException("The ingredient is terminated!");
        }
        if (!isValidState(state)) {
            throw new IllegalStateException("The given state is not valid!");
        }
        this.state = state;
    }

    /**
     * A method to check whether the given state is a valid state for an alchemic ingredient.
     *
     * @param 	state
     * 			The state to check
     * @return	True if and only if the state is effective.
     * 			| result == (state != null)
     */
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
     * DESTRUCTOR
     **********************************************************/

    /**
     * A variable for keeping track of whether the ingredient is terminated.
     */
    private boolean isTerminated = false;

    /**
     * A method to check whether the ingredient is terminated.
     */
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

}
