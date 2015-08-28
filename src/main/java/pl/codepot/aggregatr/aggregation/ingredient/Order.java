package pl.codepot.aggregatr.aggregation.ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dchojnacki
 */
public class Order {
    private List<IngredientType> items = new ArrayList<>();


    public List<IngredientType> getItems() {
        return items;
    }

    public void setItems(List<IngredientType> items) {
        this.items = items;
    }
}
