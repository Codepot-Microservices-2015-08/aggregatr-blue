package pl.codepot.aggregatr.aggregation.ingredient;

import java.util.List;
import java.util.Map;

/**
 * @author dchojnacki
 */
public class Item {
    private Map<String, List<String>> items;

    public Item(Map<String, List<String>> items) {
        this.items = items;
    }

    public Map<String, List<String>> getItems() {
        return items;
    }

    public void setItems(Map<String, List<String>> items) {
        this.items = items;
    }
}
