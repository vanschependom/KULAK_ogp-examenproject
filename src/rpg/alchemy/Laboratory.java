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
	public boolean containsTypeTwice(Device device) {
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
	 * @param 	device
	 * 			The device to compare with.
	 * @return	True if and only if the laboratory contains a device of
	 * 			the given class.
	 *          | result == ( for some I in 0..getNbOfDevices()-1:
	 *          |   getDeviceAt(I).getClass() == device.getClass() )
	 */
	public boolean hasDeviceOfSameTypeAs(Device device) {
		for (int i=0; i<getNbOfDevices(); i++) {
			if (getDeviceAt(i).getClass() == device.getClass()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * A method for checking if a given device is a legal device for this laboratory.
	 *
	 * @return 	TODO
	 */
	public boolean canHaveAsDevice(Device device) {
		return (device != null) && (!containsTypeTwice(device));
	}

	/**
	 * A method for checking whether the laboratory has proper devices inside of it.
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
			|| hasAsDeviceType(device.getClass()) ) {
			throw new IllegalArgumentException("Illegal device!");
		}
	}


}
