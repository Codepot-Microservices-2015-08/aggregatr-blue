package pl.codepot.aggregatr.aggregation.ingredient;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author dchojnacki
 */
@RestController
@RequestMapping(value = "/ingredients", consumes = "application/vnd.pl.codepot.aggregatr.v1+json",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientController {

    private static final Logger log = LoggerFactory.getLogger(IngredientController.class);

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Ingredients getIngredients(@RequestBody Order order) {
        log.info("Recieving request...");

        order.getItems().forEach(ingredientService::makeOrder);
        if (ingredientService.isEnoughIngredientInStorage()) {
            ingredientService.dropMaxIngredients();
            ingredientService.notifyThatShipmentIsReady();
        }

        return ingredientService.sentIngredients(order);
    }



}
