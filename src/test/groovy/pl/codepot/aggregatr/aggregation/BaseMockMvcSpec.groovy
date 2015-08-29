package pl.codepot.aggregatr.aggregation
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import pl.codepot.aggregatr.aggregation.ingredient.Ingredient
import pl.codepot.aggregatr.aggregation.ingredient.IngredientController
import pl.codepot.aggregatr.aggregation.ingredient.IngredientService
import pl.codepot.aggregatr.aggregation.ingredient.Ingredients
import pl.codepot.aggregatr.aggregation.ingredient.Order
import spock.lang.Specification

abstract class BaseMockMvcSpec extends Specification {

    protected static final int INGREDIENT_COUNT = 200

    IngredientService ingredientService = Stub()

    def setup() {
        setupMocks()

        RestAssuredMockMvc.standaloneSetup(new IngredientController(ingredientService))
    }

    void setupMocks() {
        ingredientService.sentIngredients(_) >> { Order order ->
            return new Ingredients(order.items.collect { new Ingredient(it.name(), INGREDIENT_COUNT)})
        }
    }

}
