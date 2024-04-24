package rpg;

public class IngredientType {

	private String simpleName;

	public IngredientType(String simpleName) {
		this.simpleName = simpleName;
	}

	@Override
	public String toString() {
		return "rpg.IngredientType{" +
				"simpleName='" + this.simpleName + "'" +
				'}';
	}

	public static final IngredientType DEFAULT = new IngredientType("water");

}
