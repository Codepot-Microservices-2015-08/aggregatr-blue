package pl.codepot.aggregatr.aggregation.ingredient;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.codepot.aggregatr.aggregation.model.Version;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author dchojnacki
 */
@Service
public class IngredientService {
    public static final Integer MAX_NUMBER = 1000;

    private final ServiceRestClient serviceRestClient;

    private HashMap<IngredientType, Integer> storage;

    @Autowired
    public IngredientService(ServiceRestClient serviceRestClient) {
        this.serviceRestClient = serviceRestClient;
        storage = new HashMap<>();
    }

    public void makeOrder(IngredientType ingredientType) {
        orderIngredient(ingredientType);
    }

    public Integer generateRandomAmountOfIngredientType() {
        return 100 * new Random().nextInt();
    }

    public boolean isEnoughIngredientInStorage() {
        for (IngredientType ingredientType : storage.keySet()) {
            Integer storageAmount = storage.get(ingredientType);
            if (storageAmount < MAX_NUMBER) {
                return false;
            }
        }
        return true;
    }

    public void orderIngredient(IngredientType ingredientType) {
        storage.put(ingredientType, storage.get(ingredientType) + generateRandomAmountOfIngredientType());
    }

    public void dropMaxIngredients() {
        for (IngredientType ingredientType : storage.keySet()) {
            storage.put(ingredientType, storage.get(ingredientType) - MAX_NUMBER);
        }
    }

    public Ingredients sentIngredients(Order order) {
        return new Ingredients(order.getItems().stream().map(item -> new Ingredient(item.name(),
                storage.get(item))).collect(Collectors.toList()));
    }

    public void notifyThatShipmentIsReady() {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture<Void> call = service.submit(new Callable<Void>() {
            public Void call() {
                Ingredients ingredients = createIngredients();
                serviceRestClient.forService("dojrzewatr")
                        .post()
                        .onUrl("/brew")
                        .body(ingredients)
                .withHeaders()
                        .header("Content-Type", Version.DOJRZEWATR_V1)
                        .contentTypeJson()
                        .andExecuteFor()
                        .ignoringResponseAsync();
                return null;
            }
        });
    }

    private Ingredients createIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientType ingredientType : storage.keySet()) {
            ingredients.add(new Ingredient(ingredientType.name(), storage.get(ingredientType)));
        }
        return new Ingredients(ingredients);
    }
}
