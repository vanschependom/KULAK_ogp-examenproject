package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.Unit;
import rpg.exceptions.IngredientNotPresentException;

import java.awt.*;
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

	/**
	 * A constructor for creating a new laboratory with a given capacity.
	 *
	 * @param 	capacity
	 * 			The capacity of the laboratory, expressed in storerooms.
	 *
	 * @post	The capacity of the new laboratory is equal to the given capacity.
	 * 			| new.getCapacity() == capacity
	 *
	 * @throws	IllegalArgumentException
	 * 			The given capacity is not a valid capacity.
	 * 			| !isValidCapacity(capacity)
	 */
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
		return capacity;
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
	private final ArrayList<Device> devices = new ArrayList<>();

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
	 * A method that gives back the index of a given device.
	 *
	 * @param 	device
	 * 			The device to get the index of.
	 * @return  The given device is registered in this laboratory at the
	 *          resulting position.
	 *          | getDeviceAt(result) == device
	 * @throws  IllegalArgumentException
	 *          The given device is not in the laboratory
	 *          | !hasAsDevice(device)
	 */
	public int getIndexOfDevice(Device device) {
		if (!hasAsDevice(device)) {
			throw new IllegalArgumentException("The device is not present in this laboratory");
		}
		for (int i = 0; i < getNbOfDevices(); i++) {
			if (getDeviceAt(i) == device) return i;
		}
		// this should never happen
		assert false;
		return -1;
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
	 *
	 * @note 	The provided type (of class Class) must extend the class Device.
	 */
	public boolean hasDeviceOfType(Class<? extends Device> type) throws IllegalArgumentException {
		for (int i=0; i<getNbOfDevices(); i++) {
			if (getDeviceAt(i).getClass() == type) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the given device is present in this laboratory.
	 *
	 * @param 	device
	 *        	The device to check.
	 * @return 	False if the given device is not effective.
	 * 	   		| if (device == null)
	 * 	   		| 	then result == false
	 * @return 	True if a device equal to the given item is registered at some
	 *         	position in this laboratory; false otherwise.
	 *         	| result ==
	 *         	|    for some I in 1..getNbOfDevices() :
	 *         	| 	      (getDeviceAt(I) == device)
	 */
	public boolean hasAsDevice(Device device) {
		if (device == null) return false;
		for (int i=0; i<getNbOfDevices(); i++) {
			if (getDeviceAt(i) == device) {
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
	 * 			| if ( for some I in 0..getNbOfDevices()-1:
	 * 			|		getDeviceAt(I).getClass() == type )
	 * 			|	then result == getDeviceAt(I)
	 * @throws	IllegalArgumentException
	 * 			The given type is not a legal type for this laboratory.
	 * 			| !hasDeviceOfType(type)
	 */
	public Device getDeviceOfType(Class<? extends Device> type) throws IllegalArgumentException {
		if (!hasDeviceOfType(type)) {
			throw new IllegalArgumentException("The given type is not present in this laboratory!");
		}
		for (int i=0; i<getNbOfDevices(); i++) {
			if (getDeviceAt(i).getClass() == type) {
				return getDeviceAt(i);
			}
		}
		// should never happen
		assert false;
		return null;
	}

	/**
	 * A method for checking if a given device is a legal device for this laboratory.
	 *
	 * @return 	True if and only if the given device is not null and
	 * 			the laboratory does not contain a device of the same type.
	 * 			| result == (device != null)
	 * 			|			&& (!hasTwiceSameTypeAs(device))
	 * 			|			&& !device.isTerminated()
	 */
	public boolean canHaveAsDevice(Device device) {
		return (device != null)
				&& (!hasTwiceSameTypeAs(device))
				&& !device.isTerminated();
	}

	/**
	 * A method for checking whether the laboratory has proper devices inside of it;
	 * thus this checks 1) the relation is correct 2) the contents are correct
	 *
	 * @return  True if and only if this laboratory can have all its devices
	 * 			at their respective indices.
	 *          | result ==
	 *          |   for each I in 0..getNbOfDevices() :
	 *          |     canHaveAsDevice(getDeviceAt(I)) && getDeviceAt(I).getLaboratory() == this
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
	 *
	 * @post    The number of devices registered in this laboratory is
	 *          incremented with 1.
	 *          | new.getNbOfDevices() == getNbOfDevices() + 1
	 * @post    The given item is inserted at the last index.
	 *          | new.getDeviceAt(getNbOfDevices()-1) == device
	 *
	 * @throws	IllegalArgumentException
	 * 			A device of this type is already present in this laboratory
	 * 			| hasDeviceOfType(device.getClass())	.
	 * @throws	IllegalArgumentException
	 * 			This device is not allowed for this laboratory.
	 * 			| !canHaveAsDevice(device)
	 * @throws	IllegalStateException
	 * 			The given device does not reference this laboratory as its parent.
	 * 			| device.getLaboratory() != this
	 */
	protected void addAsDevice(Device device) {
		if (hasDeviceOfType(device.getClass())) {
			throw new IllegalArgumentException("A device if this type is already present in this laboratory.");
		}
		if (!canHaveAsDevice(device)) {
			throw new IllegalArgumentException("This device is not allowed for this laboratory.");
		}
		// device is effective, so we can call getLaboratory()
		if (device.getLaboratory() != this) {
			throw new IllegalStateException("The given device does not yet reference this laboratory as its parent.");
		}
		devices.add(device);
	}

	/**
	 * A method for removing a device out of this laboratory.
	 *
	 * @param 	device
	 * 			The device to remove
	 *
	 * @effect 	The given item is removed from the position it was registered at.
	 *         	| removeItemAt(getIndexOf(item))
	 *
	 * @throws 	IllegalArgumentException
	 *         	The given device is not in the laboratory
	 *         	| !hasAsDevice(device)
	 * @throws	IllegalStateException
	 * 			The reference of the given (effective) device to its laboratory must already be broken down.
	 * 			| (item != null) && item.getLaboratory() != this
	 */
	@Raw
	protected void removeAsDevice(Device device) throws IndexOutOfBoundsException, IllegalStateException, IllegalArgumentException {
		if (!hasAsDevice(device)) {
			throw new IllegalArgumentException("Illegal given device!");
		}
		// device is certainly not null
		if(device.getLaboratory() == this) {
			throw new IllegalStateException("The given device still references this laboratory as its laboratory.");
		}
		try{
			removeAsDeviceAt(getIndexOfDevice(device));
		}catch(IndexOutOfBoundsException e){
			// Should not happen!
			assert false;
		}
	}

	/**
	 * Remove the given device at the given index from this laboratory.
	 *
	 * @param 	index
	 *        	The index from the device to remove.
	 *
	 * @post  	The number of items has decreased by one
	 *        	| new.getNbOfDevices() == getNbOfDevices() - 1
	 * @post	This laboratory no longer has the device at the given index as a device
	 * 			| !new.hasAsDevice(getDeviceAt(index))
	 * @post  	All elements to the right of the removed device
	 *        	are shifted left by 1 position.
	 *        	| for each I in index+1..getNbOfDevices():
	 *        	|   new.getDeviceAt(I-1) == getDeviceAt(I)
	 *
	 * @throws	IndexOutOfBoundsException
	 *        	The given position is not positive or is equal to or exceeds the number
	 *        	of devices registered in this laboratory.
	 *        	| (index < 0) || (index >= getNbItems())
	 */
	private void removeAsDeviceAt(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= getNbOfDevices())
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);
		try{
			devices.remove(index);
		}catch(IndexOutOfBoundsException e) {
			// Should not happen.
			assert false;
		}
	}



	/**********************************************************
	 * INGREDIENTS
	 **********************************************************/

	/**
	 * A method for getting a container with all the ingredient present
	 * at a certain index.
	 *
	 * @param 	index
	 * 			The index of the ingredient to get.
	 * @return 	TODO
	 */
	private IngredientContainer getAllOfIngredientAt(int index) {
		// TODO check index
		AlchemicIngredient ingredient = getIngredientAt(index);
		removeAsIngredient(ingredient);
		return new IngredientContainer(ingredient);
	}

	/**
	 * A method for getting a certain amount of a certain unit at a given index,
	 * returned in a container.
	 *
	 * @param   index
	 *          The index of the ingredient to get a certain quantity of.
	 * @param   amount
	 *          The amount to get.
	 * @param   unit
	 *          The unit of the amount.
	 * @return  TODO
	 */
	private IngredientContainer getAmountOfIngredientAt(int index, int amount, Unit unit) {
		// TODO check index out of bounds
		// 		check amount > 0
		//		check unit != null
		AlchemicIngredient ingredient = getIngredientAt(index);
		double amountLeft = ingredient.getAmount() - amount*unit.getConversionFor(ingredient.getUnit());
		if (amountLeft < 0) {
			throw new IllegalArgumentException("Not enough of this ingredient in the storage location!");
		}
		if (amountLeft == 0) {
			removeAsIngredient(ingredient);
		} else {
			removeAsIngredient(ingredient);
			addAsIngredient(new AlchemicIngredient(
					(int) amountLeft,  // afronding!
					ingredient.getUnit(),
					new Temperature(ingredient.getColdness(), ingredient.getHotness()),
					ingredient.getType(),
					ingredient.getState()
			));
		}
		return new IngredientContainer(new AlchemicIngredient(
				amount,
				unit,
				new Temperature(ingredient.getColdness(), ingredient.getHotness()),
				ingredient.getType(),
				ingredient.getState()
		));
	}

	/**
	 * A method for getting the index of an ingredient with a given simple name.
	 *
	 * @param 	name
	 * 			The simple name to check.
	 * @return  The given ingredient with this name is registered in this laboratory at the
	 *          resulting position.
	 *          | getIngredientAt(result).getSimpleName().equals(name)
	 * @throws  IllegalArgumentException
	 *          An ingredient with the given name is not in the laboratory.
	 *          | !hasIngredientWithSimpleName(name)
	 */
	public int getIndexOfSimpleName(String name) throws IllegalArgumentException {
		if (!hasIngredientWithSimpleName(name)) {throw new IllegalArgumentException("There is no ingredient with the given name in this laboratory");}
		for (int i=0; i<getNbOfIngredients(); i++) {
			if (getIngredientAt(i).getSimpleName().equals(name)) {
				return i;
			}
		}
		// should never happen
		return -1;
	}

	/**
	 * A method for getting the index of an ingredient with a given special name.
	 *
	 * @param 	name
	 * 			The special name to check.
	 * @return	The index of the ingredient with the given simple name.
	 * 			| TODO
	 */
	public int getIndexOfSpecialName(String name) {
		for (int i=0; i<getNbOfIngredients(); i++) {
			if (getIngredientAt(i).getSpecialName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

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
		if (container.getContent().isHotterThanStandardTemperature() && !hasDeviceOfType(CoolingBox.class)) {
				throw new IllegalStateException("The content is hotter than standard temperature, but there is no cooling box in the lab!");
		} else if (container.getContent().isColderThanStandardTemperature() && !hasDeviceOfType(Oven.class)) {
				throw new IllegalStateException("The content is colder than standard temperature, but there is no oven in the lab!");
		}
		if (hasIngredientWithSimpleName(container.getContent().getSimpleName()) && !hasDeviceOfType(Kettle.class)) {
			throw new IllegalStateException("There is already an ingredient with the same name in the lab, but there is no kettle in the lab!");
		}
		if (container.getContent().isHotterThanStandardTemperature()) {
			// warmer -> koel af
			CoolingBox coolingBox = (CoolingBox) getDeviceOfType(CoolingBox.class);
			coolingBox.changeTemperatureTo(new Temperature()); 	// standard temperature
			coolingBox.addIngredients(container);
			coolingBox.executeOperation();						// cooling box is exact!
			container = coolingBox.getResult();
		} else if (container.getContent().isColderThanStandardTemperature()) {
			// kouder -> warm op
			Oven oven = (Oven) getDeviceOfType(Oven.class);
			oven.changeTemperatureTo(new Temperature()); 		// standard temperature
			oven.addIngredients(container);
			// oven is not exact! +- 5 degrees
			do {
				oven.executeOperation();
			} while (oven.getIngredientAt(0).isColderThanStandardTemperature() || oven.getIngredientAt(0).isHotterThanStandardTemperature());
			container = oven.getResult();
		}
		// mix with ingredients with same name
		try {
			int indexSameName = getIndexOfSimpleName(container.getContent().getSimpleName());
			Device kettle = getDeviceOfType(Kettle.class);
			kettle.addIngredients(container);
			kettle.addIngredients(getAllOfIngredientAt(indexSameName));
			kettle.executeOperation();
			container = kettle.getResult();
		} catch (IngredientNotPresentException e) {
			// no ingredient with same name
		}
		super.addIngredients(container);
	}


}
