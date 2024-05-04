package rpg.storage;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.State;
import rpg.Unit;
import rpg.exceptions.DeviceNotYetUsedException;
import rpg.exceptions.TerminatedObjectException;
import rpg.ingredient.AlchemicIngredient;
import rpg.ingredient.IngredientContainer;
import rpg.ingredient.IngredientType;
import rpg.ingredient.Temperature;

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

    /**
     * A variable containing the laboratory in which this device is stored.
     *
     * @note    This is done bi-directionally, all the main methods are
     *          worked out in Laboratory
     */
    private Laboratory laboratory;

    private boolean isTerminated = false;

    /*********************************
     * Constructor
     *********************************/

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
    public Device(Laboratory laboratory) {
        //TODO
    }

    /**
     * Return the result of the device in an ingredient container
     * with a maximum of one barrel or chest
     * Return null if there are no ingredients in the device
     *
     * @throws  DeviceNotYetUsedException
     *          The device has not been used yet so there is no result to be given
     *          | getNbOfIngredients() > 1
     * @throws  TerminatedObjectException
     *          The device is terminated.
     *          | isTerminated()
     */
    public IngredientContainer getResult() throws DeviceNotYetUsedException, TerminatedObjectException {
        if(isTerminated) throw new TerminatedObjectException(this);
        if (getNbOfIngredients() > 1) throw new DeviceNotYetUsedException();
        if (getNbOfIngredients() == 0) return null;
        AlchemicIngredient result = getIngredientAt(0);
        if (result.getState() == State.LIQUID) {
            if (result.getSpoonAmount() > 1260)
                result = new AlchemicIngredient(1, Unit.BARREL,
                        new Temperature(result.getColdness(), result.getHotness()),
                        result.getType(), result.getState());
            return new IngredientContainer(Unit.BARREL, result);

        } else {
            if (result.getSpoonAmount() > 1260)
                result = new AlchemicIngredient(1, Unit.CHEST,
                        new Temperature(result.getColdness(), result.getHotness()),
                        result.getType(), result.getState());
            return new IngredientContainer(Unit.CHEST, result);
        }
    }

    /**
     * Return the laboratory in which this device is in.
     *
     * @throws  TerminatedObjectException
     *          The device is terminated.
     *          | isTerminated()
     */
    @Basic
    public Laboratory getLaboratory() throws TerminatedObjectException {
        if (isTerminated) throw new TerminatedObjectException(this);
        return laboratory;
    }

    /**
     * A method for executing the device.
     */
    public void executeOperation() {}

    /**
     * A method for checking if a laboratory is valid for a device.
     *
     * @param   laboratory
     *          The laboratory to check for this device
     * @return  True if the laboratory is not a null pointer
     *          and this device is valid in the laboratory
     *          | laboratory != null && laboratory.isValidDevice(this)
     */
    @Raw
    public boolean isValidLaboratory(Laboratory laboratory) {
        return laboratory != null && laboratory.isValidDevice(this);
    }

    /**
     * Return whether or not the device is terminated.
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
     * @throws  TerminatedObjectException
     *          The device is already terminated
     *          | isTerminated()
     */
    protected void terminate() throws TerminatedObjectException {
        if (!isTerminated()) {isTerminated = true;}
        else throw new TerminatedObjectException(this);
    }
}
