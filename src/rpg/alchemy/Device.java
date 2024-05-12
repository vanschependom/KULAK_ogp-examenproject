package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.*;
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
     * A constructor for a device with a given laboratory and a maximum number of ingredients.
     *
     * @param   laboratory
     *          The laboratory in which the device is stored.
     * @param   maxNbOfIngredients
     *          The maximum number of ingredients for this device.
     *
     * @post    The max number of ingredients is set to maxNbOfIngredients
     *          | new.getMaxNbOfIngredients() == maxNbOfIngredients
     * @effect  The device is added to the laboratory.
     *          | setLaboratory(laboratory)
     */
    @Raw
    public Device(Laboratory laboratory, int maxNbOfIngredients) throws IllegalArgumentException {
        super();
        setLaboratory(laboratory);
        this.maxNbOfIngredients = maxNbOfIngredients; // final so no setter
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
     *          | if ( result.getSpoonAmount() > Unit.getMaxUnitForContainerWithState(getIngredientAt(0).getState()).getSpoonEquivalent() )
     *          |   then result.equals(new AlchemicIngredient(1, Unit.getMaxUnitForContainerWithState(getIngredientAt(0)), new Temperature(getIngredientAt(0).getTemperature()),
     *          |                       getIngredientAt(0).getType(), getIngredientAt(0).getState()))
     * @return  If the spoon amount of the result is not more than the spoon equivalent of the maximum
     *          unit for a container with the state of the result, return a new container with the minimum unit
     *          for the result, given the state and size of the result, containing the result.
     *          | if ( result.getSpoonAmount() <= Unit.getMaxUnitForContainerWithState(result.getState()).getSpoonEquivalent() )
     *          |   then result.equals(new IngredientContainer(Unit.getMinUnitForContainerWithIngredient(getIngredientAt(0)), getIngredientAt(0))
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
            throw new IllegalStateException("Device is terminated!");
        }
        if (getNbOfIngredients() > 1) {
            throw new DeviceNotYetUsedException();
        }
        if (getNbOfIngredients() == 0) {
            return null;
        }
        AlchemicIngredient result = getIngredientAt(0);
        Unit maxUnit = Unit.getMaxUnitForContainerWithState(result.getState());      // get the maximum capacity for the state of the result
        if (result.getSpoonAmount() > maxUnit.getSpoonEquivalent()) {       // the result is more than this maximum capacity -> the excess goes to waste
            result = new AlchemicIngredient(1, maxUnit,
                    new Temperature(result.getTemperature()),               // create a new temperature with long[2] as argument
                    result.getType(), result.getState());
        }
        removeIngredientAt(0);          // remove the result
        return new IngredientContainer(Unit.getMinUnitForContainerWithIngredient(result), result);
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
     * A method to add an ingredient to a device.
     *
     * @param   ingredient
     *          The ingredient to add
     * @effect  The ingredient is added to the device using the superclass method (from StorageLocation)
     *          | super.addAsIngredient(ingredient)
     * @throws  IllegalStateException
     *          The maximum amount of ingredients has been reached
     *          | getNbOfIngredients() == getMaxNbOfIngredients()
     */
    @Override
    protected void addAsIngredient(AlchemicIngredient ingredient) throws IllegalArgumentException, IllegalStateException {
        if (getNbOfIngredients() == getMaxNbOfIngredients()) {
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
     *          worked out in Device.
     */
    private Laboratory laboratory;

    /**
     * A method that moves a device to a different laboratory
     *
     * @param   laboratory
     *          The laboratory to move the device to.
     *
     * @effect  The laboratory of this device is set to the given laboratory.
     *          | setLaboratory(laboratory)
     *
     * @throws  IllegalStateException
     *          The device is terminated.
     *          | isTerminated()
     * @throws  IllegalArgumentException
     *          The device is already present in the given laboratory.
     *          | laboratory == getLaboratory()
     */
    public void move(Laboratory laboratory) throws IllegalStateException, IllegalArgumentException {
        if (isTerminated()) {
            throw new IllegalStateException("Device is terminated");
        }
        if (laboratory == getLaboratory()) {
            throw new IllegalArgumentException("The device is already present in this lab!");
        }
        setLaboratory(laboratory);
    }

    /**
     * A method for setting the laboratory of a device to a given laboratory
     *
     * @param   laboratory
     *          The laboratory to put the device into
     *
     * @post    The laboratory of this device is set to the given laboratory
     *          | new.getLaboratory() == laboratory
     * @effect  The device is removed from its old laboratory
     *          | getLaboratory().removeAsDevice(this)
     * @effect  The device is added to the new laboratory
     *          | laboratory.addAsDevice(this)
     *
     * @throws  IllegalArgumentException
     *          The device can not have the given laboratory as its laboratory.
     *          | !canHaveAsLaboratory(laboratory)
     * @throws  IllegalArgumentException
     *          The laboratory is effective, but cannot have this device as one of its devices.
     *          | laboratory != null && !laboratory.canHaveAsDevice(this)
     */
    @Raw @Model
    protected void setLaboratory(Laboratory laboratory) throws IllegalStateException, IllegalArgumentException {
        if (!canHaveAsLaboratory(laboratory)) {
            throw new IllegalArgumentException("The given laboratory is not allowed for this device.");
        }
        // prevent method invocation from other places than move and the constructor
        if (laboratory != null && !laboratory.canHaveAsDevice(this)) {
            throw new IllegalArgumentException("This device is not allowed for the given laboratory.");
        }

        // remember old lab
        Laboratory oldLaboratory = getLaboratory();

        // set up new link
        this.laboratory = laboratory;

        // remove this device out of old lab,
        // old lab should always be effective but we don't want to accidentally throw exceptions
        if (oldLaboratory != null) {
            oldLaboratory.removeAsDevice(this);
        }

        // add device to new lab
        if (laboratory != null) {laboratory.addAsDevice(this);}
    }

    /**
     * A method for getting the laboratory of the device.
     */
    @Basic @Raw
    public Laboratory getLaboratory() {
        return laboratory;
    }

    /**
     * A method for checking if a laboratory is valid for a device.
     *
     * @param   laboratory
     *          The laboratory to check for this device
     *
     * @return  True if the device is terminated and the laboratory is null
     *          or if the device is not terminated and the laboratory is not null.
     *          | result == ( (isTerminated() && laboratory == null) ||
     *          |   (!isTerminated() && laboratory != null) )
     */
    @Raw
    public boolean canHaveAsLaboratory(Laboratory laboratory) {
        if (isTerminated()) {
            return (laboratory == null);
        } else {
            return laboratory != null;
        }
    }

    /**
     * A method for checking if the device has a proper laboratory.
     *
     * @return  True if and only if the laboratory is a valid laboratory for this
     *          device and the laboratory has this device as a device in the list of devices.
     *          | result == (canHaveAsLaboratory(getLaboratory())
     *          |   && getLaboratory().hasAsDevice(this))
     */
    @Raw
    public boolean hasProperLaboratory() {
        return (canHaveAsLaboratory(getLaboratory())
                && getLaboratory().hasAsDevice(this));
    }



    /**********************************************************
     * OPERATION EXECUTION
     **********************************************************/

    /**
     * An abstract method for executing the device instructions.
     *
     * @throws  IllegalStateException
     *          The device is terminated.
     *          | isTerminated()
     * @throws  IllegalStateException
     *          The device is empty.
     *          | isEmpty()
     */
    public void executeOperation() throws IllegalStateException {
        if (isTerminated()) {
            throw new IllegalStateException("The device can't be used, since the laboratory is not valid!");
        }
        if (isEmpty()) {
            throw new IllegalStateException("There are no items in the device!");
        }
        // subclasses should override this method
    }



    /**********************************************************
     * DESTRUCTION
     **********************************************************/

    // Inherited from StorageLocation

    /**
     * A method for terminating a device.
     *
     * @effect  The device (storage location) is terminated using its superclass.
     *          | super.terminate()
     * @effect  The laboratory of the device is set to null.
     *          | setLaboratory(null)
     *
     */
    @Override
    public void terminate() throws IllegalStateException {
        super.terminate();
        setLaboratory(null);    // the order in which these are done is important!
    }

}
