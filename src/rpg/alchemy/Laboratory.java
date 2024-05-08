package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;

import java.util.ArrayList;

/**
 * A class representing a Laboratory.
 *
 * @invar	The capacity of the laboratory must always be valid.
 * 			| isValidCapacity(getCapacity())
 * @invar	A laboratory must always have proper devices.
 * 			| hasProperDevices()
 */
public class Laboratory extends StorageLocation {

	/**********************************************************
	 * CONSTRUCTOR
	 **********************************************************/

	@Raw
	public Laboratory(int capacity) {
		if (!isValidCapacity(capacity)) {
			throw new IllegalArgumentException("The given capacity is not a valid capacity!");
		}
		this.capacity = capacity;
	}


	/**********************************************************
	 * CAPACITY
	 **********************************************************/

	/**
	 * A variable for keeping track of the capacity of the laboratory,
	 * expressed in a certain number of storerooms.
	 */
	private final int capacity;

	/**
	 * A getter for the capacity of this laboratory.
	 */
	@Basic
	@Immutable
	public int getCapacity() {
		return this.getCapacity();
	}

	/**
	 * A method to check whether a given capacity is a legal capacity for
	 * a storeroom.
	 *
	 * @param 	capacity
	 * 			The capacity to check.
	 * @return	True if and only if the capacity is greater than zero,
	 * 			| result == (capacity > 0)
	 */
	@Raw
	public boolean isValidCapacity(int capacity) {
		return capacity > 0;
	}



	/**********************************************************
	 * DEVICES
	 **********************************************************/

	/**
	 * A variable for keeping track of the devices within this laboratory.
	 */
	private ArrayList<Device> devices;

	/**
	 * Return the number of devices.
	 */
	@Basic
	public int getNbOfDevices() {
		return devices.size();
	}

	/**
	 * Return the devices registered at the given position in this laboratory.
	 *
	 * @param 	index
	 *        	The index of the device to be returned.
	 * @throws 	IllegalArgumentException
	 *         	The given index is not positive or exceeds the number
	 *         	of items registered in this directory minus 1.
	 *         	| (index < 0) || (index > getNbItems()-1)
	 */
	@Basic
	public Device getDeviceAt(int index) {
		try {
			return devices.get(index);
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);
		}
	}

	/**
	 * A method for checking if a laboratory contains two of the same types of devices.
	 *
	 * @return	True if and only if some type of device is present twice in this laboratory.
	 *          | result == ( for some I in 0..getNbOfDevices()-1:
	 *          |   for some J in 0..getNbOfDevices()-1:
	 *          |       (I != J) && getDeviceAt(I).getClass() == getIngredientAt(J).getClass() )
	 */
	public boolean hasTwiceSameTypeAs(Device device) {
		int count = 0;
		for (int i=0; i<getNbOfDevices(); i++) {
			if (getDeviceAt(i).getClass() == device.getClass()) {
				count++;
			}
		}
		return count > 1;
	}

	/**
	 * A method for checking if the laboratory has a device of a certain class.
	 *
	 * @param 	type
	 * 			The class of the device to check.
	 * @return	True if and only if the laboratory contains a device of
	 * 			the given class.
	 *          | result == ( for some I in 0..getNbOfDevices()-1:
	 *          |   getDeviceAt(I).getClass() == type )
	 * @throws	IllegalArgumentException
	 * 			The given type is not a subtype of Device.
	 * 			| !Device.class.isAssignableFrom(type)
	 */
	public boolean hasDeviceOfType(Class type) throws IllegalArgumentException {
		// the type is not a subtype of Device
		if (!Device.class.isAssignableFrom(type)) {
			throw new IllegalArgumentException("The given type is not a subtype of Device!");
		}
		for (int i=0; i<getNbOfDevices(); i++) {
			if (getDeviceAt(i).getClass() == type) {
				return true;
			}
		}
		return false;
	}

	/**
	 * A method for getting a device of a certain type from this laboratory.
	 *
	 * @param 	type
	 * 			The class of the device to get.
	 * @return	The device of the given type.
	 * 			| if (
	 * 			| 	for some I in 0..getNbOfDevices()-1:
	 * 			|		getDeviceAt(I).getClass() == type )
	 * 			|	then result == getDeviceAt(I)
	 * @throws	IllegalArgumentException
	 * 			The given type is not a legal type for this laboratory.
	 * 			| !hasDeviceOfType(type)
	 */
	public Device getDeviceOfType(Class type) throws IllegalArgumentException {
		if (!hasDeviceOfType(type)) {
			throw new IllegalArgumentException("Illegal type!");
		}
		for (int i=0; i<getNbOfDevices(); i++) {
			if (getDeviceAt(i).getClass() == type) {
				return getDeviceAt(i);
			}
		}
		// should never happen
		return null;
	}

	/**
	 * A method for checking if a given device is a legal device for this laboratory.
	 *
	 * @return 	TODO
	 */
	public boolean canHaveAsDevice(Device device) {
		return (device != null) && (!hasTwiceSameTypeAs(device));
	}

	/**
	 * A method for checking whether the laboratory has proper devices inside of it;
	 * thus this checks 1) the relation is correct 2) the contents are correct
	 *
	 * @return	TODO
	 */
	public boolean hasProperDevices() {
		for (int i=0; i<getNbOfDevices()-1; i++) {
			if ( !canHaveAsDevice(getDeviceAt(i))
					|| getDeviceAt(i).getLaboratory() != this ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * A method for adding a device to this laboratory.
	 *
	 * @param 	device
	 * 			The device to be added.
	 * @post	TODO
	 */
	public void addAsDevice(Device device) {
		if (!canHaveAsDevice(device)
			|| hasDeviceOfType(device.getClass()) ) {
			throw new IllegalArgumentException("Illegal device!");
		}
	}



	/**********************************************************
	 * INGREDIENTS
	 **********************************************************/

	/**
	 * A method for getting the total amount of ingredients stored in this laboratory,
	 * expressed in storerooms.
	 *
	 * @return	The total size of ingredients stored in this laboratory in storerooms.
	 * 			| result == sum( for I in 0..getNbOfDevices()-1:
	 * 			|		getDeviceAt(I).getStoreroomAmount() )
	 */
	public double getStoredAmount() {
		double amount = 0;
		for (int i=0; i<getNbOfIngredients(); i++) {
			amount += getIngredientAt(i).getStoreroomAmount();
		}
		return amount;
	}

	/**
	 * A method for checking if a given container exceeds the capacity of this laboratory
	 * if it were to be added.
	 *
	 * @param 	container
	 * 			The container to check.
	 *
	 * @return	True if and only if the given container exceeds the capacity of this laboratory.
	 * 			| result == getStoredAmount() + container.getContent().getStoreroomAmount() > getCapacity()
	 *
	 * @throws	NullPointerException
	 * 			The container is null.
	 * 			| container == null
	 */
	public boolean exceedsCapacity(IngredientContainer container) throws NullPointerException {
		if (container == null) {
			throw new NullPointerException("The container is null!");
		}
		return getStoredAmount() + container.getContent().getStoreroomAmount() > getCapacity();
	}

	@Override
	public void addIngredients(IngredientContainer container) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if (container == null) {
			throw new NullPointerException("The container is null!");
		}
		if (exceedsCapacity(container)) {
			throw new IllegalArgumentException("The content of the container is too large to fit inside this lab!");
		}
		if (container.getContent().isHotterThanStandardTemperature()) {
			if (!hasDeviceOfType(CoolingBox.class)) {
				throw new IllegalStateException("The content is hotter than standard temperature, but there is no cooling box in the lab!");
			} else if (getDeviceOfType(CoolingBox.class).canGetToStandardTemperature()) {
				throw new IllegalStateException("The content is hotter than standard temperature, but the cooling box cannot cool it down!");
			}
		} else if (container.getContent().isColderThanStandardTemperature()) {
			if (!hasDeviceOfType(Oven.class)) {
				throw new IllegalStateException("The content is colder than standard temperature, but there is no oven in the lab!");
			} else if (getDeviceOfType(Oven.class).canGetToStandardTemperature()) {
				throw new IllegalStateException("The content is colder than standard temperature, but the oven cannot heat it up!");
			}
		}
		if (hasIngredientWithSimpleName(container.getContent().getSimpleName())
				&& !hasDeviceOfType(Kettle.class)) {
			throw new IllegalStateException("There is already an ingredient with the same name in the lab, but there is no kettle in the lab!");
		}
		if (container.getContent().isHotterThanStandardTemperature()) {
			Device coolingBox = getDeviceOfType(CoolingBox.class);
			coolingBox.addIngredients(container);
			coolingBox.bringToStandardTemperature();
			container = coolingBox.getResult();
		} else if (container.getContent().isColderThanStandardTemperature()) {
			Device oven = getDeviceOfType(Oven.class);
			oven.addIngredients(container);
			oven.bringToStandardTemperature();
			container = oven.getResult();
		}
		if (hasIngredientWithSimpleName(container.getContent().getSimpleName())) {
			Device kettle = getDeviceOfType(Kettle.class);
			kettle.addIngredients(container);
			kettle.addIngredients(getIngredientWithSimpleName(container.getContent().getSimpleName()));
		} else {
			super.addIngredients(container);
		}
	}


}
