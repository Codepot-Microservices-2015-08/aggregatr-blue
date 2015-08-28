package pl.codepot.aggregatr.aggregation.ingredient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * @author dchojnacki
 */
@RestController
@RequestMapping(value = "/ingredients", consumes = "application/vnd.pl.codepot.aggregatr.v1+json",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientController {

    @RequestMapping(method = RequestMethod.POST)
    public Ingredients getIngredients(@RequestBody Order order) {
        return new Ingredients(order.getItems().stream().map(item->new Ingredient(item.name(), 200)).collect(Collectors.toList()));
    }

}
