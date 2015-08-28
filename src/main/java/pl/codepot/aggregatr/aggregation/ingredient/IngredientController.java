package pl.codepot.aggregatr.aggregation.ingredient;

import com.google.common.collect.Lists;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dchojnacki
 */
@RestController
@RequestMapping(value = "/ingredients", consumes = "application/vnd.pl.codepot.aggregatr.v1+json",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientController {

    @RequestMapping(method = RequestMethod.POST)
    public Ingredients getIngredients(@RequestBody Order order) {
        List<Ingredient> result = Lists.newArrayList();
        for(IngredientType type : order.getItems()){
            result.add(new Ingredient(type.name(), 200));
        }
        return new Ingredients(result);
    }

}
