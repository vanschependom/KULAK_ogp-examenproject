package rpg.alchemy;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.State;
import rpg.Unit;
import rpg.exceptions.DeviceNotYetUsedException;

/**
 * A class representing a device inside a laboratory.
 *
 * @invar   The device must always have a valid laboratory if it is not terminated.
 *          | if !isTerminated()
 *          | then isValidLaboratory(getLaboratory())
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public abstract class Device extends StorageLocation{

    /**********************************************************
     * CONSTRUCTORS
     **********************************************************/

    /**
     * A constructor for a device with a given laboratory.
     *
     * @param   laboratory
     *          The laboratory to add the device in.
     *
     * @post    The device is added to the laboratory
     *          TODO
     */
    @Raw
    public Device(Laboratory laboratory) throws IllegalArgumentException{
        if (!isValidLaboratory(laboratory)) throw new IllegalArgumentException();
        //TODO
    }



    /**********************************************************
     * RESULT
     **********************************************************/

    /**
     * Return the result of the device in an ingredient container
     * with a maximum of Unit.getMaximumUnitForContainer(getState())
     * Return null if there are no ingredients in the device
     *
     * @throws  DeviceNotYetUsedException
     *          The device has not been used yet so there is no result to be given
     *          | getNbOfIngredients() > 1
     * @throws  IllegalStateException
     *          The device is terminated.
     *          | isTerminated()
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
     *          |   then result.equals(new IngredientContainer(Unit.getMinUnitForContainer(getIngredientAt(0)), getIngredientAt(0))
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
        // return a new container with the minimum unit for the result, given the state and size of the result
        return new IngredientContainer(Unit.getMinUnitForContainer(result), result);
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
    public Laboratory getLaboratory() throws IllegalStateException {
        return laboratory;
    }

    /**
     * A method for checking if a laboratory is valid for a device.
     *
     * @param   laboratory
     *          The laboratory to check for this device
     * @return  TODO
     */
    @Raw
    public boolean isValidLaboratory(Laboratory laboratory) {
        // return laboratory != null && laboratory.isValidDevice(this); TODO
        return false;
    }



    /**********************************************************
     * OPERATION EXECUTION
     **********************************************************/

    /**
     * An abstract method for executing the device instructions.
     */
    public abstract void executeOperation();



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
