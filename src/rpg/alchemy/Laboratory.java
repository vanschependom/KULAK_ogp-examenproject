package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.Unit;

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
	 * @return	The index of the ingredient with the given simple name.
	 * 			| TODO
	 */
	public int getIndexOfSimpleName(String name) {
		for (int i=0; i<getNbOfIngredients(); i++) {
			if (getIngredientAt(i).getSimpleName().equals(name)) {
				return i;
			}
		}
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
		if (getIndexOfSimpleName(container.getContent().getSimpleName()) != -1 && !hasDeviceOfType(Kettle.class)) {
			throw new IllegalStateException("There is already an ingredient with the same name in the lab, but there is no kettle in the lab!");
		}
		if (container.getContent().isHotterThanStandardTemperature()) {
			// warmer -> koel af
			CoolingBox coolingBox = (CoolingBox) getDeviceOfType(CoolingBox.class);
			coolingBox.changeTemperatureTo(new Temperature()); 	// standard temperature
			coolingBox.addIngredients(container);
			// TODO operaties (evt meerdere)
			coolingBox.executeOperation();
			container = coolingBox.getResult();
		} else if (container.getContent().isColderThanStandardTemperature()) {
			// kouder -> warm op
			Oven oven = (Oven) getDeviceOfType(Oven.class);
			oven.changeTemperatureTo(new Temperature()); 		// standard temperature
			oven.addIngredients(container);
			// TODO operaties (evt meerdere)
			oven.executeOperation();
			container = oven.getResult();
		}
		int indexSameName = getIndexOfSimpleName(container.getContent().getSimpleName());
		if (indexSameName != -1) {
			Device kettle = getDeviceOfType(Kettle.class);
			kettle.addIngredients(container);
			kettle.addIngredients(getAllOfIngredientAt(indexSameName));
		} else {
			super.addIngredients(container);
		}
	}


}
