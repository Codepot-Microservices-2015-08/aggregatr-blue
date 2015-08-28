package pl.codepot.aggregatr.aggregation.ingredient;

import java.util.Collection;

/**
 * @author dchojnacki
 */
public class Ingredients {

    private Collection<Ingredient> ingredients;

    public Ingredients(Collection<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
