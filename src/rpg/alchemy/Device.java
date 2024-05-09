package rpg.alchemy;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.State;
import rpg.Unit;
import rpg.exceptions.DeviceNotYetUsedException;

/**
 * A class representing a device inside a laboratory.
 *
 * @invar   The device must always have a valid laboratory.
 *          | hasProperLaboratory()
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public abstract class Device extends StorageLocation {

    /**********************************************************
     * CONSTRUCTORS
     **********************************************************/

    /**
     * A constructor for a device with a given laboratory.
     *
     * @param   maxNbOfIngredients
     *          The maximum number of ingredients for this device.
     *
     * @post    The device is added to the laboratory
     *          TODO
     * @post    The max number of ingredients is set to maxNbOfIngredients
     *          | new.getMaxNbOfIngredients() == maxNbOfIngredients
     */
    @Raw
    public Device(int maxNbOfIngredients) throws IllegalArgumentException{
        super();
        this.maxNbOfIngredients = maxNbOfIngredients;
        // TODO
    }



    /**********************************************************
     * RESULT
     **********************************************************/

    /**
     * Return the result of the device in an ingredient container
     * with a maximum of Unit.getMaximumUnitForContainer(getState())
     * Return null if there are no ingredients in the device.
     *
     * @return  If there are no ingredients in the device, return null
     *          | if getNbOfIngredients() == 0
     *          |   then result == null
     * @return  If the spoon amount of the result is more than the spoon equivalent of the maximum
     *          unit for a container with the state of the result, the excess goes to waste.
     *          | if ( result.getSpoonAmount() > Unit.getMaxUnitForContainer(getIngredientAt(0).getState()).getSpoonEquivalent() )
     *          |   then result.equals(new AlchemicIngredient(1, Unit.getMaxUnitForContainer(getIngredientAt(0)), getIngredientAt(0).getTemperature(),
     *          |                       getIngredientAt(0).getType(), getIngredientAt(0).getState()))
     * @return  If the spoon amount of the result is not more than the spoon equivalent of the maximum
     *          unit for a container with the state of the result, return a new container with the minimum unit
     *          for the result, given the state and size of the result, containing the result.
     *          | if ( result.getSpoonAmount() <= Unit.getMaxUnitForContainer(result.getState()).getSpoonEquivalent() )
     *          |   then result.equals(new IngredientContainer(Unit.getMinUnitForContainerWith(getIngredientAt(0)), getIngredientAt(0))
     *
     * @effect  The result is removed from the device.
     *          | removeIngredientAt(0)
     * @effect  The result is set to containerized.
     *          | result.setContainerized(true)
     *
     * @throws  DeviceNotYetUsedException
     *          The device has not been used yet so there is no result to be given
     *          | getNbOfIngredients() > 1
     * @throws  IllegalStateException
     *          The device is terminated.
     *          | isTerminated()
     */
    public IngredientContainer getResult() throws DeviceNotYetUsedException, IllegalStateException {
        if (isTerminated()) {
            throw new IllegalStateException("Device is terminated");
        }
        if (getNbOfIngredients() > 1) {
            throw new DeviceNotYetUsedException();
        }
        if (getNbOfIngredients() == 0) {
            return null;
        }
        AlchemicIngredient result = getIngredientAt(0);
        // get the maximum capacity for the state of the result
        Unit maxUnit = Unit.getMaxUnitForContainer(result.getState());
        // the result is more than this maximum capacity -> the excess goes to waste
        if (result.getSpoonAmount() > maxUnit.getSpoonEquivalent()) {
            result = new AlchemicIngredient(1, maxUnit,
                    new Temperature(result.getColdness(), result.getHotness()),
                    result.getType(), result.getState());
        }
        // remove the result
        removeIngredientAt(0);
        // set containerized for the resulting ingredient
        result.setContainerized(true);
        // return a new container with the minimum unit for the result, given the state and size of the result
        return new IngredientContainer(Unit.getMinUnitForContainerWith(result), result);
    }

    /**********************************************************
     * INGREDIENTS
     **********************************************************/

    /**
     * A variable that keeps track of the maximum amount of ingredients a device can have.
     */
    private final int maxNbOfIngredients;

    /**
     * Return the maximum number of ingredients for the device.
     */
    @Basic @Immutable
    public int getMaxNbOfIngredients() {
        return maxNbOfIngredients;
    }

    /**
     * A method to add an ingredient to a temperature device.
     *
     * @param   ingredient
     *          The ingredient to add
     *
     * @throws  IllegalStateException
     *          The maximum amount of ingredients has been reached
     *          | getNbOfIngredients() == getMaxNbOfIngredients()
     */
    @Override
    protected void addAsIngredient(AlchemicIngredient ingredient) throws IllegalArgumentException, IllegalStateException {
        if(getNbOfIngredients() == getMaxNbOfIngredients()) {
            throw new IllegalStateException();
        }
        super.addAsIngredient(ingredient);
    }



    /**********************************************************
     * LABORATORY
     **********************************************************/

    /**
     * A variable containing the laboratory in which this device is stored.
     *
     * @note    This is done bi-directionally, all the main methods are
     *          worked out in Laboratory
     */
    private Laboratory laboratory;

    /**
     * A method for getting the laboratory of the device.
     */
    @Basic
    public Laboratory getLaboratory() {
        return laboratory;
    }

    /**
     * A method for checking if a laboratory is valid for a device.
     *
     * @note    Hier wordt enkel de INHOUD gecheckt (zie ook hasProperLaboratory())
     *              --> als dit zou afhangen van de object properties, moet hier
     *                  canHaveAsLaboratory() staan!
     *              --> Weten we nu nog niet want nog niet uitgewerkt!
     *
     * @param   laboratory
     *          The laboratory to check for this device
     *
     * @return  TODO
     */
    @Raw
    public boolean isValidLaboratory(Laboratory laboratory) {
        // TODO: werk uit en pas eventueel aan naar canHaveAsLaboratory()
        // voorlopig enkel nog maar null check, kweet niet of hier nog iets bij moet?
        return laboratory != null;
    }

    /**
     * A method for checking if the device has a proper laboratory.
     *
     * @note    Hier wordt
     *              1. de consistentie van de relatie gecheckt (bidirectioneel)
     *              2. de inhoud gecheckt (isValidLaboratory(getLaboratory()))
     *
     * @return  False if the laboratory is not valid laboratory.
     *          | if !isValidLaboratory(getLaboratory())
     *          | then result == false
     * @return  False if the laboratory does not have this device as a device.
     *          | if !getLaboratory().hasAsDevice(this)
     *          | then result == false
     *
     * TODO:    close specification!
     */
    public boolean hasProperLaboratory() {
        if (!isValidLaboratory(getLaboratory())) {
            return false;
        }
//        if (!getLaboratory().hasAsDevice(this)) {
//            return false;
//        }
        return true;
    }



    /**********************************************************
     * OPERATION EXECUTION
     **********************************************************/

    /**
     * An abstract method for executing the device instructions.
     *
     * @throws  IllegalStateException
     *          The device is not present in a valid laboratory.
     *          | !hasProperLaboratory()
     */
    public void executeOperation() throws IllegalStateException {
        if (!hasProperLaboratory()) { // TODO Dit is een klasseinvariant en is dus altijd geldig, moet dit dan nog gecheckt worden
            throw new IllegalStateException("The device can't be used, since the laboratory is not valid!");
        }
        if (isEmpty()) {
            throw new IllegalStateException("There are no items in the device!");
        }
    }



    /**********************************************************
     * DESTRUCTION
     **********************************************************/

    private boolean isTerminated = false;

    /**
     * Return whether the device is terminated.
     */
    public boolean isTerminated() {
        return isTerminated;
    }

    /**
     * A method for terminating a device.
     *
     * @post    If the device is not already terminated
     *          the device becomes terminated
     *          | if !isTerminated()
     *          | then new.isTerminated() == true
     *
     * @throws  IllegalStateException
     *          The device is already terminated
     *          | isTerminated()
     */
    protected void terminate() throws IllegalStateException {
        if (isTerminated()) {
            throw new IllegalStateException("Device is already terminated!");
        }
        isTerminated = true;
    }

}
