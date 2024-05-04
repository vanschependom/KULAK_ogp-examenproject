package rpg.alchemy;

import java.util.ArrayList;

/**
 * A class representing a temperature device inside a laboratory.
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class TemperatureDevice extends Device{

    private Temperature temperature = new Temperature(0, 20);

    public TemperatureDevice(Laboratory laboratory, Temperature temperature) throws IllegalArgumentException{
        super(laboratory);
        setTemperature(temperature);
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    @Override
    public boolean canHaveAsIngredients(ArrayList<AlchemicIngredient> ingredients) {
        if (ingredients.size() > 1) return false;
        if (ingredients.isEmpty()) return true;
        return ingredients.getFirst() != null && !ingredients.getFirst().isTerminated();
    }

    @Override
    public boolean canHaveAsIngredient(AlchemicIngredient ingredient) {
        if (getNbOfIngredients() == 0) return true;
        return getIngredientAt(0).equals(ingredient);
    }

    @Override
    public void executeOperation() {
        //TODO
    }




}
