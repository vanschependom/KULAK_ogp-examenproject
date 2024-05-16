package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.Unit;
import rpg.exceptions.IngredientNotPresentException;
import rpg.recipe.Operation;
import rpg.recipe.Recipe;

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
	 * 			The capacity of the laboratory, expressed in a certain number of storerooms.
	 *
	 * @post	The capacity of the new laboratory is equal to the given capacity.
	 * 			| new.getCapacity() == capacity
	 *
	 * @throws	IllegalArgumentException
	 * 			The given capacity is not a valid capacity.
	 * 			| !isValidCapacity(capacity)
	 */
	@Raw
	public Laboratory(int capacity) throws IllegalArgumentException {
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
	@Basic @Immutable
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
	 *
	 * @invar	devices references an effective list.
	 * 			| devices != null
	 * @invar   Each device in the list references an effective device.
	 *          | for each device in devices:
	 *          |   device != null
	 * @invar   Each device in the list references a non-terminated device.
	 *          | for each device in devices:
	 *          |   !device.isTerminated()
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
	 * @throws 	IndexOutOfBoundsException
	 *         	The given index is not positive or exceeds the number
	 *         	of devices registered in this laboratory minus 1.
	 *         	| (index < 0) || (index > getNbOfDevices()-1)
	 */
	@Basic
	public <T extends Device> T getDeviceAt(int index) throws IndexOutOfBoundsException {
		try {
			return (T) devices.get(index);
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
	 *          The given device is not in the laboratory.
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
	 *
	 * @note 	The device can be raw because it is called from canHaveAsDevice.
	 */
	public boolean hasTwiceSameTypeAs(@Raw Device device) {
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
	 * @return 	True if a device equal to the given device is registered at some
	 *         	position in this laboratory; false otherwise.
	 *         	| result ==
	 *         	|    for some I in 0..getNbOfDevices()-1 :
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
	public <T extends Device> T getDeviceOfType(Class<T> type) throws IllegalArgumentException {
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
	 *
	 * @note 	The device can be raw, since this method is called from the setLaboratory() method of device,
	 * 			which is in turn called from the constructor of device.
	 */
	public boolean canHaveAsDevice(@Raw Device device) {
		return (device != null)
				&& (!hasTwiceSameTypeAs(device))
				&& !device.isTerminated();
	}

	/**
	 * A method for checking whether the laboratory has proper devices inside of it;
	 * thus this checks 1) the relation is correct 2) the contents are correct.
	 *
	 * @return  True if and only if this laboratory can have all its devices
	 * 			at their respective indices.
	 *          | result ==
	 *          |   for each I in 0..getNbOfDevices()-1 :
	 *          |     canHaveAsDevice(getDeviceAt(I))
	 *          |		&& getDeviceAt(I).getLaboratory() == this
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
	 * @post    The given device is inserted at the last index.
	 *          | new.getDeviceAt(getNbOfDevices()-1) == device
	 *
	 * @throws	IllegalArgumentException
	 * 			A device of this type is already present in this laboratory.
	 * 			| hasDeviceOfType(device.getClass())
	 * @throws	IllegalArgumentException
	 * 			This device is not allowed for this laboratory.
	 * 			| !canHaveAsDevice(device)
	 * @throws	IllegalStateException
	 * 			The given device does not reference this laboratory as its parent.
	 * 			| device.getLaboratory() != this
	 */
	protected void addAsDevice(Device device) {
		if (!canHaveAsDevice(device)) {
			throw new IllegalArgumentException("This device is not allowed for this laboratory.");
		}
		if (hasDeviceOfType(device.getClass())) {
			throw new IllegalArgumentException("A device if this type is already present in this laboratory.");
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
	 * 			The device to remove.
	 *
	 * @effect 	The given device is removed from the position it was registered at.
	 *         	| removeAsDeviceAt(getIndexOfDevice(device))
	 *
	 * @throws 	IllegalArgumentException
	 *         	The given device is not in the laboratory.
	 *         	| !hasAsDevice(device)
	 * @throws	IllegalStateException
	 * 			The reference of the given (effective) device to its laboratory must already be broken down.
	 * 			| (device != null) && device.getLaboratory() != this
	 */
	@Raw
	protected void removeAsDevice(Device device) throws IndexOutOfBoundsException, IllegalStateException, IllegalArgumentException {
		if (!hasAsDevice(device)) {
			throw new IllegalArgumentException("Device is not present in this laboratory!");
		}
		// device is certainly not null
		if (device.getLaboratory() == this) {
			throw new IllegalStateException("The given device still references this laboratory as its laboratory.");
		}
		try {
			removeAsDeviceAt(getIndexOfDevice(device));
		} catch(IndexOutOfBoundsException e) {
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
	 * @post  	The number of devices has decreased by one
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
	 *        	| (index < 0) || (index >= getNbDevices())
	 */
	@Model
	private void removeAsDeviceAt(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= getNbOfDevices()) {
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);
		}
		try {
			devices.remove(index);
		} catch(IndexOutOfBoundsException e) {
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
	 * @effect 	The ingredient is removed from the laboratory.
	 * 			| removeAsIngredient(getIngredientAt(index))
	 * @return 	A container with the ingredient at the given index.
	 * 			| result.getContent().equals(getIngredientAt(index))
	 * @throws	IndexOutOfBoundsException
	 * 			The given index is not valid.
	 * 			| index < 0 || index >= getNbOfIngredients()
	 */
	public IngredientContainer getAllOfIngredientAt(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= getNbOfIngredients()) {
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);
		}
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
	 *
	 * @pre     The given amount is strictly positive.
	 * 	  		| amount > 0
	 * @pre     The given amount is smaller than or equal to the amount of ingredient in the laboratory at the same unit
	 * 	  		| amount*unit.getConversionFor(getIngredientAt(index).getUnit()) <= getIngredientAt(index).getAmount()
	 * @pre		The given unit is effective.
	 * 			| unit != null
	 *
	 * @effect	A new ingredient, with its amount subtracted with the given amount at the same unit,
	 * 			is added to the laboratory and the ingredient at given index is removed.
	 * 			| addAsIngredient(new AlchemicIngredient(
	 * 			|		(int) getIngredientAt(index).getAmount()
	 * 			|			- amount*unit.getConversionFor(getIngredientAt(index).getUnit()),
	 * 			|		getIngredientAt(index).getUnit(),
	 * 			|		new Temperature(getIngredientAt(index).getTemperature()),
	 * 			|		getIngredientAt(index).getType(),
	 * 			|		getIngredientAt(index).getState()
	 * 			| ))
	 * 			| && removeAsIngredient(getIngredientAt(index))
	 *
	 * @return  The amount of a given unit of the ingredient at the given index
	 * 			| result ==
	 * 			| 	new IngredientContainer(new AlchemicIngredient(
	 * 			|								amount, unit,
	 * 			|								new Temperature(getIngredientAt(index).getTemperature()),
	 * 			|								getIngredientAt(index).getType(),
	 * 			|								getIngredientAt(index).getState() )
	 *
	 * @throws	IndexOutOfBoundsException
	 * 			The given index is not valid.
	 * 			| index < 0 || index >= getNbOfIngredients()
	 */
	@Raw
	public IngredientContainer getAmountOfIngredientAt(int index, int amount, Unit unit) throws IndexOutOfBoundsException {
		if (index < 0 || index >= getNbOfIngredients()) {
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);
		}
		AlchemicIngredient ingredient = getIngredientAt(index);
		double amountLeft = ingredient.getAmount() - (amount*unit.getConversionFor(ingredient.getUnit()));
		removeAsIngredient(ingredient);
		if (amountLeft != 0) {
			addAsIngredient(new AlchemicIngredient(
					(int) amountLeft,  // afronding!
					ingredient.getUnit(),
					new Temperature(ingredient.getTemperature()),
					ingredient.getType(),
					ingredient.getState()
			));
		}
		return new IngredientContainer(new AlchemicIngredient(
				amount,
				unit,
				new Temperature(ingredient.getTemperature()),
				ingredient.getType(),
				ingredient.getState()
		));
	}

	/**
	 * A method for getting the index of an ingredient with a given simple name.
	 *
	 * @param 	name
	 * 			The simple name to check.
	 * @return  An ingredient with the given simple name is registered in this laboratory at the
	 *          resulting position.
	 *          | getIngredientAt(result).getSimpleName().equals(name)
	 * @throws  IllegalArgumentException
	 *          An ingredient with the given name is not in the laboratory.
	 *          | !hasIngredientWithSimpleName(name)
	 */
	public int getIndexOfSimpleName(String name) throws IllegalArgumentException {
		if (!hasIngredientWithSimpleName(name)) {throw new IllegalArgumentException("There is no ingredient with the given simple name in this laboratory");}
		for (int i=0; i<getNbOfIngredients(); i++) {
			if (name.equals(getIngredientAt(i).getSimpleName())) {
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
	 * @return  An ingredient with the given special name is registered in this laboratory at the
	 *          resulting position.
	 *          | getIngredientAt(result).getSpecialName().equals(name)
	 * @throws  IllegalArgumentException
	 *          There is no ingredient with the given special name.
	 *          | !hasIngredientWithSpecialName(name)
	 */
	public int getIndexOfSpecialName(String name) throws IllegalArgumentException {
		if (!hasIngredientWithSpecialName(name)) {
			throw new IllegalArgumentException("There is no ingredient with the special given name in this laboratory");
		}
		for (int i=0; i<getNbOfIngredients(); i++) {
			if (name.equals(getIngredientAt(i).getSpecialName())) {
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
	 * 			| result == sum( {for I in 0..getNbOfIngredients()-1: getIngredientAt(I).getStoreroomAmount()} )
	 */
	public double getStoredAmount() {
		double amount = 0;
		for (int i = 0; i < getNbOfIngredients(); i++) {
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

	/**
	 * A method to add ingredients to a laboratory given a container.
	 *
	 * @param 	container
	 *          The container of which the contents should be added to the laboratory.
	 *
	 * @effect	The ingredient in the container is set to its standard temperature.
	 * 			| container.equals(bringToStandardTemperature(container))
	 * @effect	If there is another ingredient with the same (simple) name and there is a kettle, then use the kettle
	 * 			to mix these two ingredient.
	 * 			| if (hasIngredientWithSimpleName(container.getContent().getSimpleName()) && hasDeviceOfType(Kettle.class))
	 * 			|	then getDeviceOfType(Kettle.class).addIngredients(container)
	 * 			|		&& getDeviceOfType(Kettle.class).addIngredients(getAllOfIngredientAt(getIndexOfSimpleName(container.getContent().getSimpleName())))
	 * 			|		&& getDeviceOfType(Kettle.class).executeOperation()
	 * 			|		&& super.addIngredients(getDeviceOfType(Kettle.class).getResult())
	 *
	 * @throws 	NullPointerException
	 * 			The container is null.
	 * 			| container == null
	 * @throws 	IllegalArgumentException
	 * 			The container exceeds the capacity.
	 * 			| exceedsCapacity(container)
	 * @throws 	IllegalStateException
	 * 			The content needs to be mixed with another ingredient with the same name but there isn't a kettle.
	 * 			| hasIngredientWithSimpleName(container.getContent().getSimpleName()) && !hasDeviceOfType(Kettle.class)
	 */
	@Override
	public void addIngredients(IngredientContainer container) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		// throw exceptions
		if (container == null) {
			throw new NullPointerException("The container is null!");
		}
		if (exceedsCapacity(container)) {
			throw new IllegalArgumentException("The content of the container is too large to fit inside this lab!");
		}
		if (hasIngredientWithSimpleName(container.getContent().getSimpleName()) && !hasDeviceOfType(Kettle.class)) {
			throw new IllegalStateException("There is already an ingredient with the same name in the lab, but there is no kettle in the lab!");
		}
		container = bringToStandardTemperature(container);
		// mix with ingredients with same name
		try {
			int indexSameName = getIndexOfSimpleName(container.getContent().getSimpleName());
			Kettle kettle = getDeviceOfType(Kettle.class);
			kettle.addIngredients(container);
			kettle.addIngredients(getAllOfIngredientAt(indexSameName));
			kettle.executeOperation();
			container = kettle.getResult();
		} catch (IngredientNotPresentException | IllegalArgumentException e) {
			// no ingredient with the same name
		}
		super.addIngredients(container);
	}

	/**
	 * A help method to set the ingredient of a container to its standard temperature.
	 *
	 * @param 	container
	 * 			The container of which the ingredient will be set to its standard temperature.
	 *
	 * @effect	The ingredient in the container is set to the standard temperature of its type.
	 * 			| bringToTemperature(container, container.getContent().getType().getStandardTemperature())
	 */
	@Model
	private IngredientContainer bringToStandardTemperature(IngredientContainer container) throws IllegalStateException {
		return bringToTemperature(container, container.getContent().getType().getStandardTemperature());
	}


	/**
	 * A method to bring the ingredient of a container to a certain temperature.
	 *
	 * @param 	container
	 * 			The container that contains the ingredient.
	 * @param 	temperature
	 * 			The temperature (an array of two longs) that the ingredient will be set to.
	 *
	 * @return 	If the ingredient is warmer than the given temperature and there is a cooling box then
	 * 			use to cooling box to set the ingredient to its standard temperature.
	 * 			| if (container.getContent().isHotterThan(temperature) && hasDeviceOfType(CoolingBox.class))
	 * 			| 	then result.getTemperature()[0] == temperature[0]
	 * 			|			&& result.getTemperature()[1] == temperature[1]
	 * @return	If the ingredient is colder than the given temperature and there is an oven then use
	 * 			the oven to set the ingredient to its standard temperature.
	 * 			| if (container.getContent().isColderThan(temperature) && hasDeviceOfType(Oven.class))
	 * 			| 	then result.getTemperature()[0] == temperature[0]
	 * 			|		&& result.getTemperature()[1] == temperature[1]
	 * @return 	If the ingredient is already at the given temperature, then no modifications are made.
	 * 			| if ( container.getContent().getTemperature()[0] == temperature[0]
	 * 			|		 && container.getContent().getTemperature()[1] == temperature[1] )
	 * 			| 	then result == container
	 *
	 * @throws 	IllegalStateException
	 * 			The content needs to be heated and there isn't an oven.
	 * 			| container.getContent().isHotterThanStandardTemperature() && !hasDeviceOfType(CoolingBox.class)
	 * @throws 	IllegalStateException
	 * 			The content needs to be cooled but there isn't a cooling box.
	 * 			| container.getContent().isColderThanStandardTemperature() && !hasDeviceOfType(Oven.class)
	 */
	@Model
	private IngredientContainer bringToTemperature(IngredientContainer container, long[] temperature) throws IllegalStateException {

		if (container.getContent().isHotterThan(temperature) && !hasDeviceOfType(CoolingBox.class)) {
			throw new IllegalStateException("The content is hotter than the provided temperature, but there is no cooling box in the lab!");
		} else if (container.getContent().isColderThan(temperature) && !hasDeviceOfType(Oven.class)) {
			throw new IllegalStateException("The content is colder than the provided temperature, but there is no oven in the lab!");
		}

		// because the oven is not exact, we need to keep heating/cooling until the temperature is standard!
		while (container.getContent().isColderThan(temperature) || container.getContent().isHotterThan(temperature)) {
			if (container.getContent().isHotterThan(temperature)) {
				// too hot -> cool
				CoolingBox coolingBox = getDeviceOfType(CoolingBox.class);
				coolingBox.changeTemperatureTo(temperature);    // standard temperature of the ingredient type
				coolingBox.addIngredients(container);
				coolingBox.executeOperation();                 	// cooling box is exact!
				container = coolingBox.getResult();
			} else {
				// too cold -> heat
				Oven oven = getDeviceOfType(Oven.class);
				oven.changeTemperatureTo(temperature);        	// standard temperature of the ingredient type
				oven.addIngredients(container);
				oven.executeOperation();
				container = oven.getResult(); 					// oven is not exact so we need a while loop
			}
		}
		return container;
	}



	/**********************************************************
	 * RECIPE EXECUTION
	 **********************************************************/

	/**
	 * A method to execute a recipe in a laboratory an x amount of times.
	 */
	public void execute(Recipe recipe, int multiplier) throws IllegalStateException {

		if (!isValidMultiplier(multiplier)) {
			throw new IllegalArgumentException("The multiplier must be greater than zero.");
		}
		if(!hasDevicesForRecipe(recipe)) {
			throw new IllegalStateException("The laboratory does not have the required devices for this recipe.");
		}

		AlchemicIngredient currentIngredient = null;

		for (int i = 0; i < recipe.getNbOfOperations(); i++) {

			Operation operation = recipe.getOperationAt(i);

			if (operation == Operation.ADD) {

				if (currentIngredient != null) {
					getDeviceOfType(Kettle.class).addIngredients(new IngredientContainer(currentIngredient));
				}
				currentIngredient = recipe.getIngredientAt(i);

			} else if (operation == Operation.COOL) {

				// currentIngredient can't be null, since the first operation must be ADD
				// add sets the currentIngredient to an effective object.
				Temperature temperatureMinusTen = Temperature.add(new Temperature(currentIngredient.getTemperature()), new Temperature(10, 0));

				currentIngredient = bringToTemperature(new IngredientContainer(currentIngredient), temperatureMinusTen.getTemperature()).getContent();

			} else if (operation == Operation.HEAT) {

				// currentIngredient can't be null, since the first operation must be ADD
				// add sets the currentIngredient to an effective object.
				Temperature temperaturePlusTen = Temperature.add(new Temperature(currentIngredient.getTemperature()), new Temperature(0, 10));

				currentIngredient = bringToTemperature(new IngredientContainer(currentIngredient), temperaturePlusTen.getTemperature()).getContent();

			} else {

				Kettle kettle = getDeviceOfType(Kettle.class);
				kettle.addIngredients(new IngredientContainer(currentIngredient));
				kettle.executeOperation();
				currentIngredient = kettle.getResult().getContent();

			}

		}
	}

	public boolean hasDevicesForRecipe(Recipe recipe) {
		if (!hasDeviceOfType(Kettle.class)) {
			return false;
		}
		if (recipe.hasAsOperation(Operation.COOL) && !hasDeviceOfType(CoolingBox.class)) {
			return false;
		}
		if (recipe.hasAsOperation(Operation.HEAT) && !hasDeviceOfType(Oven.class)) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if there are enough ingredients to execute the recipe.
	 */
	protected boolean hasEnoughIngredientsForRecipe(Recipe recipe, int multiplier) {
		if (!isValidMultiplier(multiplier)) {
			throw new IllegalArgumentException("The multiplier must be greater than zero.");
		}
		for (int i = 0; i < recipe.getNbOfIngredients(); i++) {
			if (!hasIngredientWithSimpleName(recipe.getIngredientAt(i).getSimpleName())) {
				return false;
			} else {
				// check if spoon equivalents are enough
				AlchemicIngredient ingredient = recipe.getIngredientAt(i);
				if (ingredient.getSpoonAmount() * multiplier >= getIngredientAt(getIndexOfSimpleName(ingredient.getSimpleName())).getSpoonAmount()) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean isValidMultiplier(int multiplier) {
		return multiplier > 0;
	}

}
