package pl.codepot.aggregatr.aggregation.ingredient;

/**
 * @author dchojnacki
 */
public class Ingredient {
    private String type;
    private Integer quantity;

    public Ingredient(String type, Integer quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
